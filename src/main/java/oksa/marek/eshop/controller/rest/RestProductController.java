package oksa.marek.eshop.controller.rest;

import oksa.marek.eshop.controller.ProductFilter;
import oksa.marek.eshop.controller.services.ProductService;
import oksa.marek.eshop.model.entities.Product;
import oksa.marek.eshop.controller.errorhandlers.exeptions.CustomIllegalArgumentException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.*;
import java.util.List;

@RestController
@Validated
public class RestProductController {

    private final ProductService productService;

    public RestProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/api/admin/products/page/{num}")
    public ResponseEntity<List<Product>> getAllProducts(@PathVariable
                                                        @PositiveOrZero(message = "Page number must be >= 0 !") Integer num) {

        return new ResponseEntity<>(productService.findAllProductsLimitByPage(num).getContent(), HttpStatus.OK);
    }

    //Just a nice little addition for admin
    @GetMapping("/api/admin/products/id/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable
                                                  @Positive(message = "Id of the product must be > 0 !") Long id) {

        Product product = productService.findById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
    //Also a nice little addition for admin
    @GetMapping("/api/admin/products/name/{name}/page/{num}")
    public ResponseEntity<List<Product>> getProductByName(@PathVariable @Size(max = 255)
                                                          @NotNull(message = "Product name cannot be null !") String name,
                                                          @PathVariable @PositiveOrZero Integer num) {
        // Just in case
        name = name.replaceAll("[^a-zA-Z0-9 ]", "");
        return new ResponseEntity<>(productService.findByName(name, num), HttpStatus.OK);
    }

    @PostMapping("/api/admin/addProduct")
    public ResponseEntity<Product> addNewProduct(@RequestBody
                                                 @NotNull(message = "Added product cannot be null !") Product newProduct) {

        return ResponseEntity.ok(productService.save(newProduct));
    }

    @PostMapping("/api/products/filter/page/{num}")
    public ResponseEntity<List<Product>> getFilteredProducts(@RequestBody ProductFilter filter,
                                                             @PathVariable("num")
                                                             @PositiveOrZero(message = "Page number must be > 0 !") Integer num) {

        // Ak nechce filtrovat, teda ak je filter null,  tak vratim list vsetkych productov
        if (filter == null) {
            return ResponseEntity.ok(productService.findAllProductsLimitByPage(num).getContent());
        }

        if (filter.getPrefix() == null || filter.getPrefix().equals("") || filter.getPrefix().equals(" ")) {
            filter.setPrefix("");
        }

        if (filter.getMinPrice() == null) {
            filter.setMinPrice(-1.0);
        }
        // hmm .. ?
        if (filter.getMaxPrice() == null) {
            filter.setMaxPrice((double) (Integer.MAX_VALUE - 1));
        }

        if (filter.getMinPrice() > filter.getMaxPrice()) {
            throw new CustomIllegalArgumentException("Minimum price must be greater than maximum price !");
        }

        return ResponseEntity.ok(productService.findFilteredProductsByPriceAndName(filter, num).getContent());
    }
}
