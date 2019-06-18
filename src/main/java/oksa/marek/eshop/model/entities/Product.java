package oksa.marek.eshop.model.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @JsonInclude(Include.NON_NULL)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Column(nullable = false)
    @Size(max = 255, min = 3, message = "The name length of the Product must be between 3 and 255 characters !")
    private String name;

    @NotNull
    @Column(nullable = false)
    @PositiveOrZero(message = "Product price must be a positive number")
    private Double price;

    @JsonInclude(Include.NON_NULL)
    private String description;

    @ManyToMany
    @NotNull
    @JoinTable(name = "products_animal_categories",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "animal_category_id"))
    private List<AnimalCategory> animalCategories;

    @OneToOne
    @JsonInclude(Include.NON_NULL)
    private Gallery gallery; //List of image URLS

    public Product() {
    }

    public Product(List<AnimalCategory> animalCategories, Double price, String description, String name, Gallery gallery) {
        this.animalCategories = animalCategories;
        this.price = price;
        this.description = description;
        this.name = name;
        this.gallery = gallery;
    }

    /**
     * NOT A COPY CONSTRUCTOR
     **/
    //Special constructor for the JPQL Query in IProductRepository
    public Product(Product p) {
        this.id = p.getId();
        this.name = p.getName();
        this.price = p.getPrice();
        this.animalCategories = p.getAnimalCategories();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Gallery getGallery() {
        return gallery;
    }

    public void setGallery(Gallery gallery) {
        this.gallery = gallery;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<AnimalCategory> getAnimalCategories() {
        return animalCategories;
    }

    public void setAnimalCategories(List<AnimalCategory> animalCategories) {
        this.animalCategories = animalCategories;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", animalCategories=" + animalCategories +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", gallery=" + gallery +
                '}';
    }
}
