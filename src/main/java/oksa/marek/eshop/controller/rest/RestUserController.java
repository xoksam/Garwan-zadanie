package oksa.marek.eshop.controller.rest;

import oksa.marek.eshop.controller.services.UserService;
import oksa.marek.eshop.model.entities.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@RestController
@Validated
public class RestUserController {

    private final UserService userService;

    public RestUserController(UserService userService) {
        this.userService = userService;
    }

//    @GetMapping("/api/admin/users")
//    public List<User> getAllUsers() {
//        return userService.findAll();
//    }


    //TODO: REDO !!! Toto je HNOJ !!!
    // Malo by to fungovat jak pre usera tak pre admina ... Ak nie tak pre admina treba spravit zvlast endpoint
    @PostMapping("/api/login")
    public ResponseEntity<String> loginUser(@RequestBody
                                            @Valid
                                                    User user) {
        User foundUser = userService.findByUserName(user.getUserName());
        if (foundUser != null) {
            if (foundUser.getEmail().equals(user.getEmail()) && foundUser.getUserName().equals(user.getUserName())) {
                return ResponseEntity.ok("Http: " + HttpStatus.OK + "\nUser successfully logged in");
            }
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Header", "Value");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(responseHeaders)
                .body("Http: " + HttpStatus.NOT_FOUND + "\nUser not found !");
    }

    @GetMapping("/api/admin/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable
                                            @NotNull
                                            @PositiveOrZero(message = "User id must be >= 0 !")
                                                    Long id) {
        User user = userService.findById(id);

        return ResponseEntity.ok(user);
    }

    @PostMapping("/api/register")
    public ResponseEntity<User> registerUser(@RequestBody
                                             @Valid
                                                     User newUser) {

        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(newUser));
    }

}

