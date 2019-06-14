package oksa.marek.eshop.controllers.services;

import oksa.marek.eshop.controllers.repositories.IProductRepository;
import oksa.marek.eshop.entities.Product;
import oksa.marek.eshop.exeptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final Integer PRODUCTS_PER_PAGE = 10;

    @Autowired
    private IProductRepository repository;

    public Page<Product> findAllProductsLimitByPage(Integer pageNum) {
        return repository.findAllProductsLimitByPage(PageRequest.of(pageNum, PRODUCTS_PER_PAGE));
    }

    public Page<Product> findFilteredProductsByPriceAndName(Double minPrice, Double maxPrice, String prefix, Integer pageNum) {
        return repository.findFilteredProductsByPriceAndName(minPrice, maxPrice, prefix,
                PageRequest.of(pageNum, PRODUCTS_PER_PAGE));
    }

    public Product save(Product newProduct) {
        return repository.save(newProduct);
    }

    public Page<Product> findByName(String name, Integer pageNum) {
        return repository.findByName(name, PageRequest.of(pageNum, PRODUCTS_PER_PAGE));
    }

    public Product findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException(id, Product.class));
    }
}