package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.jsons.VolcanoSeedDto;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.entity.VolcanoType;
import softuni.exam.repository.CountryRepository;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.service.VolcanoService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class VolcanoServiceImpl implements VolcanoService {

    private static final String FILE_PATH = "src/main/resources/files/json/volcanoes.json";

    private final VolcanoRepository volcanoRepository;

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final CountryRepository countryRepository;

    @Autowired
    public VolcanoServiceImpl(VolcanoRepository volcanoRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil, CountryRepository countryRepository) {
        this.volcanoRepository = volcanoRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.countryRepository = countryRepository;
    }


    @Override
    public boolean areImported() {
        return this.volcanoRepository.count() > 0;
    }

    @Override
    public String readVolcanoesFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importVolcanoes() throws IOException {
        VolcanoSeedDto[] volcanoSeedDtos =
                this.gson.fromJson(readVolcanoesFileContent(), VolcanoSeedDto[].class);

        StringBuilder sb = new StringBuilder();


        for (VolcanoSeedDto volcanoSeedDto : volcanoSeedDtos) {

            Optional<Volcano> optVolcano =
                    this.volcanoRepository.findByName(volcanoSeedDto.getName());

            Optional<Country> optCountry =
                    this.countryRepository.findById(volcanoSeedDto.getCountry());

            if (!this.validationUtil.isValid(volcanoSeedDto) || optVolcano.isPresent() || optCountry.isEmpty()) {
                sb.append("Invalid volcano\n");
                continue;
            }

            System.out.println();

            Volcano volcano = this.modelMapper.map(volcanoSeedDto, Volcano.class);

            volcano.setCountry(optCountry.get());

            this.volcanoRepository.saveAndFlush(volcano);

            sb.append(String.format("Successfully imported volcano %s of type %s\n",
                    volcano.getName(),
                    volcano.getVolcanoType()));
        }

        return sb.toString();
    }

    @Override
    public String exportVolcanoes() {

        StringBuilder sb = new StringBuilder();

        List<Object[]> volcanoRecords = this.volcanoRepository.findActiveVolcanoesAbove3000mWithLastEruption();

        for (Object[] record : volcanoRecords) {
            String volcanoName = (String) record[0];
            String countryName = (String) record[1];
            int elevation = (int) record[2];
            LocalDate lastEruption = (LocalDate) record[3];

            String formattedInfo = formatVolcanoInfo(volcanoName, countryName, elevation, lastEruption);
            sb.append(formattedInfo).append("\n");
        }

        return sb.toString();
    }

    private String formatVolcanoInfo(String name, String country, int elevation, LocalDate lastEruption) {
        return String.format("Volcano: %s\n   *Located in: %s\n   **Elevation: %d\n   ***Last eruption on: %s",
                name, country, elevation, lastEruption);
    }
}
