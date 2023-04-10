package lab3_2.tqsCars;

import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.Optional;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import lab3_2.tqsCars.boundary.CarController;
import lab3_2.tqsCars.model.Car;
import lab3_2.tqsCars.services.CarManagerService;

@WebMvcTest(CarController.class)
class A_CarRestControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService service;

    @BeforeEach
    public void setup() throws Exception {
    }

    @Test
    public void whenPostCar_thenCreateCar() throws Exception{
        Car carro1 = new Car("Mercedes", "AMG");
        
        when(service.save(Mockito.any())).thenReturn(carro1);

        mvc.perform(
            post("/api/cars")
            .contentType(MediaType.APPLICATION_JSON)
            .content(JsonUtils.toJsonFunc(carro1))
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.maker", is("Mercedes")));

        verify(service, times(1)).save(Mockito.any());
    }

    @Test
    void whenManyCars_returnAll() throws Exception {
        Car carro1 = new Car("Mercedes", "AMG");
        Car carro2 = new Car("Toyota", "Avencis");
        Car carro3 = new Car("Porsche", "911");
        Car carro4 = new Car("DMW", "m3");

        when(service.getAllCars()).thenReturn(Arrays.asList(carro1, carro2, carro3, carro4));

        mvc.perform(
            get("/api/cars")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", hasSize(4)))
            .andExpect(jsonPath("$[0].model", is(carro1.getModel())))
            .andExpect(jsonPath("$[1].model", is(carro2.getModel())))
            .andExpect(jsonPath("$[2].model", is(carro3.getModel())))
            .andExpect(jsonPath("$[3].model", is(carro4.getModel())));
        
        verify(service, times(1)).getAllCars();
    }

    @Test
    void whenCar_returnID() throws Exception {
        Car carro1 = new Car("Mercedes", "AMG");

        when(service.getCarDetails(Mockito.any())).thenReturn(Optional.of(carro1));

        mvc.perform(
            get("/api/cars/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.model", is(carro1.getModel())));

        verify(service, times(1)).getCarDetails(Mockito.anyLong());
    }
    





}
