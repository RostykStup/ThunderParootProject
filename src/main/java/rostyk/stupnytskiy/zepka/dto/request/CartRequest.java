package rostyk.stupnytskiy.zepka.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CartRequest {

    private Long publicationId;

    private Integer count;
}
