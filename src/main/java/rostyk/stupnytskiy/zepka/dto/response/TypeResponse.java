package rostyk.stupnytskiy.zepka.dto.response;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.zepka.entity.Publication;
import rostyk.stupnytskiy.zepka.entity.Type;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class TypeResponse {
    private Long id;
    private String name;
    private List<String> publications;

    public TypeResponse(Type type) {
        id = type.getId();
        name = type.getName();
        publications = type.getPublications().stream().map(Publication::getName).collect(Collectors.toList());
    }
}
