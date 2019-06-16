package oksa.marek.eshop.controller.rest;

import oksa.marek.eshop.controller.services.OrderService;
import oksa.marek.eshop.controller.services.UserService;
import oksa.marek.eshop.model.entities.Order;
import oksa.marek.eshop.model.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final UserService userService;

    public RestOrderController(OrderService orderService, UserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @GetMapping("/user/orders/page/{num}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable
                                                     @PositiveOrZero Integer num) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findByUserName(username);

        return ResponseEntity.ok(orderService.getAllOrdersByUserIdForLoggedInUser(currentUser.getId(), num).getContent());
    }

    @GetMapping("/admin/orders/page/{num}")
    public ResponseEntity<List<Order>> getAllOrders(@PathVariable
                                                    @PositiveOrZero(message = "Page number must be > 0 !") Integer num) {

        return new ResponseEntity<>(orderService.getOrdersLimitByPageNum(num).getContent(), HttpStatus.OK);
    }

    @GetMapping("/admin/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable
                                              @Positive(message = "Order id must be > 0 !") Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @PostMapping("/user/createOrder")
    public ResponseEntity<Order> createNewOrder(@RequestBody
                                                @NotNull(message = "New Order cannot be null !") @Valid Order newOrder) {

        return new ResponseEntity<>(orderService.save(newOrder), HttpStatus.OK);
    }
}
