package rostyk.stupnytskiy.zepka.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class AddImageRequest {

    @NotNull
    @NotBlank
    private String image;
}
