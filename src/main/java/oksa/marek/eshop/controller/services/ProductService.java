package oksa.marek.eshop.controller.services;

import oksa.marek.eshop.controller.ProductFilter;
import oksa.marek.eshop.controller.repositories.IProductRepository;
import oksa.marek.eshop.model.entities.Product;
import oksa.marek.eshop.controller.errorhandlers.exceptions.CustomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private static final Integer PRODUCTS_PER_PAGE = 10;

    @Autowired
    private IProductRepository repository;

    public Page<Product> findAllProductsLimitByPage(Integer pageNum) {
        return repository.findAllProductsLimitByPage(PageRequest.of(pageNum, PRODUCTS_PER_PAGE));
    }

    public Page<Product> findFilteredProductsByPriceAndName(ProductFilter filter, Integer pageNum) {


        return repository.findFilteredProductsByPriceAndName(filter.getMinPrice(), filter.getMaxPrice(),
                filter.getPrefix(), PageRequest.of(pageNum, PRODUCTS_PER_PAGE));
    }

    public Product save(Product newProduct) {
        return repository.save(newProduct);
    }

    public Page<Product> findByName(String name, Integer pageNum) {
        Page<Product> products = repository.findByName(name, PageRequest.of(pageNum, PRODUCTS_PER_PAGE));

        if (products == null || products.getContent() == null || products.getContent().size() < 1)
            throw new CustomNotFoundException("name", name, Product.class);

        return products;
    }

    public Product findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CustomNotFoundException("id", id.toString(), Product.class));
    }

}
