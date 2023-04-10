package homework.HW1.ServiceTest;

import homework.HW1.Cache.Cache;
import homework.HW1.Model.AirCondition;
import homework.HW1.Model.AirConditionResolver;
import homework.HW1.Service.AirConditionService;
import homework.HW1.connection.HttpClientAPI;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;

@ExtendWith(MockitoExtension.class)
public class ServiceTest {

    @Mock (lenient = true)
    Cache cache;

    @Mock (lenient = true)
    AirConditionResolver resolver;

    @InjectMocks
    AirConditionService service;

    AirCondition condition;

    ArrayList<AirCondition> conditions = new ArrayList<>();

    @BeforeEach
    public void setUp() throws URISyntaxException, IOException, ParseException {
        MockitoAnnotations.openMocks(this);
        condition = new AirCondition(50.0, -8.1, LocalDateTime.now(ZoneOffset.UTC), 226.97, 0.0, 2.01, 83.68, 1.03, 0.99, 1.81, 0.64);
        conditions.add(condition);

    }

    @Test
    void givenCurrentData_whenNotInCache_Return() throws URISyntaxException, IOException, ParseException {
        Mockito.when(resolver.currentAirPollution(anyDouble(), anyDouble())).thenReturn(Optional.of(condition));
        Mockito.when(cache.checkIfInCacheCurrent(anyDouble(), anyDouble())).thenReturn(null);
        AirCondition condition = service.getAirCondition(50.0, -8.1);
        assertEquals(condition, this.condition);
    }

    @Test
    void givenDatesData_whenNotInCache_Return() throws URISyntaxException, IOException, ParseException {
        Mockito.when(resolver.airPollutionDates(anyDouble(), anyDouble(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(conditions);
        Mockito.when(cache.checkIfInCacheDays(anyDouble(), anyDouble(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(null);

        ArrayList<AirCondition> airConditions = service.getAirConditionByDates(50.0, -8.1, LocalDateTime.now(), LocalDateTime.now());
        assertEquals(airConditions, conditions);
    }

    @Test
    void givenNoCurrentData_whenInCache_Return() throws URISyntaxException, IOException, ParseException {
        Mockito.when(resolver.currentAirPollution(anyDouble(), anyDouble())).thenReturn(null);
        Mockito.when(cache.checkIfInCacheCurrent(anyDouble(), anyDouble())).thenReturn(condition);
        AirCondition condition = service.getAirCondition(50.0, -8.1);
        assertEquals(condition, this.condition);
    }

    @Test
    void givenNoDatesData_whenInCache_Return() throws URISyntaxException, IOException, ParseException {
        Mockito.when(resolver.airPollutionDates(anyDouble(), anyDouble(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(null);
        Mockito.when(cache.checkIfInCacheDays(anyDouble(), anyDouble(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(conditions);

        ArrayList<AirCondition> airConditions = service.getAirConditionByDates(50.0, -8.1, LocalDateTime.now(), LocalDateTime.now());
        assertEquals(airConditions, conditions);
    }

    @Test
    void givenNothingPopulatedDays_ReturnNull() throws URISyntaxException, IOException, ParseException {
        Mockito.when(resolver.airPollutionDates(anyDouble(), anyDouble(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(null);
        Mockito.when(cache.checkIfInCacheDays(anyDouble(), anyDouble(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(null);

        ArrayList<AirCondition> airConditions = service.getAirConditionByDates(50.0, -8.1, LocalDateTime.now(), LocalDateTime.now());
        assertNull(airConditions);
    }

    @Test
    void givenNothingPopulatedCurrent_ReturnNull() throws URISyntaxException, IOException, ParseException {
        Mockito.when(resolver.airPollutionDates(anyDouble(), anyDouble(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(null);
        Mockito.when(cache.checkIfInCacheDays(anyDouble(), anyDouble(), any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(null);

        AirCondition condition = service.getAirCondition(50.0, -8.1);
        assertNull(condition);
    }





}
