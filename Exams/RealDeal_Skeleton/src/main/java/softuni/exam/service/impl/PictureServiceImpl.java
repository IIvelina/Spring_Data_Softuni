package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.Car;
import softuni.exam.models.Picture;
import softuni.exam.models.dto.jsons.PictureSeedDto;
import softuni.exam.repository.CarRepository;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Service
public class PictureServiceImpl implements PictureService {
    private final PictureRepository pictureRepository;
    private static final String FILE_PATH = "src/main/resources/files/json/pictures.json";

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    private final CarService carService;

    @Autowired
    public PictureServiceImpl(PictureRepository pictureRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, CarService carService) {
        this.pictureRepository = pictureRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;

        this.carService = carService;
    }


    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importPictures() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson
                        .fromJson(readPicturesFromFile(), PictureSeedDto[].class))
                .filter(pictureSeedDto -> {
                    boolean isValid = validationUtil.isValid(pictureSeedDto);
                    sb.append(isValid ? String.format("Successfully import picture - %s",
                                    pictureSeedDto.getName())
                                    : "Invalid picture")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(pictureSeedDto -> {
                    Picture picture = modelMapper.map(pictureSeedDto, Picture.class);
                    Optional<Car> optionalCar = Optional.ofNullable(carService.findById(pictureSeedDto.getCar()));
                    if (optionalCar.isPresent()) {
                        picture.setCar(optionalCar.get());
                        return picture;
                    } else {
                        // Обработка на липсващата кола
                        return null; // или можете да хвърлите изключение или да направите друго, което е подходящо за вашето приложение
                    }
                })
                .filter(Objects::nonNull) // Филтрирайте null обектите, ако върнете null за липсваща кола
                .forEach(pictureRepository::save);

        return sb.toString();
    }
}
