package oksa.marek.eshop.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.*;
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
    private Product product;

    private Integer count;

    private Double priceAtTimeOfPurchase;
    
    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "order_ordered_products",
            joinColumns = @JoinColumn(name = "ordered_product_id"),
            inverseJoinColumns = @JoinColumn(name = "order_id"))
    private List<Order> orders;


    public OrderedProduct(Product product, Integer count) {
        this.product = product;
        this.count = count;
//        setPriceAtTimeOfPurchase(product.getPrice());
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public OrderedProduct(Product product, Integer count, Double priceAtTimeOfPurchase) {
        this.product = product;
        this.count = count;
        this.priceAtTimeOfPurchase = priceAtTimeOfPurchase;
    }

    public Double getPriceAtTimeOfPurchase() {
        return priceAtTimeOfPurchase;
    }

    public void setPriceAtTimeOfPurchase(Double priceAtTimeOfPurchase) {
        this.priceAtTimeOfPurchase = priceAtTimeOfPurchase;
    }

    public OrderedProduct() {
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

}