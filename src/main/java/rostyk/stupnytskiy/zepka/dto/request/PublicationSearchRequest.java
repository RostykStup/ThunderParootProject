package rostyk.stupnytskiy.zepka.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PublicationSearchRequest {
    private String name;
    private Double minPrice;
    private Double maxPrice;
    private Long categoryId;
    private List<Long> subcategoryId = new ArrayList<>();
    private Boolean image;

    @JsonProperty("pagination")
    private PaginationRequest paginationRequest;
}
