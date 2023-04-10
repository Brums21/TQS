package lab3_2.tqsCars;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import lab3_2.tqsCars.data.CarRepository;
import lab3_2.tqsCars.model.Car;
import lab3_2.tqsCars.services.CarManagerService;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class B_CarServiceTest {
    
    @Mock(lenient = true)
    private CarRepository repository;
    
    @InjectMocks
    private CarManagerService service;

    @BeforeEach
    public void setup(){
        Car carro1 = new Car("Mercedes", "AMG");
        Car carro2 = new Car("Toyota", "Avencis");
        Car carro3 = new Car("Porsche", "911");
        Car carro4 = new Car("DMW", "m3");
        carro1.setCarId(20L);

        List<Car> carros = Arrays.asList(carro1, carro2, carro3, carro4);

        Mockito.when(repository.findAll()).thenReturn(carros);
        Mockito.when(repository.findByCarId(carro1.getCarId())).thenReturn(carro1);

    }


    @Test
    void given4Cars_whengetAll_thenReturn4Records() {
        Car carro1 = new Car("Mercedes", "AMG");
        Car carro2 = new Car("Toyota", "Avencis");
        Car carro3 = new Car("Porsche", "911");
        Car carro4 = new Car("DMW", "m3");

        List<Car> carros = service.getAllCars();
        Mockito.verify(repository, VerificationModeFactory.times(1)).findAll();
        assertThat(carros).hasSize(4).extracting(Car::getModel).contains(carro1.getModel(), carro2.getModel(), carro3.getModel(), carro4.getModel());
    }

    @Test
    void whenValidId_thenCarShouldBeFound() {
        Car carro1 = service.getCarDetails(20L).get();
        assertThat(carro1.getModel()).isEqualTo("AMG");

        Mockito.verify(repository, VerificationModeFactory.times(1)).findByCarId(Mockito.anyLong());
    }
}
