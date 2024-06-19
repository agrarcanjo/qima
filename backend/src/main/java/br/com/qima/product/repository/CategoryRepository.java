package br.com.qima.product.repository;

import br.com.qima.product.models.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    String GET_RECURSIVELY_ALL_SUBCATEGORIES_SQL = "" +
            " WITH RECURSIVE ALL_SUBCATEGORIES(ID, PARENT_ID) " +
            " AS ( " +
            " select c.id, c.parent_id from productdb.category c  " +
            " where c.parent_id is null union all  " +
            " select c.id, c.parent_id from ALL_SUBCATEGORIES  " +
            " inner join productdb.category c on ALL_SUBCATEGORIES.id = c.parent_id)  " +
            " select id, parent_id from ALL_SUBCATEGORIES";

    @Query(
            value = "WITH RECURSIVE ancestors(id, parent_id, name, lvl) AS ("
                    + "   SELECT cat.id, cat.parent_id, cat.name, 1 AS lvl "
                    + "   FROM category cat "
                    + "   WHERE cat.id = :categoryId "
                    + "   UNION ALL "
                    + "   SELECT parent.id, parent.parent_id, parent.name, child.lvl + 1 AS lvl "
                    + "   FROM category parent "
                    + "   JOIN ancestors child "
                    + "   ON parent.id = child.parent_id "
                    + " )"
                    + "SELECT name from ancestors ORDER BY lvl DESC"
            , nativeQuery = true)
    List<String> findAncestry(@Param("categoryId") Long categoryId);
}
