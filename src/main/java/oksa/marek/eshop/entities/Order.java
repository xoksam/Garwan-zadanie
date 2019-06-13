package oksa.marek.eshop.entities;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
@Transactional
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToMany
    @JoinTable(name = "order_ordered_products",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "ordered_product_id"))
    private List<OrderedProduct> orderedProducts;

    @ManyToOne
    private User user;

    private Double totalPrice;

    @Column(updatable = false, nullable = false)
    private Date time;

    @PrePersist
    public void onPrePersist() {
        setTime(new Date());
    }

    public Order() {
    }

    public Order(List<OrderedProduct> orderedProducts, Double totalPrice, User user) {
        this.orderedProducts = orderedProducts;
        this.totalPrice = totalPrice;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Order(List<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public Order(List<OrderedProduct> orderedProducts, Double totalPrice) {
        this.orderedProducts = orderedProducts;
        this.totalPrice = totalPrice;
    }

    public List<OrderedProduct> getOrderedProducts() {
        return orderedProducts;
    }

    public void setOrderedProducts(List<OrderedProduct> orderedProducts) {
        this.orderedProducts = orderedProducts;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotalPrice() {

        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderedProducts=" + orderedProducts +
                ", user=" + user +
                ", totalPrice=" + totalPrice +
                ", time=" + time +
                '}';
    }

    //    @Override
//    public String toString() {
//        return "Order{" +
//                "id=" + id +
//                ", totalPrice=" + totalPrice +
//                ", time=" + time +
//                '}';
//    }
}
