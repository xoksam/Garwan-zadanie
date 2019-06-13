package oksa.marek.eshop.controllers.repositories;

import oksa.marek.eshop.entities.Order;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {
    @Query(nativeQuery = true,
            value = "SELECT SUM(op.count * p.price) FROM ordered_products op JOIN products p ON op.product_id = p.id")
    Double getTotalPrice();

    @Query(nativeQuery = true,
            value = "SELECT * FROM orders LIMIT 10 OFFSET :num")
    List<Order> getOrdersLimitByPageNum(@Param("num") Integer num);

//    @Modifying
//    @Query(nativeQuery = true,
//            value = "UPDATE orders SET user_id = (SELECT u.id FROM users u WHERE u.user_name = :username) WHERE id = :orderId ")
//    void updateUserIdInOrderByUserName(@Param("username") String username, @Param("orderId") Long orderId);
//
//    @Modifying
//    @Query(nativeQuery = true,
//            value = "UPDATE orders SET user_id = :userId WHERE id = :orderId")
//    void updateUserIdInOrderByUserId(@Param("userId") Long userId, @Param("orderId") Long orderId);
    //    Object[] getAllWithoutGalleryAndDesc();
}
