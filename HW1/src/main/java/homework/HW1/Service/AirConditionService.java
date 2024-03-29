package homework.HW1.Service;

import homework.HW1.Cache.Cache;
import homework.HW1.Model.AirCondition;
import homework.HW1.Model.AirConditionResolver;
import homework.HW1.connection.HttpClientAPI;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class AirConditionService {

    private Cache cache = new Cache();

    static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private AirConditionResolver resolver = new AirConditionResolver(new HttpClientAPI());

    public AirConditionService(Cache cache, AirConditionResolver resolver){
        this.resolver = resolver;
        this.cache = cache;
    }

    public AirConditionService(){

    }

    public String getHits(){
        return String.valueOf(cache.getHits());
    }

    public String getMisses(){
        return String.valueOf(cache.getMisses());
    }

    public String getRequests(){
        return String.valueOf(cache.getMisses() + cache.getHits());
    }

    public AirCondition getAirCondition(Double latitude, Double longitude) throws URISyntaxException, IOException, ParseException {
        log.info("in service -> getAirCondition");
        AirCondition condition = cache.checkIfInCacheCurrent(latitude, longitude);
        if (condition!=null){
            return condition;
        }

        Optional<AirCondition> conditionAPI = resolver.currentAirPollution(latitude, longitude);
        log.info(conditionAPI.toString());
        if (conditionAPI.isPresent()){
            cache.addToCacheCurrent(conditionAPI.get());
            return conditionAPI.get();
        }
        log.info("couldn't find anything");
        return null;

    }

    public ArrayList<AirCondition> getAirConditionByDates(Double latitude, Double longitude, LocalDateTime start, LocalDateTime end) throws URISyntaxException, IOException, ParseException {

        ArrayList<AirCondition> cacheDays = cache.checkIfInCacheDays(latitude, longitude, start, end);

        if (cacheDays!=null){
            log.info("Its in the cache");
            return cacheDays;
        }

        ArrayList<AirCondition> conditions = resolver.airPollutionDates(latitude, longitude, start, end);
        cache.addToCacheDays(conditions);
        log.info("in try by dates");
        return conditions;

    }

}
