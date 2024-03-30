package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xmls.VolcanologistRootDto;
import softuni.exam.models.dto.xmls.VolcanologistSeedDto;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.entity.Volcanologist;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.repository.VolcanologistRepository;
import softuni.exam.service.VolcanologistService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

// TODO: Implement all methods
@Service
public class VolcanologistServiceImpl implements VolcanologistService {

    private static final String FILE_PATH = "src/main/resources/files/xml/volcanologists.xml";

    private final VolcanologistRepository volcanologistRepository;

    private final XmlParser xmlParser;

    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    private final VolcanoRepository volcanoRepository;

    @Autowired
    public VolcanologistServiceImpl(VolcanologistRepository volcanologistRepository, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper, VolcanoRepository volcanoRepository) {
        this.volcanologistRepository = volcanologistRepository;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.volcanoRepository = volcanoRepository;
    }


    @Override
    public boolean areImported() {
        return this.volcanologistRepository.count() > 0;
    }

    @Override
    public String readVolcanologistsFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importVolcanologists() throws IOException, JAXBException {
        VolcanologistRootDto volcanologistRootDto =
                this.xmlParser.unmarchalFromFile(FILE_PATH, VolcanologistRootDto.class);

        StringBuilder sb = new StringBuilder();
        System.out.println();
        for (VolcanologistSeedDto volcanologistSeedDto : volcanologistRootDto.getVolcanologistSeedDtos()) {

            Optional<Volcanologist> optVolcanologist =
                    this.volcanologistRepository
                            .findByFirstNameAndLastName(volcanologistSeedDto.getFirstName(), volcanologistSeedDto.getLastName());

            Optional<Volcano> optVolcano = this.volcanoRepository.findById(volcanologistSeedDto.getVolcano());



            if (!this.validationUtil.isValid(volcanologistSeedDto) || optVolcanologist.isPresent() || optVolcano.isEmpty()){
                sb.append("Invalid volcanologist\n");
                continue;
            }

            Volcanologist volcanologist = this.modelMapper.map(volcanologistSeedDto, Volcanologist.class);

            volcanologist.setVolcano(optVolcano.get());

            this.volcanologistRepository.saveAndFlush(volcanologist);

            sb.append(String.format("Successfully imported volcanologist %s %s\n",
                    volcanologist.getFirstName(),
                    volcanologist.getLastName()));


        }


        return sb.toString();
    }
}