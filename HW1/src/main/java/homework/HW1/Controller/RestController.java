package homework.HW1.Controller;

import homework.HW1.Model.AirCondition;
import homework.HW1.Service.AirConditionService;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

@Controller
public class RestController {

    static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final AirConditionService airService;

    public RestController(AirConditionService service){
        this.airService = service;
    }

    @GetMapping("/stats")
    public String getStats(Model model){
        model.addAttribute("hitsMessage", "Numbers of hits: " + airService.getHits());
        model.addAttribute("missesMessage", "Numbers of misses: " + airService.getMisses());
        model.addAttribute("requestMessage", "Numbers of requests: " + airService.getRequests());
        return "index";
    }

    @GetMapping("/current")
    public String getCurrentAirPollution(@RequestParam(value = "lat") String lat, @RequestParam(value = "lon") String lon, Model model){
        try {
            Double lati = Double.parseDouble(lat);
            Double longi = Double.parseDouble(lon);
            log.info("In current get mapping");
            AirCondition airConditions = airService.getAirCondition(lati, longi);
            log.info("done...");
            model.addAttribute("airConditions", airConditions);
            return "index";
        }
        catch (NumberFormatException e){
            model.addAttribute("errorMessage", "Only numbers accepted in the inputs");
            return "index";
        }
        catch (URISyntaxException | IOException | ParseException e) {
            model.addAttribute("errorMessage", "Error retrieving data");
            return "index";
        }
    }

    // forecast para 1 dia - hora a hora
    @GetMapping("/forecast")
    public String getForecastAirPollution(@RequestParam("lat") String lat, @RequestParam("lon") String lon, Model model){
        try{
            Double lati = Double.parseDouble(lat);
            Double longi = Double.parseDouble(lon);
            ArrayList<AirCondition> conditionsForecast = airService.getAirConditionByDates(lati, longi, LocalDateTime.now(ZoneOffset.UTC), LocalDateTime.now(ZoneOffset.UTC).plusDays(1));
            model.addAttribute("forecast", conditionsForecast);
            return "index";
        }
        catch (NumberFormatException e){
            model.addAttribute("errorMessage1", "Only numbers accepted in the inputs");
            return "index";
        } catch (URISyntaxException | IOException | ParseException e) {
            model.addAttribute("errorMessage1", "Error retrieving data");
            return "index";
        }
    }

    @GetMapping("/by_dates")
    public String getAirPollutionByDays(@RequestParam("lat") String lat,
                                        @RequestParam("lon") String lon,
                                        @RequestParam("startDate") String startDate,
                                        @RequestParam("endDate") String endDate,
                                        Model model){

        try{
            Double lati = Double.parseDouble(lat);
            Double longi = Double.parseDouble(lon);
            LocalDateTime start = LocalDateTime.parse(startDate);
            LocalDateTime end = LocalDateTime.parse(endDate);
            ArrayList<AirCondition> conditionsDates = airService.getAirConditionByDates(lati, longi, start, end);
            model.addAttribute("byDates", conditionsDates);
            return "index";
        }
        catch (NumberFormatException e){
            model.addAttribute("errorMessage2", "Only numbers accepted in the inputs");
            return "index";
        }
        catch (DateTimeParseException e){
            model.addAttribute("errorMessage2", "Date format is incorrect");
            return "index";
        } catch (URISyntaxException | IOException | ParseException e) {
            model.addAttribute("errorMessage2", "Error retrieving data");
            return "index";
        }


    }


}
