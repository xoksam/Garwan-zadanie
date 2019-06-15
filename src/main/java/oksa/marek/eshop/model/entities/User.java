package oksa.marek.eshop.model.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import oksa.marek.eshop.model.enums.EUserRole;
import oksa.marek.eshop.model.entities.Order;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "users")
@RepositoryRestResource(exported = false)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 30, message = "User name length must be between 3-30 characters")
    private String userName;

    @NotNull
    @Size(min = 5, max = 60, message = "E-mail length must be between 5-60 characters")
    private String email;

    @Enumerated(value = EnumType.STRING)
    private EUserRole role;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private List<Order> orders;

    public User() {
    }

    public User(String userName, String email) {
        this.userName = userName;
        this.email = email;
        this.role = EUserRole.ROLE_USER;
    }

    public User(String userName, String email, EUserRole role) {
        this.userName = userName;
        this.email = email;
        this.role = role;
    }

    public EUserRole getRole() {
        return role;
    }

    public void setRole(EUserRole role) {
        this.role = role;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", role=" + role +
                ", email='" + email + '\'' +
//                ", orders=" + orders +
                '}';
    }
}
