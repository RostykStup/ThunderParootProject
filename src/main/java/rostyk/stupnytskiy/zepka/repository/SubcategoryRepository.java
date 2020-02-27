package rostyk.stupnytskiy.zepka.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.zepka.entity.Subcategory;

import java.util.List;

@Repository
public interface SubcategoryRepository extends JpaRepository<Subcategory,Long> {
    List<Subcategory> findAllByCategoryId(Long id);
}
