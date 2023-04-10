package homework.HW1.UnitTest;

import homework.HW1.Model.AirCondition;
import homework.HW1.Model.AirConditionResolver;
import homework.HW1.Model.ConfigUtils;
import homework.HW1.connection.HttpClient;
import org.apache.http.client.utils.URIBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExternalAPIUnitTest {

    public static final String EXTERNAL_API = "https://api.openweathermap.org/data/2.5/air_pollution";

    @Mock
    HttpClient httpClient;

    @InjectMocks
    public AirConditionResolver airConditionResolver;

    @Test
    public void testWhenValid_CurrentAirPollution() throws URISyntaxException, IOException, ParseException {

        String apiKey = ConfigUtils.getPropertyFromConfig("key");

        URIBuilder uribuilder = new URIBuilder(EXTERNAL_API);

        uribuilder.addParameter("appid", apiKey);
        uribuilder.addParameter("lat", String.valueOf(50.0));
        uribuilder.addParameter("lon", String.valueOf(-8.1));

        String apiResponse = "{\"coord\":{\"lon\":-8.1,\"lat\":50},\"list\":[{\"main\":{\"aqi\":3},\"components\":{\"co\":220.3,\"no\":0.14,\"no2\":1.21,\"o3\":107.29,\"so2\":1.65,\"pm2_5\":10.54,\"pm10\":21.13,\"nh3\":0.9},\"dt\":1680970160}]}";

        when(httpClient.doHttpGet(uribuilder.build().toString())).thenReturn(apiResponse);
        Optional<AirCondition> result = airConditionResolver.currentAirPollution(50.0, -8.1);

        LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochSecond(1680970160), ZoneOffset.UTC);

        AirCondition expected = new AirCondition(50.0,-8.1, date, 220.3, 0.14, 1.21, 107.29, 1.65, 10.54, 21.13, 0.9);

        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals(expected.toString(), result.get().toString());

    }

    @Test
    public void testWhenValid_DaysAirCondition() throws URISyntaxException, IOException, ParseException {
        String apiKey = ConfigUtils.getPropertyFromConfig("key");

        URIBuilder uribuilder = new URIBuilder(EXTERNAL_API + "/history");

        LocalDateTime start = LocalDateTime.of(2023, 4, 1, 23,30,10);
        LocalDateTime end = LocalDateTime.of(2023, 4, 2, 10,30,10);

        uribuilder.addParameter("appid", apiKey);
        uribuilder.addParameter("lat", String.valueOf(50.0));
        uribuilder.addParameter("lon", String.valueOf(-8.1));
        uribuilder.addParameter("start", String.valueOf(start.toEpochSecond(ZoneOffset.UTC)));
        uribuilder.addParameter("end", String.valueOf(end.toEpochSecond(ZoneOffset.UTC)));

        String apiResponse = "{\"coord\":{\"lon\":-8.1,\"lat\":50},\"list\":[{\"main\":{\"aqi\":2},\"components\":{\"co\":226.97,\"no\":0,\"no2\":2.01,\"o3\":83.68,\"so2\":1.03,\"pm2_5\":0.99,\"pm10\":1.81,\"nh3\":0.64},\"dt\":1677715200},{\"main\":{\"aqi\":2},\"components\":{\"co\":226.97,\"no\":0,\"no2\":2.33,\"o3\":79.39,\"so2\":1.25,\"pm2_5\":1.67,\"pm10\":2.41,\"nh3\":0.57},\"dt\":1677718800}]}";

        when(httpClient.doHttpGet(uribuilder.build().toString())).thenReturn(apiResponse);

        List<AirCondition> airconditions = airConditionResolver.airPollutionDates(50.0, -8.1, start, end);

        LocalDateTime dateStart = LocalDateTime.ofInstant(Instant.ofEpochSecond(1677715200), ZoneOffset.UTC);
        LocalDateTime dateEnd = LocalDateTime.ofInstant(Instant.ofEpochSecond(1677718800), ZoneOffset.UTC);

        List<AirCondition> expected = new ArrayList<>();
        expected.add(new AirCondition(50.0, -8.1, dateStart, 226.97, 0.0, 2.01, 83.68, 1.03, 0.99, 1.81, 0.64));
        expected.add(new AirCondition(50.0, -8.1, dateEnd, 226.97, 0.0, 2.33, 79.39, 1.25, 1.67, 2.41, 0.57));

        Assertions.assertEquals(expected.toString(), airconditions.toString());

    }

    @Test
    public void testWhenNotValid_CurrentAirPollution() throws URISyntaxException, IOException, ParseException {

        String apiKey = ConfigUtils.getPropertyFromConfig("key");

        URIBuilder uribuilder = new URIBuilder(EXTERNAL_API);

        uribuilder.addParameter("appid", apiKey);
        uribuilder.addParameter("lat", String.valueOf(-5045.0));
        uribuilder.addParameter("lon", String.valueOf(-5045.0));

        String apiResponse = "{\"cod\":\"400\",\"message\":\"wrong latitude\"}";

        when(httpClient.doHttpGet(uribuilder.build().toString())).thenReturn(apiResponse);
        Optional<AirCondition> result = airConditionResolver.currentAirPollution(-5045.0, -5045.0);

        Assertions.assertEquals(Optional.empty(), result);

    }

    @Test
    public void testWhenInValid_DaysAirCondition() throws URISyntaxException, IOException, ParseException {
        String apiKey = ConfigUtils.getPropertyFromConfig("key");

        URIBuilder uribuilder = new URIBuilder(EXTERNAL_API + "/history");

        LocalDateTime start = LocalDateTime.of(2023, 4, 1, 23,30,10);
        LocalDateTime end = LocalDateTime.of(2023, 4, 2, 10,30,10);

        uribuilder.addParameter("appid", apiKey);
        uribuilder.addParameter("lat", String.valueOf(-5045.0));
        uribuilder.addParameter("lon", String.valueOf(-5045.0));
        uribuilder.addParameter("start", String.valueOf(start.toEpochSecond(ZoneOffset.UTC)));
        uribuilder.addParameter("end", String.valueOf(end.toEpochSecond(ZoneOffset.UTC)));

        String apiResponse = "{\"cod\":\"400\",\"message\":\"wrong latitude\"}";

        when(httpClient.doHttpGet(uribuilder.build().toString())).thenReturn(apiResponse);

        List<AirCondition> airconditions = airConditionResolver.airPollutionDates(-5045.0, -5045.0, start, end);

        Assertions.assertEquals(new ArrayList<>(), airconditions);
    }

}