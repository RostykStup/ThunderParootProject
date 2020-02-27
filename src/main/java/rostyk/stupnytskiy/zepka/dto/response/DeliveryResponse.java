package rostyk.stupnytskiy.zepka.dto.response;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.zepka.entity.Delivery;
import rostyk.stupnytskiy.zepka.entity.Publication;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class DeliveryResponse {
    private Long id;
    private String name;
    private List<String> publications;

    public DeliveryResponse(Delivery delivery){
        id = delivery.getId();
        name = delivery.getName();
        publications = delivery.getPublications().stream().map(Publication::getName).collect(Collectors.toList());
    }
}
