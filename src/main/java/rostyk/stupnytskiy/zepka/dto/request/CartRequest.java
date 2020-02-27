package rostyk.stupnytskiy.zepka.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Setter
@Getter
public class CartRequest {
    @NotNull
    private Long publicationId;
    @NotNull
    @Positive
    private Integer count;
}
