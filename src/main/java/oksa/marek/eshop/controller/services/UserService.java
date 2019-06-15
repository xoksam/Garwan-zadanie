package oksa.marek.eshop.controller.services;


import oksa.marek.eshop.controller.repositories.IUserRepository;
import oksa.marek.eshop.model.entities.User;
import oksa.marek.eshop.model.enums.EUserRole;
import oksa.marek.eshop.controller.errorhandlers.exeptions.CustomIllegalArgumentException;
import oksa.marek.eshop.controller.errorhandlers.exeptions.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    IUserRepository repository;

    public User findById(Long id) {

        return repository.findById(id).orElseThrow(() -> new CustomNotFoundException("id", id.toString(), User.class));
    }

    public User findByUserName(String userName) {
        User user = repository.findByUserName(userName);
        if (user == null) throw new CustomNotFoundException("userName", userName, User.class);

        return user;
    }

    public User save(User newUser) {

        if (checkIfExists(newUser))
        throw new CustomIllegalArgumentException("User with username '" + newUser.getUserName() +
                    "' or email '" + newUser.getEmail() + "' already exists !");

        if (newUser.getRole() == null) newUser.setRole(EUserRole.ROLE_USER);
        return repository.save(newUser);
    }

    /**
     * Returns true if a given user's username or email is present in database
     **/
    public boolean checkIfExists(User user) {

        return repository.findByEmailOrUserName(user.getEmail(), user.getUserName()) != null;
    }
}
