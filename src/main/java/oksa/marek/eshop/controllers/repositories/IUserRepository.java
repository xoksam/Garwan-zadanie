package oksa.marek.eshop.controllers.repositories;

import oksa.marek.eshop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.ws.rs.Path;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true,
            value = "SELECT * FROM users u WHERE u.user_name = :username")
    User findByUserName(@Param("username") String username);
}
