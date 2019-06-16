package oksa.marek.eshop.controller.services;


import oksa.marek.eshop.controller.repositories.IUserRepository;
import oksa.marek.eshop.model.entities.Order;
import oksa.marek.eshop.model.entities.User;
import oksa.marek.eshop.controller.errorhandlers.exceptions.CustomIllegalArgumentException;
import oksa.marek.eshop.controller.errorhandlers.exceptions.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private IUserRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public User findById(Long id) {

        return repository.findById(id).orElseThrow(() -> new CustomNotFoundException("id", id.toString(), User.class));
    }

    public User findByUserName(String userName) {
        User user = repository.findByUserName(userName);
        if (user == null) throw new CustomNotFoundException("userName", userName, User.class);

        return user;
    }

    public User save(User newUser) {
        newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));

        if (checkIfExists(newUser))
        throw new CustomIllegalArgumentException("User with username '" + newUser.getUserName() +
                    "' or email '" + newUser.getEmail() + "' already exists !");

        return repository.save(newUser);
    }

    /**
     * Returns true if a given user's username or email is present in database
     **/
    public boolean checkIfExists(User user) {

        return repository.findByEmailOrUserName(user.getEmail(), user.getUserName()) != null;
    }

    public List<User> findAll() {
        return repository.findAll();
    }

}
