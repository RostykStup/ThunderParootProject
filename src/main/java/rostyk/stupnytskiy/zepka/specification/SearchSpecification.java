package rostyk.stupnytskiy.zepka.specification;

import org.springframework.data.jpa.domain.Specification;
import rostyk.stupnytskiy.zepka.dto.request.PublicationSearchRequest;
import rostyk.stupnytskiy.zepka.dto.request.SearchRequest;
import rostyk.stupnytskiy.zepka.entity.Category;
import rostyk.stupnytskiy.zepka.entity.Publication;
import rostyk.stupnytskiy.zepka.entity.Subcategory;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class SearchSpecification implements Specification<Publication> {

    private String name;

    public SearchSpecification(SearchRequest searchRequest) {
        this.name = searchRequest.getSearch();
    }

    @Override
    public Predicate toPredicate(Root<Publication> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        final List<Predicate> predicates = new ArrayList<>();
        predicates.add(findByNameLike(root, cb));
        return cb.and(predicates.toArray(new Predicate[0]));
    }


    private Predicate findByNameLike(Root<Publication> r, CriteriaBuilder cb) {
        Predicate predicate;
        if (name == null || name.trim().isEmpty()) {
            predicate = cb.conjunction();
        } else {
//            predicate = cb.like(r.get("name"), '%' + name + '%');
            predicate = cb.like(r.get("name"), name + '%');
        }
        return predicate;
    }


}
