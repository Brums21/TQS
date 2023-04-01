package homework.HW1.Model;

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
import java.util.Optional;

public class AirConditionResolver {

    static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static final String EXTERNAL_API = "https://api.openweathermap.org/data/2.5/air_pollution";

    private final String apiKey;

    private static final int API_SUCCESS = 0;
    private final HttpClientAPI httpClient;

    public URIBuilder uriBuilder = new URIBuilder(EXTERNAL_API);

    public AirConditionResolver(HttpClientAPI httpClient) {
        this.httpClient = httpClient;
        this.apiKey = ConfigUtils.getPropertyFromConfig("key");
    }

    public void initialize(String latitude, String longitude) throws org.json.simple.parser.ParseException {
        uriBuilder.addParameter("appid", this.apiKey);
        uriBuilder.addParameter("lat", latitude);
        uriBuilder.addParameter("lon", longitude);

        String apiResponse = this.httpClient.doHttpGet(uriBuilder.build().toString());
        log.debug("remote response: " + apiResponse);

        // get root object from JSON
        JSONObject obj = (JSONObject) new JSONParser().parse(apiResponse);
    }

    public Optional<AirCondition> currentAirPollution(Double latitude, Double longitude) throws URISyntaxException, IOException, ParseException, org.json.simple.parser.ParseException {

        initialize(latitude.toString(), longitude.toString());

        String apiResponse = this.httpClient.doHttpGet(uriBuilder.build().toString());
        log.debug("remote response: " + apiResponse);

        // get root object from JSON
        JSONObject obj = (JSONObject) new JSONParser().parse(apiResponse);

        if( ((JSONArray)obj.get("list")).isEmpty()) {
            return Optional.empty();
        }else {
            // get the first element of the results array
            obj = (JSONObject) ((JSONArray) obj.get("list")).get(0);

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
                return Optional.of(new AirCondition(latitude, longitude, CO, NO, NO2, o3, SO2, PM2_5, PM10, NH3));
            }
        }
    }

    public Optional<AirCondition> airPollutionTimeStamp(Double latitude, Double longitude, ) throws URISyntaxException, IOException, ParseException, org.json.simple.parser.ParseException {

        initialize(latitude.toString(), longitude.toString());



        if( ((JSONArray)obj.get("list")).isEmpty()) {
            return Optional.empty();
        }else {
            // get the first element of the results array
            obj = (JSONObject) ((JSONArray) obj.get("list")).get(0);

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
                return Optional.of(new AirCondition(latitude, longitude, CO, NO, NO2, o3, SO2, PM2_5, PM10, NH3));
            }
        }
    }
}