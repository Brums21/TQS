package homework.HW1;

import homework.HW1.Cache.Cache;
import homework.HW1.Hw1Application;
import homework.HW1.Model.AirCondition;
import homework.HW1.Service.AirConditionService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.ui.ModelMap;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Objects;


import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = Hw1Application.class)
@AutoConfigureMockMvc
public class IntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    AirConditionService service;

    @AfterEach
    public void reset(){
        Cache cache = new Cache();
    }

    @Test
    public void whenCorrectInputCurrent_ReturnData() throws Exception{
        mockMvc.perform(get("/current")
                        .param("lat", "50.0")
                        .param("lon", "-8.1"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("airConditions"))
                .andExpect(model().attribute("airConditions", hasProperty("lat", is(50.0))))
                .andExpect(model().attribute("airConditions", hasProperty("NO2")));

    }

    @Test
    public void whenIncorrectInputCurrent_ReturnError() throws Exception {

        mockMvc.perform(get("/current")
                        .param("lat", "50sgv0")
                        .param("lon", "-8.1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errorMessage"));

    }

    @Test
    public void whenCorrectInputForecast_ReturnData() throws Exception {

        MvcResult result = mockMvc.perform(get("/forecast")
                        .param("lat", "50.0")
                        .param("lon", "-8.1"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("forecast"))
                .andReturn();

        ModelMap modelMap = Objects.requireNonNull(result.getModelAndView()).getModelMap();
        ArrayList<AirCondition> conditions = (ArrayList<AirCondition>) modelMap.get("forecast");

        assertEquals(24, conditions.size());
        assertEquals("50.0", conditions.get(0).getLat().toString());

    }

    @Test
    public void whenIncorrectInputForecast_ReturnError() throws Exception {

        mockMvc.perform(get("/forecast")
                        .param("lat", "50dsva0")
                        .param("lon", "-8.1"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errorMessage1"));

    }

    @Test
    public void whenCorrectInputDates_ReturnData() throws Exception {

        MvcResult result = mockMvc.perform(get("/by_dates")
                        .param("lat", "50.0")
                        .param("lon", "-8.1")
                        .param("startDate", "2023-03-01T00:00:00")
                        .param("endDate", "2023-03-01T02:00:00"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("byDates"))
                .andReturn();

        ModelMap modelMap = Objects.requireNonNull(result.getModelAndView()).getModelMap();
        ArrayList<AirCondition> conditions = (ArrayList<AirCondition>) modelMap.get("byDates");

        assertEquals(3, conditions.size());
        assertEquals("50.0", conditions.get(0).getLat().toString());

    }

    @Test void whenIncorrectLatOrLon_returnError() throws  Exception {
        mockMvc.perform(get("/by_dates")
                        .param("lat", "50dsva0")
                        .param("lon", "-8.1")
                        .param("startDate", "2023-03-01T00:00:00")
                        .param("endDate", "2023-03-01T02:00:00"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("errorMessage2", "Only numbers accepted in the inputs"));
    }

    @Test void whenIncorrectDates_returnError() throws  Exception {
        mockMvc.perform(get("/by_dates")
                        .param("lat", "50.0")
                        .param("lon", "-8.1")
                        .param("startDate", "2023-gbiyub1T00:0o:00")
                        .param("endDate", "2023-03-01T02:00:00"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("errorMessage2", "Date format is incorrect"));
    }

}
