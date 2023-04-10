package geocoding;

import connection.ISimpleHttpClient;

import org.apache.http.client.utils.URIBuilder;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Formatter;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddressResolverTest {

    public static final String MAPQUESTAPI_GEOCODING = "https://www.mapquestapi.com/geocoding/v1/reverse";

    @Mock 
    ISimpleHttpClient httpClient;

    @InjectMocks 
    AddressResolver resolver;

    @Test
    void whenResolveDetiGps_returnJacintoMagalhaeAddress() throws ParseException, IOException, URISyntaxException {

        //todo: implement test; remove Disabled annotation
        String apiKey = ConfigUtils.getPropertyFromConfig("mapquest_key");

        URIBuilder uriBuilder = new URIBuilder(MAPQUESTAPI_GEOCODING);
        uriBuilder.addParameter("key", apiKey);
        uriBuilder.addParameter("location", (new Formatter()).format(Locale.US, "%.6f,%.6f", 40.633116,-8.658784).toString());


        String apiResponse = "{\"info\":{\"statuscode\":0," +
        "\"copyright\": {\"text\":\"\\u00A9 2021 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"\\u00A9 2021 MapQuest, Inc.\"}," +
        "\"messages\":[]},\"options\":{\"maxResults\":1,\"thumbMaps\":true,\"ignoreLatLngInput\":false}," +
        "\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":40.6318,\"lng\":-8.658}}," +
        "\"locations\":[{\"street\":\"Avenida João Jacinto de Magalhães\",\"adminArea6\":\"\",\"adminArea6Type\":\"Neighborhood\",\"adminArea5\":\"Aveiro\",\"adminArea5Type\":\"City\",\"adminArea4\":\"\",\"adminArea4Type\":\"County\",\"adminArea3\":\"\",\"adminArea3Type\":\"State\",\"adminArea1\":\"PT\",\"adminArea1Type\":\"Country\",\"postalCode\":\"3810-149\",\"geocodeQualityCode\":\"P1AAA\",\"geocodeQuality\":\"POINT\",\"dragPoint\":false,\"sideOfStreet\":\"N\",\"linkId\":\"0\",\"unknownInput\":\"\",\"type\":\"s\",\"latLng\":{\"lat\":40.631803,\"lng\":-8.657881}," +
        "\"displayLatLng\":{\"lat\":40.631803,\"lng\":-8.657881}," +
        "\"mapUrl\":\"http://open.mapquestapi.com/staticmap/v5/map?key=uXSAVwYWbf9tJmsjEGHKKAo0gOjZfBLQ&type=map&size=225,160&locations=40.6318025,-8.657881|marker-sm-50318A-1&scalebar=true&zoom=15&rand=-98728279\",\"roadMetadata\":null}]}]}";

        when(httpClient.doHttpGet(uriBuilder.build().toString())).thenReturn(apiResponse);

        // will crash for now...need to set the resolver before using it
        Optional<Address> result = resolver.findAddressForLocation( 40.633116,-8.658784);
        
        //return
        Address expected = new Address( "Avenida João Jacinto de Magalhães", "Aveiro", "", "3810-149", null);

        assertTrue( result.isPresent());
        assertEquals( expected, result.get());

    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddress() throws IOException, URISyntaxException, ParseException {

        ///todo: implement test
        String apiKey = ConfigUtils.getPropertyFromConfig("mapquest_key");

        URIBuilder uriBuilder = new URIBuilder(MAPQUESTAPI_GEOCODING);
        uriBuilder.addParameter("key", apiKey);
        uriBuilder.addParameter("location", (new Formatter()).format(Locale.US, "%.6f,%.6f", -361.00000,-361.000000).toString());

        String jsonexpected =
        "{\"info\":{\"statuscode\":0," +
         "\"copyright\":{\"text\":\"\\u00A9 2021 MapQuest, Inc.\",\"imageUrl\":\"http://api.mqcdn.com/res/mqlogo.gif\",\"imageAltText\":\"\\u00A9 2021 MapQuest, Inc.\"}," +
         "\"messages\":[]}," +
         "\"options\":{\"maxResults\":1,\"thumbMaps\":true,\"ignoreLatLngInput\":false}," +
         "\"results\":[{\"providedLocation\":{\"latLng\":{\"lat\":-361.0,\"lng\":-361}}," +
         "\"locations\":[]}]}";

        when(httpClient.doHttpGet(uriBuilder.build().toString())).thenReturn(jsonexpected);

        Optional<Address> result = resolver.findAddressForLocation( -361,-361);
        // verify no valid result
        assertFalse( result.isPresent());

    }
}