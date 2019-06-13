//package oksa.marek.eshop.model;
//
//import oksa.marek.eshop.entities.User;
//import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.PersistenceContext;
//import java.util.List;
//
//
//@Repository
//@Transactional
//public class UserDAO implements IUserDAO {
//
//    @PersistenceContext
//    private EntityManager entityManager;
//
//    @Override
//    public List<User> getAllUsers() {
//        return entityManager.createNativeQuery("SELECT * FROM user", User.class).getResultList();
//    }
//
//    @Override
//    public User getUserById(Long id) {
//        return entityManager.find(User.class, id);
//    }
//
//    @Override
//    public void addUser(User user) {
//
//    }
//
//    @Override
//    public void updateUser(User user) {
//
//    }
//
//    @Override
//    public void deleteUser(User user) {
//
//    }
//
//    @Override
//    public boolean userExists(User user) {
//        return false;
//    }
//}
