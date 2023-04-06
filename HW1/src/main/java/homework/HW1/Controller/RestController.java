package homework.HW1.Controller;

import homework.HW1.Model.AirCondition;
import homework.HW1.Service.AirConditionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.invoke.MethodHandles;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

@Controller
public class RestController {

    static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final AirConditionService airService;

    public RestController(AirConditionService service){
        this.airService = service;
    }

    @GetMapping("/current")
    public String getCurrentAirPollution(@RequestParam(value = "lat") Double lat, @RequestParam(value = "lon") Double lon, Model model){
        try {
            log.info("In current try mapping");
            AirCondition airConditions = airService.getAirCondition(lat, lon);
            model.addAttribute("airConditions", airConditions);
            return "index";
        }
        catch (Exception e){
            log.error("Not found");
            return "index";
        }
    }

    // forecast para 1 dia - hora a hora
    @GetMapping("/forecast")
    public String getForecastAirPollution(@RequestParam("lat") Double lat, @RequestParam("lon") Double lon, Model model){
        try{
            ArrayList<AirCondition> conditionsForecast = airService.getAirConditionByDates(lat, lon, LocalDateTime.now(ZoneOffset.UTC), LocalDateTime.now(ZoneOffset.UTC).plusDays(1));
            model.addAttribute("forecast", conditionsForecast);
            return "index";
        }
        catch (Exception e){
            return "index";
        }
    }

    @GetMapping("/by_dates")
    public String getAirPollutionByDays(@RequestParam("lat") Double lat,
                                        @RequestParam("lon") Double lon,
                                        @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                        @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                        Model model){

        try{
            log.info("in here");
            ArrayList<AirCondition> conditionsDates = airService.getAirConditionByDates(lat, lon, startDate, endDate);
            log.info(conditionsDates.toString());
            model.addAttribute("byDates", conditionsDates);
            return "index";
        }
        catch (Exception e){
            return "index";
        }


    }


}
