package rostyk.stupnytskiy.zepka.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.zepka.entity.PublicationForPurchase;
import rostyk.stupnytskiy.zepka.entity.Purchase;
import rostyk.stupnytskiy.zepka.entity.User;

import java.time.LocalDateTime;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
//    Purchase findByUserLogin(String login);
//    Purchase findByUserLoginAndCreationDate(String login, LocalDateTime dateTime);
//    Purchase findByPublicationsForPurchaseContains(PublicationForPurchase publicationForPurchase);
    Page<Purchase> findAllByUserLogin(String login, Pageable pageable);
}
