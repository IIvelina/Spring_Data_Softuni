package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsons.CountrySeedDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

// TODO: Implement all methods
@Service
public class CountryServiceImpl implements CountryService {

    private static final String FILE_PATH = "src/main/resources/files/json/countries.json";

    private final CountryRepository countryRepository;

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public CountryServiceImpl(CountryRepository countryRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }


    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importCountries() throws IOException {
        CountrySeedDto[] countrySeedDtos =
                this.gson.fromJson(readCountriesFromFile(), CountrySeedDto[].class);

        StringBuilder sb = new StringBuilder();

        for (CountrySeedDto countrySeedDto : countrySeedDtos) {
            Optional<Country> optCountry =
                    this.countryRepository.findByName(countrySeedDto.getName());

            if (!this.validationUtil.isValid(countrySeedDto) || optCountry.isPresent()){
                sb.append("Invalid country\n");
                continue;
            }

            Country country = this.modelMapper.map(countrySeedDto, Country.class);

            this.countryRepository.saveAndFlush(country);

            sb.append(String.format("Successfully imported country %s - %s\n",
                    country.getName(),
                    country.getCapital()));
        }

        return sb.toString();
    }
}
