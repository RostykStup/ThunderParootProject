package rostyk.stupnytskiy.zepka.dto.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
public class OrderRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long publicationId;
    private LocalDateTime creationDate;
}
