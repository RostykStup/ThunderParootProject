package rostyk.stupnytskiy.zepka.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.zepka.entity.Cart;
import rostyk.stupnytskiy.zepka.entity.PublicationForPurchase;

import java.util.Set;

@Repository
public interface PublicationForPurchaseRepository extends JpaRepository<PublicationForPurchase, Long> {
    Boolean existsByPublicationIdAndCart(Long id, Cart cart);
    PublicationForPurchase findByIdAndCart(Long id, Cart cart);
    PublicationForPurchase findByPublicationIdAndCart(Long id, Cart cart);
    Set<PublicationForPurchase> findAllByCart(Cart cart);
}
