package rostyk.stupnytskiy.zepka.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.zepka.entity.Cart;
import rostyk.stupnytskiy.zepka.entity.Category;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUserLogin(String login);
}
