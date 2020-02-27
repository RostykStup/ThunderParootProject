package rostyk.stupnytskiy.zepka.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.zepka.entity.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery,Long> {
}
