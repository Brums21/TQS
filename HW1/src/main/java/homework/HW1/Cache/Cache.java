package homework.HW1.Cache;

import homework.HW1.Model.AirCondition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;

public class Cache {

    static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final long TTL = 240;

    public final HashMap<AirCondition, LocalDateTime> cacheCurrent = new HashMap<>();

    public final HashMap<ArrayList<AirCondition>, LocalDateTime> cacheDays = new HashMap<>();

    private int hits = 0;

    private int misses = 0;

    public Cache(){

    }


    public int getHits(){
        return this.hits;
    }

    public int getMisses(){
        return this.misses;
    }

    public void addToCacheCurrent(AirCondition airCondition){
        this.cacheCurrent.put(airCondition, LocalDateTime.now(ZoneOffset.UTC));
    }

    public void addToCacheDays(ArrayList<AirCondition> airConditions) { this.cacheDays.put(airConditions, LocalDateTime.now(ZoneOffset.UTC));};

    public boolean isExpired(LocalDateTime localDateTime){
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        long duration = Duration.between(now, localDateTime).getSeconds();
        return duration >= TTL;
    }

    public AirCondition checkIfInCacheCurrent(Double latitude, Double longitude){  // so applicable para current
        log.info("in check cache for current information");
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        log.info(String.valueOf(cacheCurrent.keySet().size()));
        for (AirCondition key : cacheCurrent.keySet()){
            log.info("hora na cache: " + String.valueOf(key.getHours()));
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
        long numbHoursStart;
        if (hoursStart.toEpochSecond(ZoneOffset.UTC)%3600 == 0 ){ //hora certa
            log.info("in ig");
            numbHoursStart = hoursStart.toEpochSecond(ZoneOffset.UTC)/3600;
        }
        else{
            log.info("in else");
            numbHoursStart = hoursStart.toEpochSecond(ZoneOffset.UTC)/3600 + 1;
        }

        for (ArrayList<AirCondition> condicoesAr : cacheDays.keySet()){

            // compare unix hours works well because it takes in consideration same day, month and year!
            // comparing normal hours could form some errors when the hours would be the same but days would be different
            long unixHoursStart = condicoesAr.get(0).getLocalDateTime().toEpochSecond(ZoneOffset.UTC)/3600;;
            long unixHoursEnd = condicoesAr.get(condicoesAr.size()-1).getLocalDateTime().toEpochSecond(ZoneOffset.UTC)/3600;

            log.info("Start " + unixHoursStart + " " + numbHoursStart);
            log.info("End " + unixHoursEnd + " " + hoursEnd.toEpochSecond(ZoneOffset.UTC)/3600);

            if (condicoesAr.get(0).getLon().equals(longitude) && condicoesAr.get(0).getLat().equals(latitude) && unixHoursStart == numbHoursStart && unixHoursEnd== hoursEnd.toEpochSecond(ZoneOffset.UTC)/3600){
                this.hits +=1;
                log.info("its in the cache - 1 hit!");
                return condicoesAr;
            }
            if (isExpired(cacheDays.get(condicoesAr))){
                log.info("some items in cache are expired... ill just remove them");
                cacheDays.remove(condicoesAr);
            }
        }
        log.info("not in cache - 1 miss");
        this.misses +=1;
        return null;
    }

}
