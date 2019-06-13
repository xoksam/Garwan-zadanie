package oksa.marek.eshop.controllers.rest;

import oksa.marek.eshop.controllers.repositories.IOrderRepository;
import oksa.marek.eshop.entities.Order;
import oksa.marek.eshop.exeptions.MyIllegalArgumentException;
import oksa.marek.eshop.exeptions.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.List;

@RestController
public class RestOrderController {
    private final IOrderRepository repository;

    public RestOrderController(IOrderRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/admin/orders/page/{num}")
    public ResponseEntity<List<Order>> getAllOrders(@PathVariable Integer num) {

        if(num <= 0) throw new MyIllegalArgumentException("Page number must be > 0 !");

        return new ResponseEntity<List<Order>>(repository.getOrdersLimitByPageNum((num - 1) * 10), HttpStatus.OK);
    }

    @GetMapping("/api/orders/{id}")
    public ResponseEntity<Order> getProductById(@PathVariable Long id) {
        Order order = repository.findById(id).orElseThrow(() -> new NotFoundException(id, Order.class));

        return new ResponseEntity<Order>(order, HttpStatus.OK);
    }

    @PostMapping("/api/addOrder")
    public ResponseEntity<Order> addNewOrder(@RequestBody Order newOrder) {

        if(newOrder.getTotalPrice() == null) {
            newOrder.setTotalPrice(repository.getTotalPrice());
        }

        return new ResponseEntity<Order>(repository.save(newOrder), HttpStatus.OK);
    }
}
