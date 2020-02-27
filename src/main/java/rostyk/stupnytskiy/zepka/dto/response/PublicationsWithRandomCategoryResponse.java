package rostyk.stupnytskiy.zepka.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PublicationsWithRandomCategoryResponse {

    private List<PublicationResponse> data;
    private String categoryName;

    public PublicationsWithRandomCategoryResponse(List<PublicationResponse> publications, String category){
        data = publications;
        categoryName = category;
    }
}