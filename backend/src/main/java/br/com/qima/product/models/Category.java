package br.com.qima.product.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;


@Getter
@Setter
@Entity
@Table(	name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 100)
    @NotEmpty(message = "Name is required")
    private String name;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;

//    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "categories")
//    private Set<Product> products;

//    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
//    private List<Category> children = new ArrayList<>();
}
