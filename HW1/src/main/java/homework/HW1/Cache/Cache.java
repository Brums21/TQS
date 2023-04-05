package homework.HW1.Cache;

import homework.HW1.Model.AirCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Cache {

    static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final long TTL = 240;

    private final HashMap<AirCondition, LocalDateTime> cacheCurrent = new HashMap<>();

    private final HashMap<ArrayList<AirCondition>, LocalDateTime> cacheDays = new HashMap<>();

    private int hits = 0;

    private int misses = 0;

    public Cache(){

    }

    public void addToCacheCurrent(AirCondition airCondition){
        this.cacheCurrent.put(airCondition, LocalDateTime.now());
    }

    public void addToCacheDays(ArrayList<AirCondition> airConditions) { this.cacheDays.put(airConditions, LocalDateTime.now());};

    public boolean isExpired(LocalDateTime localDateTime){
        LocalDateTime now = LocalDateTime.now();
        long duration = Duration.between(now, localDateTime).getSeconds();
        return duration > TTL;
    }

    public AirCondition checkIfInCacheCurrent(Double latitude, Double longitude){  // so applicable para current
        log.info("in checkifincache");
        LocalDateTime now = LocalDateTime.now();
        log.info(String.valueOf(cacheCurrent.keySet().size()));
        for (AirCondition key : cacheCurrent.keySet()){
            log.info("hora key: " + String.valueOf(key.getHours()));
            log.info("hora agora: " + String.valueOf(now.getHour()));
            if (key.getLon().equals(longitude) && key.getLat().equals(latitude) && key.getHours()==now.getHour() && (!isExpired(cacheCurrent.get(key)))) {
                this.hits +=1;
                log.info("um hit");
                return key;
            }
            if (isExpired(cacheCurrent.get(key))){
                cacheCurrent.remove(key);
            }

        }
        log.info("um miss");
        this.misses +=1;
        return null;
    }

    public ArrayList<AirCondition> checkIfInCacheDays(Double latitude, Double longitude, LocalDateTime hoursStart, LocalDateTime hoursEnd){  // so applicable para current
        for (ArrayList<AirCondition> condicoesAr : cacheDays.keySet()){
            if (Objects.equals(condicoesAr.get(0).getLon(), longitude) && Objects.equals(condicoesAr.get(0).getLat(), latitude) && condicoesAr.get(0).getHours()== hoursStart.getHour() && condicoesAr.get(-1).getHours()==hoursEnd.getHour()){
                this.hits +=1;
                return condicoesAr;
            }
            log.info("uhhh...");
            if (isExpired(cacheDays.get(condicoesAr))){
                cacheDays.remove(condicoesAr);
            }
        }
        log.info("um miss");
        this.misses +=1;
        return null;
    }

}
