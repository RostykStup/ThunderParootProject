package rostyk.stupnytskiy.zepka.dto.response;

import lombok.Getter;
import lombok.Setter;
import rostyk.stupnytskiy.zepka.entity.Cart;
import rostyk.stupnytskiy.zepka.entity.PublicationForPurchase;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
public class CartResponse {
    private Set<PublicationForPurchaseResponse> data;
    private Double sum;
    private Integer elements;

    public CartResponse(Cart cart){
        data = cart.getPublicationsForPurchase()
                .stream()
                .map(PublicationForPurchaseResponse::new)
                .collect(Collectors.toSet());
        sum = 0.0;
        elements = 0;
        for (PublicationForPurchase forPurchase : cart.getPublicationsForPurchase()) {
            elements++;
            sum = sum + forPurchase.getPublication().getPrice() * forPurchase.getCount();
        }
    }
}
