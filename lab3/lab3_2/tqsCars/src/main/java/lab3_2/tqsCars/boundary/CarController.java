package lab3_2.tqsCars.boundary;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lab3_2.tqsCars.model.Car;
import lab3_2.tqsCars.services.CarManagerService;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CarController {    // car REST controller

    private final CarManagerService carManagerService;

    public CarController(CarManagerService injectedCarManagerService) {
        this.carManagerService = injectedCarManagerService;
    }


    @PostMapping("/cars") public ResponseEntity<Car> createCar(@RequestBody Car oneCar) {
        HttpStatus status = HttpStatus.CREATED;
        Car saved = carManagerService.save(oneCar);
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping(path = "/cars",  produces = "application/json")
    public List<Car> getAllCars() {
        return carManagerService.getAllCars();
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable(value = "id") Long carId)
            throws ResourceNotFoundException {
        Car car = carManagerService.getCarDetails(carId)
                .orElseThrow(() -> new ResourceNotFoundException("Car not found for id: " + carId));
        return ResponseEntity.ok().body(car);
    }

}
