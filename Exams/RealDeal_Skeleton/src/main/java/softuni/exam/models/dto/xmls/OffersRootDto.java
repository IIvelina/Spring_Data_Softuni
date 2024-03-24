package softuni.exam.models.dto.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "offers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OffersRootDto implements Serializable {
    @XmlElement(name = "offer")
    private List<OfferSeedDto> offerSeedDtos;
    public List<OfferSeedDto> getOfferSeedDtos() {
        return offerSeedDtos;
    }

    public void setOfferSeedDtos(List<OfferSeedDto> offerSeedDtos) {
        this.offerSeedDtos = offerSeedDtos;
    }

   }
