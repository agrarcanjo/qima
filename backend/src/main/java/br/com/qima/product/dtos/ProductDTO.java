package br.com.qima.product.dtos;

import br.com.qima.product.models.Category;
import br.com.qima.product.models.Product;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class ProductDTO {
//    private UUID id;
    private Long id;
    private String name;
    private String description;
    private String categoryPath;
    private BigDecimal price;
    private CategoryDTO category;
    private Boolean available;

    public Product toEntity() {
        Product product = new Product();
        product.setId(this.id);
        product.setName(this.name);
        product.setDescription(this.description);
        product.setPrice(this.price);
        product.setAvailable(this.available);
        return product;
    }

    public ProductDTO fromEntity(Product product, String categories) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setAvailable(product.getAvailable());
        productDTO.setCategoryPath(categories);
        return productDTO;
    }
}
