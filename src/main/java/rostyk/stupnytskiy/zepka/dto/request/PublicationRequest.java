package rostyk.stupnytskiy.zepka.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class PublicationRequest {
    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @Min(0)
    private Double price;

    @NotBlank
    private String description;

    @NotNull
    @NotBlank
    private Long subcategoryId;

    @Min(1)
    private Integer count;

    @NotNull
    private Long typeId;

    @NotNull
    private Long deliveryId;

    private List<String> images;

    private String mainImage;
}
