package oksa.marek.eshop.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import oksa.marek.eshop.model.enums.EAnimalCategory;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@RepositoryRestResource(exported = false)
@Table(name = "animal_categories")
public class AnimalCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EAnimalCategory name;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "products_animal_categories",
            joinColumns = @JoinColumn(name = "animal_category_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    public AnimalCategory() {
    }

    public AnimalCategory(@NotNull EAnimalCategory name) {
        this.name = name;
    }

    public EAnimalCategory getName() {
        return name;
    }

    public void setName(EAnimalCategory name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "AnimalCategory{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
