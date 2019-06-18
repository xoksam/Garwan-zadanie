package oksa.marek.eshop.controller.repositories;

import oksa.marek.eshop.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
    @Query(nativeQuery = true,
            value = "SELECT * FROM users u WHERE u.user_name = :username")
    User findByUserName(@Param("username") String username);

    @Query(nativeQuery = true,
            value = "SELECT * FROM users u WHERE u.email = :email")
    User findByEmail(@Param("email") String email);

    @Query(nativeQuery = true,
            value = "SELECT * FROM users u WHERE u.email = :email OR u.user_name = :username")
    User findByEmailOrUserName(@Param("email") String email, @Param("username") String username);

    @Query(nativeQuery = true,
            value = "SELECT * FROM users u WHERE u.email = :email AND u.user_name = :username")
    User findByUserNameAndEmail(@Param("email") String email, @Param("username") String username);

}
