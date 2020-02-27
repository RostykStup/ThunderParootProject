package rostyk.stupnytskiy.zepka.dto.response;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.zepka.entity.Publication;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PublicationResponse {
    private Long id;
    private String name;
    private Double price;
    private String description;
    private String delivery;
    private Long deliveryId;
    private String type;
    private Long typeId;
    private String category;
    private Long categoryId;
    private String subcategory;
    private Long subcategoryId;
    private String username;
    private Long userId;
    private LocalDateTime creationDate;
    private String imageName;
    private List<String> images;
    private Integer number;

    public PublicationResponse (Publication publication){
        id = publication.getId();
        name = publication.getName();
        price = publication.getPrice();
        category = publication.getSubcategory().getCategory().getName();
        categoryId = publication.getSubcategory().getCategory().getId();
        subcategory = publication.getSubcategory().getName();
        subcategoryId = publication.getSubcategory().getId();
        type = publication.getType().getName();
        description = publication.getDescription();
        delivery = publication.getDelivery().getName();
        creationDate = publication.getCreationDate();
        deliveryId = publication.getDelivery().getId();
        typeId = publication.getType().getId();
        if(publication.getMainImageName() != null) {
            imageName = publication.getMainImageName();
        }
        if (!publication.getImages().isEmpty()) {
            images = publication.getImages();
        }
        username = publication.getUser().getUsername();
        userId = publication.getUser().getId();
        number = publication.getCount();
    }
}
