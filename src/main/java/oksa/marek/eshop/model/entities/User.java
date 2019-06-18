package oksa.marek.eshop.model.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import oksa.marek.eshop.controller.security.SecurityConstants;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "users")
@RepositoryRestResource(exported = false)
public class User implements UserDetails {

    @Id
    @JsonInclude(Include.NON_NULL)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @Size(min = 3, max = 30, message = "User name length must be between 3-30 characters")
    private String userName;

    @JsonInclude(Include.NON_NULL)
    @NotNull
    private String password;

    @NotNull
    @Size(min = 5, max = 60, message = "E-mail length must be between 5-60 characters")
    private String email;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private List<Order> orders;

    private String authority;

    public User() {
    }

    //Can create admin account, only if the authority is explicitly set to ADMIN
    public User(String userName, String password, String email, String authority) {
        this.userName = userName;
        this.password = password;
        this.email = email;
        this.authority = authority;
    }

    public User(String userName, String password, String email) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.authority = SecurityConstants.USER_ROLE;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.asList(new SimpleGrantedAuthority(getAuthority()));
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setPassword(String password) {
        this.password = password;
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
                ", email='" + email + '\'' +
                ", authority='" + authority + '\'' +
                '}';
    }
}
