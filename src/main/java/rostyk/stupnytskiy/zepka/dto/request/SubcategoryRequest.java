package rostyk.stupnytskiy.zepka.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@Setter
public class SubcategoryRequest {
    @NotNull
    private String name;
    @NotNull
    @Positive
    private Long categoryId;
}
