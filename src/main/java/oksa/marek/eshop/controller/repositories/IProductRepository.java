package oksa.marek.eshop.controller.repositories;

import oksa.marek.eshop.model.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    @Query(nativeQuery = true,
            value = "SELECT * FROM products p WHERE p.name LIKE %:prodName%")
    Page<Product> findByName(@Param("prodName") String prodName, Pageable pageable);

    @Query("select new oksa.marek.eshop.model.entities.Product(p.id, p.name, p.price) from Product p where p.name like concat(?3, '%') and " +
            "p.price >= ?1 and p.price <= ?2")
    Page<Product> findFilteredProductsByPriceAndName(Double minPrice, Double maxPrice,
                                                     String prefix, Pageable pageable);

    @Query(nativeQuery = true,
            value = "SELECT * FROM products")
    Page<Product> findAllProductsLimitByPage(Pageable pageable);
}

