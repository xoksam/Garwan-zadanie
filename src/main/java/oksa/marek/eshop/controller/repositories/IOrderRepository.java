package oksa.marek.eshop.controller.repositories;

import oksa.marek.eshop.model.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IOrderRepository extends JpaRepository<Order, Long> {
    @Query(nativeQuery = true,
            value = "SELECT SUM(op.count * p.price) FROM orders o JOIN order_ordered_products oop ON o.id = oop.order_id" +
                    " JOIN ordered_products op ON op.id = oop.ordered_product_id JOIN products p ON op.product_id = p.id" +
                    " WHERE o.id = :orderId")
    Double getTotalPrice(@Param("orderId") Long orderId);

    @Query(nativeQuery = true,
            value = "SELECT * FROM orders")
    Page<Order> getOrdersLimitByPageNum(Pageable pageable);
}
