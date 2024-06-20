package br.com.qima.product.repository;

import br.com.qima.product.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    String GET_PRODUCTS_ASSOCIATED_WITH_CATEGORY_PATH_SQL =
            "WITH RECURSIVE category_path (id, name, path) AS ( " +
                    "   SELECT id, name, name as path " +
                    "   FROM category " +
                    "   WHERE parent_id IS NULL " +
                    "   UNION ALL " +
                    "   SELECT c.id, c.name, CONCAT(cp.path, ' -> ', c.name) " +
                    "   FROM category_path cp JOIN category c " +
                    "   ON cp.id = c.parent_id " +
                    " ) " +
                    " SELECT p.* " +
                    " FROM product p " +
                    " JOIN product_category pc ON p.id = pc.product_id " +
                    " JOIN category_path cp ON pc.category_id = cp.id" ;

    @Query(value = GET_PRODUCTS_ASSOCIATED_WITH_CATEGORY_PATH_SQL, nativeQuery = true)
    List<Product> findByAssociatedWithCategoryPath();
}
