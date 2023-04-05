package homework.HW1.Controller;

import homework.HW1.Model.AirCondition;
import homework.HW1.Service.AirConditionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.util.ArrayList;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("air_pollution")
public class RestController {

    static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final AirConditionService airService;

    public RestController(AirConditionService service){
        this.airService = service;
    }

    @GetMapping("/current")
    public ResponseEntity<AirCondition> getCurrentAirPollution(@RequestParam("lat") Double lat, @RequestParam("lon") Double lon){
        try {
            log.info("In current try mapping");
            AirCondition condition = airService.getAirCondition(lat, lon);
            return ResponseEntity.ok().body(condition);
        }
        catch (Exception e){
            log.error("Not found");
            return ResponseEntity.notFound().build();
        }
    }

    // forecast para 7 dias
    @GetMapping("/forecast")
    public ResponseEntity<ArrayList<AirCondition>> getForecastAirPollution(@RequestParam("lat") Double lat, @RequestParam("lon") Double lon){
        try{
            ArrayList<AirCondition> conditions = airService.getAirConditionByDates(lat, lon, LocalDateTime.now(), LocalDateTime.now().plusDays(7));
            return ResponseEntity.ok().body(conditions);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/by_dates")
    public ResponseEntity<ArrayList<AirCondition>> getAirPollutionByDays(@RequestParam("lat") Double lat,
                                                                         @RequestParam("lon") Double lon,
                                                                         @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                                                         @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate){
        try{
            ArrayList<AirCondition> conditions = airService.getAirConditionByDates(lat, lon, startDate, endDate);
            return ResponseEntity.ok().body(conditions);
        }
        catch (Exception e){
            return ResponseEntity.notFound().build();
        }


    }


}
