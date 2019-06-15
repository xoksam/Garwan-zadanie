package oksa.marek.eshop.controller.repositories;

import oksa.marek.eshop.model.entities.OrderedProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderedProductRepository extends JpaRepository<OrderedProduct, Long> {

}
