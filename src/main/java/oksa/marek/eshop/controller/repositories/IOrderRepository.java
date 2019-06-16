package oksa.marek.eshop.controller.repositories;

import oksa.marek.eshop.model.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {
    @Query(nativeQuery = true,
            value = "SELECT SUM(op.count * op.price_at_time_of_purchase) FROM orders o JOIN order_ordered_products oop ON o.id = oop.order_id" +
                    " JOIN ordered_products op ON op.id = oop.ordered_product_id" +
                    " WHERE o.id = :orderId")
    Double getTotalPrice(@Param("orderId") Long orderId);

    @Query(nativeQuery = true,
            value = "SELECT * FROM orders")
    Page<Order> getOrdersLimitByPageNum(Pageable pageable);

// Hmmmmmmmmmmmmm I don't speak JPQL
//    @Query("select new oksa.marek.eshop.model.entities.Order(o.orderedProducts, o.totalPrice) " +
//            "from Order o join o.user u where u.id = ?1")
    @Query(nativeQuery = true,
            value = "SELECT * FROM orders o WHERE o.user_id = :userId")
    Page<Order> findAllOrdersByUserId(@Param("userId") Long userId, Pageable pageable);
}
