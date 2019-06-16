package oksa.marek.eshop.controller.rest;

import oksa.marek.eshop.controller.services.UserService;
import oksa.marek.eshop.model.entities.Order;
import oksa.marek.eshop.model.entities.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;


@RestController
@Validated
public class RestUserController {

    private final UserService userService;

    public RestUserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> getAllUsers() {

        return ResponseEntity.ok().body(userService.findAll());
    }


    // Login is not necessary, it's handled by JWTAuthenticationFilter
    @GetMapping("/user/details")
    public ResponseEntity<User> getUserInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return ResponseEntity.ok(userService.findByUserName(username));
    }

    @GetMapping("/admin/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable
                                            @NotNull
                                            @PositiveOrZero(message = "User id must be >= 0 !")
                                                    Long id) {
        User user = userService.findById(id);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/public/register")
    public ResponseEntity<User> registerUser(@RequestBody
                                             @Valid
                                                     User newUser) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(newUser));
    }

}
