package oksa.marek.eshop.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import oksa.marek.eshop.controller.services.ProductService;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

@Entity
@RepositoryRestResource(exported = false)
@Table(name = "ordered_products")
public class OrderedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    @NotNull(message = "Product in OrderedProduct cannot be null !")
    private Product product;

    @Positive(message = "Number of products must be a positive number !")
    @NotNull
    private Integer count;

    @NotNull
    @Positive(message = "Price at time of purchase must be a positive number !")
    private Double priceAtTimeOfPurchase;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "order_ordered_products",
            joinColumns = @JoinColumn(name = "ordered_product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Order> orders;


//    public OrderedProduct(Product product, Integer count) {
//        this.product = product;
//        this.count = count;
//        setPriceAtTimeOfPurchase(product.getPrice());  //Doesn't work
//    }

    public OrderedProduct(Product product, Integer count, Double priceAtTimeOfPurchase) {
        this.product = product;
        this.count = count;
        this.priceAtTimeOfPurchase = priceAtTimeOfPurchase;
    }

    public OrderedProduct() {
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Double getPriceAtTimeOfPurchase() {
        return priceAtTimeOfPurchase;
    }

    public void setPriceAtTimeOfPurchase(Double priceAtTimeOfPurchase) {
        this.priceAtTimeOfPurchase = priceAtTimeOfPurchase;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "OrderedProduct{" +
                "id=" + id +
                ", product.name=" + product.getName() +
                ", count=" + count +
                ", priceAtTimeOfPurchase=" + priceAtTimeOfPurchase +
                '}';
    }
}