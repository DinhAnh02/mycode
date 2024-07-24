package vn.eledevo.vksbe.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

public class DynamicSpecification<T> implements Specification<T> {
    private final Map<String, Object> filters;

    public DynamicSpecification(Map<String, Object> filters) {
        this.filters = filters;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        filters.forEach((field, value) -> {
            if (value != null) {
                // Lấy kiểu dữ liệu của field từ root
                Class<?> fieldType = root.get(field).getJavaType();

                if (fieldType == Boolean.class || fieldType == boolean.class) {
                    // Chuyển đổi value sang Boolean
                    boolean booleanValue = Boolean.parseBoolean(value.toString());
                    predicates.add(
                            booleanValue
                                    ? criteriaBuilder.isTrue(root.get(field))
                                    : criteriaBuilder.isFalse(root.get(field)));
                } else {
                    predicates.add(criteriaBuilder.equal(root.get(field), value));
                }
            }
        });
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
