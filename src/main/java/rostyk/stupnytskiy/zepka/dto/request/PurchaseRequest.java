package rostyk.stupnytskiy.zepka.dto.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class PurchaseRequest {
    @NotNull
    private List<Long> publicationsForPurchaseId;
}
