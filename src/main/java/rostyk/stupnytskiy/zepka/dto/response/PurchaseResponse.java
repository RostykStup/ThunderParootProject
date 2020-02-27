package rostyk.stupnytskiy.zepka.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.zepka.entity.Purchase;
import rostyk.stupnytskiy.zepka.entity.User;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class PurchaseResponse {
    private Long id;
    private Set<PublicationForPurchaseResponse> publications;
    private String sellerName;
    private Long sellerId;
    private Double sum;
    private Boolean isSent;
    private Boolean isCanceled;
    private Boolean isFinished;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy.MM.dd HH:mm")
    private LocalDateTime creationDate;
    private AddressResponse address;

    public PurchaseResponse(Purchase purchase){
        id = purchase.getId();
        sum = purchase.getPrice();
        publications = purchase.getPublicationsForPurchase().stream().map(PublicationForPurchaseResponse::new).collect(Collectors.toSet());
        sellerName = getSellerByPurchase(purchase).getUsername();
        sellerId = getSellerByPurchase(purchase).getId();
        isSent = purchase.getSent();
        isCanceled = purchase.getCancelled();
        isFinished = purchase.getFinished();
        creationDate = purchase.getCreationDate();
        address = new AddressResponse(purchase.getAddress());
    }

    private User getSellerByPurchase(Purchase purchase){
        return purchase.getPublicationsForPurchase().stream().findFirst().orElseThrow(() -> new IllegalArgumentException("not found")).getPublication().getUser();
    }
}
