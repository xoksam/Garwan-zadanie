package oksa.marek.eshop.controllers.repositories;

import oksa.marek.eshop.entities.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderedProductRepository extends JpaRepository<OrderedProduct, Long> {

}
