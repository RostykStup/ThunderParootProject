package rostyk.stupnytskiy.zepka.dto.response;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.zepka.entity.Publication;
import rostyk.stupnytskiy.zepka.entity.PublicationForPurchase;

@Getter
@Setter
public class PublicationForPurchaseResponse {
    private Long id;
    private Long publicationId;
    private String image;
    private String name;
    private Double price;
    private String userName;
    private Long userId;
    private Integer count;
    private Integer publicationCount;

    public PublicationForPurchaseResponse(PublicationForPurchase publicationForPurchase){
        id = publicationForPurchase.getId();
        publicationId = publicationForPurchase.getPublication().getId();
        image = publicationForPurchase.getPublication().getMainImageName();
        name = publicationForPurchase.getPublication().getName();
        count = publicationForPurchase.getCount();
        price = publicationForPurchase.getPublication().getPrice() * count;
        userName = publicationForPurchase.getPublication().getUser().getUsername();
        userId = publicationForPurchase.getPublication().getUser().getId();
        publicationCount = publicationForPurchase.getPublication().getCount();
    }
}
