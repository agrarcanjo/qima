package br.com.qima.product.repository;

import br.com.qima.product.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    String GET_PRODUCTS_ASSOCIATED_WITH_CATEGORY_SQL = "" +
            " select p.* from product p " +
            " inner join product_category pc on p.id = pc.product_id " +
            " where (pc.category_id = ?1 or pc.category_id in " +
            " (select ac.id from (" + CategoryRepository.GET_RECURSIVELY_ALL_SUBCATEGORIES_SQL + ") ac " +
            " where ac.parentid = ?1)) ";


    @Query(value = GET_PRODUCTS_ASSOCIATED_WITH_CATEGORY_SQL, nativeQuery = true)
    Product findByAssociatedWithCategory(Long categoryId);


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
