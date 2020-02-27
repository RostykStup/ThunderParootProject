package rostyk.stupnytskiy.zepka.specification;

import org.springframework.data.jpa.domain.Specification;
import rostyk.stupnytskiy.zepka.dto.request.PublicationSearchRequest;
import rostyk.stupnytskiy.zepka.entity.Category;
import rostyk.stupnytskiy.zepka.entity.Publication;
import rostyk.stupnytskiy.zepka.entity.Subcategory;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class PublicationSpecification implements Specification<Publication> {

    private String name;
    private Double minPrice;
    private Double maxPrice;
    private List<Long> subcategoriesId;
    private Long categoryId;
    private Boolean image;

    public PublicationSpecification(PublicationSearchRequest publicationSearchRequest) {
        this.name = publicationSearchRequest.getName();
        this.minPrice = publicationSearchRequest.getMinPrice();
        this.maxPrice = publicationSearchRequest.getMaxPrice();
        this.subcategoriesId = publicationSearchRequest.getSubcategoryId();
        this.categoryId = publicationSearchRequest.getCategoryId();
        this.image = publicationSearchRequest.getImage();
    }

    @Override
    public Predicate toPredicate(Root<Publication> root, CriteriaQuery<?> cq, CriteriaBuilder cb) {
        final List<Predicate> predicates = new ArrayList<>();
//        predicates.add(findByNameLike(root, cb));
        predicates.add(findBySubcategoriesAndCategory(root, cb));
        predicates.add(findByPrice(root, cb));
        predicates.add(findByImage(root, cb));
        return cb.and(predicates.toArray(new Predicate[0]));
    }


    private Predicate findByNameLike(Root<Publication> r, CriteriaBuilder cb) {
        Predicate predicate;
        if (name == null || name.trim().isEmpty()) {
            predicate = cb.conjunction();
        } else {
            predicate = cb.like(r.get("name"), '%' + name + '%');
        }
        return predicate;
    }

    private Predicate findBySubcategoriesAndCategory(Root<Publication> r, CriteriaBuilder cb) {
        Predicate predicate;

        final Join<Publication, Subcategory> subcategoryJoin  = r.join("subcategory");
        final Join<Subcategory, Category> categoryJoin = subcategoryJoin.join("category");
        if (categoryId != null) {
            predicate = cb.equal(categoryJoin.get("id"), categoryId);
        } else if (subcategoriesId == null || subcategoriesId.isEmpty() ){
            predicate = cb.conjunction();
//            System.out.println(categoryJoin.get("id").toString());
        }else {
            predicate = subcategoryJoin.get("id").in(subcategoriesId);
        }
        return predicate;
    }


    private Predicate findByPrice(Root<Publication> r, CriteriaBuilder cb) {
        Predicate predicate;
        if (maxPrice == null && minPrice == null) {
            predicate = cb.conjunction();
        } else if (maxPrice == null) {
            predicate = cb.greaterThanOrEqualTo(r.get("price"), minPrice);
        } else if (minPrice == null) {
            predicate = cb.lessThanOrEqualTo(r.get("price"), maxPrice);
        } else {
            predicate = cb.between(r.get("price"), minPrice, maxPrice);
        }
        return predicate;
    }

    private Predicate findByImage(Root<Publication> r, CriteriaBuilder cb) {
        Predicate predicate;
        if (image != null && image) {
            return cb.isNotNull(r.get("mainImageName"));
        } else {
            predicate = cb.conjunction();
        }
        return predicate;
    }
}
