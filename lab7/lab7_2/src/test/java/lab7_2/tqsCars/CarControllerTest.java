package lab7_2.tqsCars;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.module.mockmvc.RestAssuredMockMvc;
import lab3_2.tqsCars.boundary.CarController;
import lab3_2.tqsCars.model.Car;
import lab3_2.tqsCars.services.CarManagerService;

@WebMvcTest(CarController.class)
public class CarControllerTest {
    
    @MockBean
    CarManagerService service;

    @Autowired
    private MockMvc mvc;

    @Before
    public void setup(){
        RestAssuredMockMvc.mockMvc(mvc);
        Car carro = new Car(1L, "Opel", "Astra");
    }
    

    @Test
    public void testing() {
        
        
    }
}
