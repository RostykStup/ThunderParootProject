package rostyk.stupnytskiy.zepka.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.zepka.entity.Order;

import javax.swing.text.html.ListView;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class OrderResponse {
    private Long id;
    private String shopperName;
    private Long shopperId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime creationDate;

    private Set<PublicationForPurchaseResponse> purchases;
    private Double sum;
    private Boolean isViewed;
    private Boolean isSent;
    private Boolean isCancelled;
    private Boolean isFinished;
    private AddressResponse address;

    public OrderResponse (Order order){
        id = order.getId();
        shopperName = order.getPurchase().getUser().getUsername();
        shopperId = order.getPurchase().getUser().getId();
        creationDate = order.getPurchase().getCreationDate();
        purchases = order.getPurchase().getPublicationsForPurchase().stream().map(PublicationForPurchaseResponse::new).collect(Collectors.toSet());
        sum = order.getPurchase().getPrice();
        isViewed = order.getIsView();
        isSent = order.getPurchase().getSent();
        isCancelled = order.getPurchase().getCancelled();
        isFinished = order.getPurchase().getFinished();
        address = new AddressResponse(order.getPurchase().getAddress());
    }
}
