package rostyk.stupnytskiy.zepka.dto.response;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.zepka.entity.Category;
import rostyk.stupnytskiy.zepka.entity.Publication;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CategoryResponse {
    private Long id;
    private String name;
    private List<String> publications;

    public CategoryResponse(Category category) {
        id = category.getId();
        name = category.getName();

    }
}
