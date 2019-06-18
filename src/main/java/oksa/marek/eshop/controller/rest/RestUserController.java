package oksa.marek.eshop.controller.rest;

import oksa.marek.eshop.controller.security.SecurityConstants;
import oksa.marek.eshop.controller.services.UserService;
import oksa.marek.eshop.model.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    @GetMapping("/user/username")
    @ResponseBody
    public String currentUserName(Authentication authentication) {
        return authentication.getName();
    }


//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> allUsers = userService.findAll();

        // It doesn't send password or id, when they're null
        allUsers.forEach(user -> {
            user.setPassword(null);
            user.setId(null);
        });

        return ResponseEntity.ok().body(allUsers);
    }

    // Login is not necessary, it's handled by JWTAuthenticationFilter

    @GetMapping("/user/details")
    public ResponseEntity<User> getUserInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByUserName(username);
        user.setPassword(null);
        user.setId(null);

        return ResponseEntity.ok(user);
    }

    @GetMapping("/admin/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable
                                            @NotNull
                                            @PositiveOrZero(message = "User id must be >= 0 !")
                                                    Long id) {
        // It doesn't send password or id, when they're null
        User user = userService.findById(id);
        user.setPassword(null);
        user.setId(null);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/public/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void registerUser(@RequestBody
                             @Valid
                                     User newUser) {
        if (newUser.getAuthority() == null) {
            newUser.setAuthority(SecurityConstants.USER_AUTH);
        }

        userService.save(newUser);
    }

}
