package homework.HW1.Cache;

import homework.HW1.Model.AirCondition;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;

public class Cache {

    private final long TTL = 120;

    private final HashMap<AirCondition, LocalDateTime> cache = new HashMap<>();

    private int hits = 0;

    private int misses = 0;

    public Cache(){

    }

    public void addToCache(AirCondition airCondition){
        this.cache.put(airCondition, LocalDateTime.now());
    }

    public boolean isExpired(LocalDateTime localDateTime){
        LocalDateTime now = LocalDateTime.now();
        long duration = Duration.between(now, localDateTime).getSeconds();
        return duration > TTL;
    }

    public AirCondition checkIfInCache(AirCondition airCondition){
        for (AirCondition key : cache.keySet()){
            if (key.getLon().equals(airCondition.getLon()) && key.getLat().equals(airCondition.getLat()) && (!isExpired(cache.get(key)))) {
                this.hits +=1;
                return key;
            }
            if (isExpired(cache.get(key))){
                cache.remove(key);
            }

        }
        this.misses +=1;
        return null;
    }

}
