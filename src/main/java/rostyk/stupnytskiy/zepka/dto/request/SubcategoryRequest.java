package rostyk.stupnytskiy.zepka.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class SubcategoryRequest {
    @NotNull
    private String name;
    private Long categoryId;
}
