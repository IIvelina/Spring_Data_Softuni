package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.Car;
import softuni.exam.models.Offer;
import softuni.exam.models.dto.xmls.OffersRootDto;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.OfferService;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private static final String FILE_PATH = "src/main/resources/files/xml/offers.xml";

    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    private final XmlParser xmlParser;

    private final CarService carService;

    private final SellerService sellerService;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, CarService carService, SellerService sellerService) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.carService = carService;
        this.sellerService = sellerService;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        xmlParser
                .fromFile(FILE_PATH, OffersRootDto.class)
                .getOfferSeedDtos()
                .stream()
                .filter(offerSeedDto -> {
                    boolean isValid = validationUtil.isValid(offerSeedDto);
                    sb.append(isValid ? String.format("Successfully import offer %s - %s",
                                    offerSeedDto.getAddedOn(),
                                    offerSeedDto.getHasGoldStatus())
                                    : "Invalid offer")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .forEach(offerSeedDto -> {
                    if (offerSeedDto.getCar() == null) {
                        sb.append("Invalid car data for the offer").append(System.lineSeparator());
                        return; // прекратяваме обработката на тази оферта и продължаваме със следващата
                    }

                    Long carId = offerSeedDto.getCar().getId();
                    if (carId == null) {
                        sb.append("Car ID is missing for the offer").append(System.lineSeparator());
                        return; // прекратяваме обработката на тази оферта и продължаваме със следващата
                    }

                    Optional<Car> optionalCar = Optional.ofNullable(carService.findById(carId));
                    if (optionalCar.isEmpty()) {
                        sb.append("Car with ID ").append(carId).append(" does not exist").append(System.lineSeparator());
                        return; // прекратяваме обработката на тази оферта и продължаваме със следващата
                    }

                    Offer offer = modelMapper.map(offerSeedDto, Offer.class);
                    offer.setSeller(sellerService.findById(offerSeedDto.getSeller().getId()));
                    offer.setCar(optionalCar.get());
                    offerRepository.save(offer);
                });

        return sb.toString();
    }


}

