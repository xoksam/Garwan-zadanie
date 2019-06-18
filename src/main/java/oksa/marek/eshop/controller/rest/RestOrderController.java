package oksa.marek.eshop.controller.rest;

import oksa.marek.eshop.controller.services.OrderService;
import oksa.marek.eshop.controller.services.OrderedProductService;
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
    private final OrderedProductService orderedProductService;

    public RestOrderController(OrderService orderService, UserService userService, OrderedProductService orderedProductService) {
        this.orderService = orderService;
        this.userService = userService;
        this.orderedProductService = orderedProductService;
    }

    @GetMapping("/user/orders/page/{num}")
    public ResponseEntity<List<Order>> getUserOrders(@PathVariable
                                                     @PositiveOrZero Integer num) {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userService.findByUserName(username);

        List<Order> orders = orderService.getAllOrdersByUserIdForLoggedInUser(currentUser.getId(), num).getContent();

        //I don't want to send to user his own details in the order
        orders.forEach(order -> order.setUser(null));

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/admin/orders/page/{num}")
    public ResponseEntity<List<Order>> getAllOrders(@PathVariable
                                                    @PositiveOrZero(message = "Page number must be > 0 !") Integer num) {
        //Here I don't want to send user's id and password to admin
        //It's not the most elegant way to do it, but it works ... ;D
        List<Order> orders = orderService.getOrdersLimitByPageNum(num).getContent();
        orders.forEach(order -> {
            order.getUser().setPassword(null);
            order.getUser().setId(null);
        });

        return ResponseEntity.ok(orders);
    }

    @GetMapping("/admin/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable
                                              @Positive(message = "Order id must be > 0 !")
                                                      Long id) {
        Order order = orderService.findById(id);
        order.getUser().setPassword(null);
        order.getUser().setId(null);

        return ResponseEntity.ok(order);
    }

    @PostMapping("/user/createOrder")
    @ResponseStatus(HttpStatus.CREATED)
    public void createNewOrder(@RequestBody
                               @NotNull(message = "New Order cannot be null !")
                               @Valid
                                       Order newOrder) {

        orderedProductService.saveAll(newOrder.getOrderedProducts());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        newOrder.setUser(userService.findByUserName(username));

        orderService.save(newOrder);
    }
}
