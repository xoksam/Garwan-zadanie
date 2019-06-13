package oksa.marek.eshop.controllers.repositories;

import oksa.marek.eshop.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    @Query(nativeQuery = true,
            value = "SELECT * FROM products p WHERE p.name LIKE %:prodName%")
    Product findByName(@Param("prodName") String prodName);

    @Query(nativeQuery = true,
            value = "SELECT p.* FROM products p")
    List<Product> findAllProducts();

    //    @Query(nativeQuery = true,
//            value = "SELECT p.id, p.name, p.price FROM products p WHERE p.name LIKE CONCAT(:prefix, '%') AND " +
//                    "p.price >= :minPrice AND p.price <= :maxPrice")
    @Query("select new oksa.marek.eshop.entities.Product(p.id, p.name, p.price) from Product p where p.name like concat(?3, '%') and " +
            "p.price >= ?1 and p.price <= ?2")
    List<Product> findFilteredProductsByPriceAndName(Double minPrice, Double maxPrice,
                                                     String prefix);

    @Query(nativeQuery = true,
            value = "SELECT * FROM products LIMIT 10 OFFSET :num")
    List<Product> findAllProductsLimitByPage(@Param("num") Integer num);
}

