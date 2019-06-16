package oksa.marek.eshop.controller.rest;

import oksa.marek.eshop.controller.services.OrderService;
import oksa.marek.eshop.model.entities.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Validated
public class RestOrderController {

    private final OrderService orderService;

    public RestOrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/api/admin/orders/page/{num}")
    public ResponseEntity<List<Order>> getAllOrders(@PathVariable
                                                    @PositiveOrZero(message = "Page number must be > 0 !") Integer num) {

        return new ResponseEntity<>(orderService.getOrdersLimitByPageNum(num).getContent(), HttpStatus.OK);
    }

    @GetMapping("/api/admin/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable
                                              @Positive(message = "Order id must be > 0 !") Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @PostMapping("/api/user/createOrder")
    public ResponseEntity<Order> createNewOrder(@RequestBody
                                                @NotNull(message = "New Order cannot be null !") @Valid Order newOrder) {

        return new ResponseEntity<>(orderService.save(newOrder), HttpStatus.OK);
    }
}
