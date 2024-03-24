package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.Car;
import softuni.exam.models.dto.jsons.CarSeedDto;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;


@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private static final String FILE_PATH = "src/main/resources/files/json/cars.json";

    @Autowired
    public CarServiceImpl(CarRepository carRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;

    }

    @Override
    public String readCarsFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));

    }



   /* @Override
    public String importCars() throws IOException {

        StringBuilder sb = new StringBuilder();

        Arrays.stream( gson
                .fromJson(readCarsFileContent(), CarSeedDto[].class))
                .filter(carSeedDto -> {
                    boolean isValid = validationUtil.isValid(carSeedDto);
                    sb.append(isValid ?
                            String.format("Successfully imported car - %s - %s",
                                   carSeedDto.getMake(), carSeedDto.getModel())
                            :"Invalid car")
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(carSeedDto -> modelMapper.map(carRepository, Car.class))
                .forEach(carRepository::save);

        return sb.toString();
    }

    */

    @Override
    public String importCars() throws IOException {
        StringBuilder sb = new StringBuilder();
        CarSeedDto[] carSeedDtos = gson.fromJson(readCarsFileContent(), CarSeedDto[].class);

        for (CarSeedDto carSeedDto : carSeedDtos) {
            if (!validationUtil.isValid(carSeedDto)) {
                sb.append("Invalid car").append(System.lineSeparator());
                continue;
            }

            Car car = modelMapper.map(carSeedDto, Car.class);

            // Проверка дали колата вече съществува в базата данни
            if (carRepository.findByMake(car.getMake()) != null) {
                sb.append(String.format("Car already exists: %s - %s",
                        car.getMake(), car.getModel())).append(System.lineSeparator());
                continue;
            }

            carRepository.save(car);
            sb.append(String.format("Successfully imported car - %s - %s",
                    car.getMake(), car.getModel())).append(System.lineSeparator());
        }

        return sb.toString();
    }

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {
        StringBuilder sb = new StringBuilder();
        carRepository.findCarsOrderByPicturesCountThenByMake()
                .forEach(car -> {
                    sb.append(String.format("Car make - %s, model - %s\n" +
                            "\tKilometers - %d\n" +
                            "\tRegistered on - %s\n" +
                            "\tNumber of pictures - %d\n",
                            car.getMake(), car.getModel(),
                            car.getKilometers(),
                            car.getRegisteredOn(),
                            car.getPictures().size()))
                            .append(System.lineSeparator());
                });
        return sb.toString();    }

    @Override
    public Car findById(Long id) {
        return carRepository.findById(id).orElse(null);
    }

}
