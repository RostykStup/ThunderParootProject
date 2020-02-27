package rostyk.stupnytskiy.zepka.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.zepka.entity.Publication;

@Getter
@Setter
public class PublicationForSearchResponse {
    private Integer matchPoints;

//    @JsonProperty("publication")
    private PublicationResponse response;

    public PublicationForSearchResponse(PublicationResponse response, Integer matchPoints) {
        this.matchPoints = matchPoints;
        this.response = response;
    }
}
