package homework.HW1.Model;

import homework.HW1.connection.HttpClient;
import homework.HW1.connection.HttpClientAPI;
import org.apache.http.client.utils.URIBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.http.ParseException;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Optional;

public class AirConditionResolver {

    static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static final String EXTERNAL_API = "https://api.openweathermap.org/data/2.5/air_pollution";

    private final String apiKey;

    private final HttpClient httpClient;

    private URIBuilder uriBuilder;

    public AirConditionResolver(HttpClient httpClient) {
        this.httpClient = httpClient;
        this.apiKey = ConfigUtils.getPropertyFromConfig("key");
    }

    public Optional<AirCondition> currentAirPollution(Double latitude, Double longitude) throws URISyntaxException, IOException, ParseException, org.json.simple.parser.ParseException {

        URIBuilder uriBuilder = new URIBuilder(EXTERNAL_API);

        this.uriBuilder = uriBuilder;

        uriBuilder.addParameter("appid", this.apiKey);
        uriBuilder.addParameter("lat", latitude.toString());
        uriBuilder.addParameter("lon", longitude.toString());

        String apiResponse = this.httpClient.doHttpGet(uriBuilder.build().toString());
        log.info("remote response: " + apiResponse);


        // get root object from JSON
        JSONObject obj = (JSONObject) new JSONParser().parse(apiResponse);

        if ((JSONArray)obj.get("list") == null){
            return Optional.empty();
        }

        if (((JSONArray)obj.get("list")).isEmpty()) {
            return Optional.empty();
        }else {
            // get the first element of the results array
            obj = (JSONObject) ((JSONArray) obj.get("list")).get(0);

            LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochSecond((long)obj.get("dt")), ZoneOffset.UTC);

            if (obj.isEmpty()) {
                return Optional.empty();
            } else {
                //Double lat, Double lon, Long CO, Long NO, Long NO2, Long o3, Long SO2, Long PM2_5, Long PM10, Long NH3
                obj = (JSONObject) obj.get("components");

                Double CO = ((Number) obj.get("co")).doubleValue();
                Double NO = ((Number) obj.get("no")).doubleValue();
                Double NO2 = ((Number) obj.get("no2")).doubleValue();
                Double o3 = ((Number) obj.get("o3")).doubleValue();
                Double SO2 = ((Number) obj.get("so2")).doubleValue();
                Double PM2_5 = ((Number) obj.get("pm2_5")).doubleValue();
                Double PM10 = ((Number) obj.get("pm10")).doubleValue();
                Double NH3 = ((Number) obj.get("nh3")).doubleValue();

                log.info(Optional.of(new AirCondition(latitude, longitude, date, CO, NO, NO2, o3, SO2, PM2_5, PM10, NH3)).toString());
                return Optional.of(new AirCondition(latitude, longitude, date, CO, NO, NO2, o3, SO2, PM2_5, PM10, NH3));
            }
        }
    
    }

    public ArrayList<AirCondition> airPollutionDates(Double latitude, Double longitude, LocalDateTime start, LocalDateTime end) throws URISyntaxException, IOException, ParseException, org.json.simple.parser.ParseException {

        ArrayList<AirCondition> array = new ArrayList<>();

        URIBuilder uriBuilder = new URIBuilder(EXTERNAL_API + "/history");

        this.uriBuilder = uriBuilder;

        uriBuilder.addParameter("appid", this.apiKey);
        uriBuilder.addParameter("lat", latitude.toString());
        uriBuilder.addParameter("lon", longitude.toString());
        uriBuilder.addParameter("start", String.valueOf(start.toEpochSecond(ZoneOffset.UTC)));
        uriBuilder.addParameter("end", String.valueOf(end.toEpochSecond(ZoneOffset.UTC)));

        String apiResponse = this.httpClient.doHttpGet(uriBuilder.build().toString());
        log.info("remote response: " + apiResponse);


        // get root object from JSON
        JSONObject obj = (JSONObject) new JSONParser().parse(apiResponse);

        if ((JSONArray)obj.get("list") == null){
            return array;
        }
        if( ((JSONArray)obj.get("list")).isEmpty()) {
            return array;
        }else {
            for (int i = 0; i < ((JSONArray)obj.get("list")).size(); i++){

                JSONObject object = (JSONObject) ((JSONArray) obj.get("list")).get(i);

                LocalDateTime date = LocalDateTime.ofInstant(Instant.ofEpochSecond((long)object.get("dt")), ZoneOffset.UTC);

                //Double lat, Double lon, Long CO, Long NO, Long NO2, Long o3, Long SO2, Long PM2_5, Long PM10, Long NH3
                object = (JSONObject) object.get("components");

                Double CO = ((Number) object.get("co")).doubleValue();
                Double NO = ((Number) object.get("no")).doubleValue();
                Double NO2 = ((Number) object.get("no2")).doubleValue();
                Double o3 = ((Number) object.get("o3")).doubleValue();
                Double SO2 = ((Number) object.get("so2")).doubleValue();
                Double PM2_5 = ((Number) object.get("pm2_5")).doubleValue();
                Double PM10 = ((Number) object.get("pm10")).doubleValue();
                Double NH3 = ((Number) object.get("nh3")).doubleValue();
                array.add(Optional.of(new AirCondition(latitude, longitude, date, CO, NO, NO2, o3, SO2, PM2_5, PM10, NH3)).get());
            }

        }

        return array;
    }
}