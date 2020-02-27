package rostyk.stupnytskiy.zepka.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.zepka.entity.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Address findByUserLogin(String login);
}
