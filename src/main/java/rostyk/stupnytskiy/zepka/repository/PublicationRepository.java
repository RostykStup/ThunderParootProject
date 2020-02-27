package rostyk.stupnytskiy.zepka.repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import rostyk.stupnytskiy.zepka.entity.Publication;

import java.util.List;

@Repository
public interface PublicationRepository extends JpaRepository<Publication,Long>, JpaSpecificationExecutor<Publication> {
//    List<Publication> findAllBySubcategoryCategoryId(Long id);
    Page<Publication> findAllBySubcategoryCategoryId(Long id, Pageable pageable);
    Page<Publication> findAllByUserId(Long id, Pageable pageable);
    Page<Publication> findAllByUserLogin(String login,Pageable pageable);
}
