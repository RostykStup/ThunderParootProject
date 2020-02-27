package rostyk.stupnytskiy.zepka.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchRequest {
    private String search;

    @JsonProperty("pagination")
    private PaginationRequest paginationRequest;
}
