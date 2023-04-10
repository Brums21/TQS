package lab3_2.tqsCars;

import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import lab3_2.tqsCars.data.CarRepository;
import lab3_2.tqsCars.model.Car;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
public class C_CarRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository repository;

    @Test
    void givenSetOfCars_returnAll() {
        Car carro1 = new Car("Mercedes", "AMG");
        Car carro2 = new Car("Toyota", "Avencis");
        Car carro3 = new Car("Porsche", "911");

        entityManager.persist(carro1);
        entityManager.persist(carro2);
        entityManager.persist(carro3);
        entityManager.flush();

        List<Car> cars = repository.findAll();

        assertThat(cars).hasSize(3).extracting(Car::getModel).containsOnly(carro1.getModel(), carro2.getModel(), carro3.getModel());     

    }

    @Test
    void whenFindCarById_thenReturnCar() {
        Car carro1 = new Car("Mercedes", "AMG");
        entityManager.persistAndFlush(carro1);

        Car carrox = repository.findByCarId(carro1.getCarId());
        assertThat(carrox).isNotNull();
        assertThat(carrox.getModel()).isEqualTo(carro1.getModel());
    }
}
