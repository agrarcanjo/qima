package br.com.qima.product.dtos;

import br.com.qima.product.models.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProjectQuery {

    private String name;
    private String description;
    private Boolean available;

    public Specification<Product> toSpecification() {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (getName() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" + getName().toUpperCase() + "%"));
            }
            if (getDescription() != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("description")), "%" + getDescription().toUpperCase() + "%"));
            }
            if (getAvailable() != null) {
                predicates.add(criteriaBuilder.equal(root.get("available"), getAvailable()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
