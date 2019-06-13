package oksa.marek.eshop.controllers.rest;

import oksa.marek.eshop.controllers.repositories.IUserRepository;
import oksa.marek.eshop.entities.User;
import oksa.marek.eshop.enums.EUserRole;
import oksa.marek.eshop.exeptions.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class RestUserController {

    private final IUserRepository repository;

    //This is just for testing purposes ... It's not gonna stay like this

    public RestUserController(IUserRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/users")
    public List<User> getAllUsers() {
        return repository.findAll();
    }

    @GetMapping("/api/users/{id}")
    public User getOneUser(@PathVariable Long id) {
        return repository.findById(id).
                orElseThrow(() -> new NotFoundException(id, User.class));
    }

    @PostMapping("/api/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        User foundUser = repository.findByUserName(user.getUserName());

        if(foundUser != null) {
            if(foundUser.getEmail().equals(user.getEmail()) && foundUser.getUserName().equals(user.getUserName())) {
                return ResponseEntity.ok("User successfully logged in");
            }
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Header", "Value");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(responseHeaders).body("User not found !");

    }

    @PostMapping("/api/addUser")
    public User addNewUser(@RequestBody User newUser) {
        if(newUser.getRole() == null) newUser.setRole(EUserRole.ROLE_USER);
        return repository.save(newUser);
    }

}

