package oksa.marek.eshop.controller.services;

import oksa.marek.eshop.controller.errorhandlers.exceptions.CustomNotFoundException;
import oksa.marek.eshop.controller.repositories.IOrderedProductRepository;
import oksa.marek.eshop.model.entities.OrderedProduct;
import oksa.marek.eshop.model.entities.Product;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderedProductService {

    @Autowired
    private IOrderedProductRepository repository;

    public OrderedProduct save(OrderedProduct orderedProduct) {
        return repository.save(orderedProduct);
    }

    public OrderedProduct findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CustomNotFoundException("id", id.toString(), OrderedProduct.class));
    }

    public List<OrderedProduct> saveAll(List<OrderedProduct> orderedProducts) {
        return repository.saveAll(orderedProducts);
    }
}
