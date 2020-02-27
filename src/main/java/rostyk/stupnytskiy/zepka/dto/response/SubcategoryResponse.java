package rostyk.stupnytskiy.zepka.dto.response;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.zepka.entity.Publication;
import rostyk.stupnytskiy.zepka.entity.Subcategory;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class SubcategoryResponse {
    private Long id;
    private String name;
    private String categoryName;
    private Long categoryId;
    private List<String> publications;

    public SubcategoryResponse(Subcategory subcategory) {
        id = subcategory.getId();
        name = subcategory.getName();
        categoryName = subcategory.getCategory().getName();
        categoryId = subcategory.getCategory().getId();
        publications = subcategory.getPublications().stream().map(Publication::getName).collect(Collectors.toList());
    }
}
