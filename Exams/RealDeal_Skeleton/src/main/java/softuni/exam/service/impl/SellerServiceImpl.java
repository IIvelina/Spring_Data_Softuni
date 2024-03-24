package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.Seller;
import softuni.exam.models.dto.xmls.SellerSeedDto;
import softuni.exam.models.dto.xmls.SellersRootDto;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService {
    private final SellerRepository sellerRepository;

    private static final String FILE_PATH = "src/main/resources/files/xml/sellers.xml";

    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    @Autowired
    public SellerServiceImpl(SellerRepository sellerRepository, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return new String(Files.readAllBytes(Path.of(FILE_PATH)));
    }

    @Override
    public String importSellers() throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();

        JAXBContext jaxbContext = JAXBContext.newInstance(SellersRootDto.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        SellersRootDto sellersRootDto = (SellersRootDto)unmarshaller.unmarshal(new File(FILE_PATH));

        for (SellerSeedDto sellerSeedDto : sellersRootDto.getSellerSeedDtos()) {

            Optional<Seller> optSeller =
                    this.sellerRepository.findByFirstNameAndLastNameAndEmailAndTownAndRating(
                            sellerSeedDto.getFirstName(), sellerSeedDto.getLastName(),
                            sellerSeedDto.getEmail(), sellerSeedDto.getTown(), sellerSeedDto.getRating()
                    );

            if (!this.validationUtil.isValid(sellerSeedDto) || optSeller.isPresent()){
                sb.append("Invalid seller\n");
                continue;
            }

            Seller seller = this.modelMapper.map(sellerSeedDto, Seller.class);

            this.sellerRepository.saveAndFlush(seller);
            sb.append(String.format("Successfully import seller %s - %s\n",
                    seller.getLastName(),
                    seller.getEmail()));
        }
        return sb.toString();
    }

    @Override
    public Seller findById(Long id) {
        return sellerRepository.findById(id).orElse(null);
    }
}
