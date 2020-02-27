package rostyk.stupnytskiy.zepka.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.zepka.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    Order findByIdAndUserLogin(Long id, String login);
    Page<Order> findAllByUserLogin (String login, Pageable pageable);
}
