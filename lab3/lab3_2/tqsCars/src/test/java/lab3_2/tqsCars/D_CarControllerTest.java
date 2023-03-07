package lab3_2.tqsCars;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;

import lab3_2.tqsCars.data.CarRepository;
import lab3_2.tqsCars.model.Car;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@AutoConfigureTestDatabase
@TestPropertySource( locations = "classpath:application-integrationtest.properties")
public class D_CarControllerTest {
    
    @LocalServerPort
    int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository repository;

    @AfterEach
    public void resetDb() {
        repository.deleteAll();
    }

    @Test
    public void whenPostCars_thenCreateCars() throws Exception{
        Car carro1 = new Car("Mercedes", "AMG");
        ResponseEntity<Car> response = restTemplate.postForEntity("/api/cars", carro1, Car.class);

        List<Car> found = repository.findAll();
        assertThat(found).extracting(Car::getModel).containsOnly("AMG");
        
    }

    @Test
    public void givenCars_returnAllCars(){
        Car carro1 = new Car("Mercedes", "AMG");
        Car carro2 = new Car("Toyota", "Avencis");
        Car carro3 = new Car("Porsche", "911");
        repository.saveAndFlush(carro1);
        repository.saveAndFlush(carro2);
        repository.saveAndFlush(carro3);

        ResponseEntity<List<Car>> response = restTemplate.exchange("/api/cars", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {
        });

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).extracting(Car::getModel).containsExactly("AMG", "Avencis", "911");
    }
}
