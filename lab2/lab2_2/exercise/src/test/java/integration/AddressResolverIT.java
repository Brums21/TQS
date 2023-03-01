package integration;

import connection.TqsBasicHttpClient;
import geocoding.Address;
import geocoding.AddressResolver;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class AddressResolverIT {

    private AddressResolver resolver;

    @BeforeEach
    public void init(){
        resolver = new AddressResolver( new TqsBasicHttpClient());
    }

    @Test
    public void whenGoodCoordidates_returnAddress() throws IOException, URISyntaxException, ParseException {

        //todo

        // repeat the same tests conditions from AddressResolverTest, without mocks
        
        Optional<Address> result = resolver.findAddressForLocation( 40.633116,-8.658784);
        Address expected = new Address( "Avenida João Jacinto de Magalhães", "Aveiro", "", "3810-149", null);
        
        assertEquals(expected, result.get());
    
    }

    @Test
    public void whenBadCoordidates_thenReturnNoValidAddrress() throws IOException, URISyntaxException, ParseException {

        //todo
        // repeat the same tests conditions from AddressResolverTest, without mocks
        Optional<Address> result = resolver.findAddressForLocation( -361,-361);
        
        assertThrows(NoSuchElementException.class, () -> {result.get();});
    }

}
