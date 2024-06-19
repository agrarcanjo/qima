package br.com.qima.product.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;


@Data
@Entity
@Table(name = "product")
public class Product {

//    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "uuid2")
//    private UUID id;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @NotEmpty(message = "Name is required")
    private String name;

    private String description;

    private String categoryPath;

    private BigDecimal price;

    private Boolean available;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "product_category", joinColumns = {
            @JoinColumn(name = "product_id", referencedColumnName = "id") }, inverseJoinColumns = {
            @JoinColumn(name = "category_id", referencedColumnName = "id") })
    private List<Category> categories;

}
