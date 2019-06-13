package oksa.marek.eshop.controllers.rest;

import oksa.marek.eshop.controllers.repositories.IProductRepository;
import oksa.marek.eshop.entities.Product;
import oksa.marek.eshop.exeptions.MyIllegalArgumentException;
import oksa.marek.eshop.exeptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class RestProductController {

    private final IProductRepository repository;


    public RestProductController(IProductRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/products/page/{num}")
    public ResponseEntity<List<Product>> getAllProducts(@PathVariable Integer num) {
        if(num <= 0) throw new MyIllegalArgumentException("Page number must be > 0 !");

        return new ResponseEntity<List<Product>>(repository.findAllProductsLimitByPage((num - 1) * 10), HttpStatus.OK);
    }

    @GetMapping("/api/products/id/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {

        Product product = repository.findById(id).orElseThrow(() -> new NotFoundException(id, Product.class));
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }
    @GetMapping("/api/products/name/{name}")
    public ResponseEntity<Product> getProductByName(@PathVariable String name) {
        name = name.replaceAll("[^a-zA-Z0-9 ]", "");
        Product product = repository.findByName(name);

        if(product == null) throw new NotFoundException(name, Product.class);

        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @PostMapping("/api/admin/addProduct")
    public ResponseEntity<Product> addNewProduct(@RequestBody Product newProduct) {

        return ResponseEntity.ok(repository.save(newProduct));
    }

    @GetMapping("/api/products/filter/{minPrice}/{maxPrice}/{prefix}")
    public ResponseEntity<List<Product>> getFilteredProducts(@PathVariable(name = "minPrice") Double minPrice,
                                                             @PathVariable(name = "maxPrice") Double maxPrice,
                                                             @PathVariable(name = "prefix") String prefix) {

    if(minPrice > maxPrice) throw new MyIllegalArgumentException("Minimum price must be greater than maximum price !");
    // ???
    if(prefix == null || prefix.equals("null")) prefix = "";

        return ResponseEntity.ok(repository.findFilteredProductsByPriceAndName(
                minPrice, maxPrice, prefix));
    }

}
