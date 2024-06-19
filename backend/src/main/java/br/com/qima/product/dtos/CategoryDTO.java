package br.com.qima.product.dtos;

import br.com.qima.product.models.Category;
import lombok.Data;


@Data
public class CategoryDTO {
    private Long id;
    private String name;
//    private String parentId;

    public Category toEntity() {
        Category category = new Category();
        category.setId(this.id);
        category.setName(this.name);
        return category;
    }
}
