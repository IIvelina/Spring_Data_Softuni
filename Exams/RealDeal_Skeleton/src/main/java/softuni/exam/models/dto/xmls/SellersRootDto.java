package softuni.exam.models.dto.xmls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "sellers")
@XmlAccessorType(XmlAccessType.FIELD)
public class SellersRootDto implements Serializable {
@XmlElement(name = "seller")
private List<SellerSeedDto> sellerSeedDtos;

    public List<SellerSeedDto> getSellerSeedDtos() {
        return sellerSeedDtos;
    }

    public void setSellerSeedDtos(List<SellerSeedDto> sellerSeedDtos) {
        this.sellerSeedDtos = sellerSeedDtos;
    }
}
