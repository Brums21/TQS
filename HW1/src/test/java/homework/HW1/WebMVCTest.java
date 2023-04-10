package homework.HW1;

import homework.HW1.Controller.RestController;
import homework.HW1.Model.AirCondition;
import homework.HW1.Service.AirConditionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RestController.class)
@ExtendWith(MockitoExtension.class)
public class WebMVCTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AirConditionService airConditionService;

    @Test
    public void shouldGetAirConditionsCurrentPage() throws Exception {

        AirCondition condition = new AirCondition();

        when(airConditionService.getAirCondition(Mockito.anyDouble(), Mockito.anyDouble())).thenReturn(condition);

        this.mockMvc.perform(get("/current")
                        .param("lat", "50.0")
                        .param("lon", "-8.1"))
                        .andExpect(status().isOk())
                        .andExpect(view().name("index"))
                        .andExpect(model().attributeExists("airConditions"))
                        .andExpect(model().attribute("airConditions", condition));
    }

    @Test
    public void shouldGetStatsThymeleafPage() throws Exception {

        this.mockMvc.perform(get("/stats"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("hitsMessage"))
                .andExpect(model().attributeExists("missesMessage"))
                .andExpect(model().attributeExists("requestMessage"));
    }

    @Test
    public void shouldGetForecast() throws Exception {

        ArrayList<AirCondition> condition = new ArrayList<>();
        condition.add(new AirCondition(50.0, -8.1, LocalDateTime.now(ZoneOffset.UTC), 226.97, 0.0, 2.01, 83.68, 1.03, 0.99, 1.81, 0.64));
        condition.add(new AirCondition(40.0, -8.1, LocalDateTime.now(ZoneOffset.UTC), 226.97, 0.0, 2.01, 83.68, 1.03, 0.99, 1.81, 0.64));
        when(airConditionService.getAirConditionByDates(Mockito.anyDouble(), Mockito.anyDouble(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(condition);

        this.mockMvc.perform(get("/forecast")
                        .param("lat", "50.0")
                        .param("lon", "-8.1"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("forecast"))
                .andExpect(model().attribute("forecast", condition));
    }

    @Test
    public void shouldGetByDates() throws Exception {

        ArrayList<AirCondition> condition = new ArrayList<>();
        condition.add(new AirCondition(50.0, -8.1, LocalDateTime.now(ZoneOffset.UTC), 226.97, 0.0, 2.01, 83.68, 1.03, 0.99, 1.81, 0.64));
        condition.add(new AirCondition(40.0, -8.1, LocalDateTime.now(ZoneOffset.UTC), 226.97, 0.0, 2.01, 83.68, 1.03, 0.99, 1.81, 0.64));
        when(airConditionService.getAirConditionByDates(Mockito.anyDouble(), Mockito.anyDouble(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(condition);

        this.mockMvc.perform(get("/by_dates")
                        .param("lat", "50.0")
                        .param("lon", "-8.1")
                        .param("startDate", "2023-04-01T22:23:22")
                        .param("endDate", "2023-04-01T23:23:22"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("byDates"))
                .andExpect(model().attribute("byDates", condition));
    }

}
