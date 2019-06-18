package oksa.marek.eshop.controller.services;


import oksa.marek.eshop.controller.errorhandlers.exceptions.CustomIllegalArgumentException;
import oksa.marek.eshop.controller.errorhandlers.exceptions.CustomNotFoundException;
import oksa.marek.eshop.controller.repositories.IOrderRepository;
import oksa.marek.eshop.model.entities.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final static Integer ORDERS_PER_PAGE = 5;

    @Autowired
    private IOrderRepository repository;

    public Page<Order> getAllOrdersByUserIdForLoggedInUser(Long userId, Integer pageNum) {

        return repository.findAllOrdersByUserId(userId, PageRequest.of(pageNum, ORDERS_PER_PAGE));
    }

    public Page<Order> getOrdersLimitByPageNum(Integer pageNum) {
        return repository.getOrdersLimitByPageNum(PageRequest.of(pageNum, ORDERS_PER_PAGE));
    }

    public Order findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CustomNotFoundException("id", id.toString(), Order.class));
    }

    public Double getTotalPriceByOrderId(Long orderId) {
        return repository.getTotalPrice(orderId);
    }

    public Order save(Order newOrder) {

        newOrder = repository.save(newOrder);
        if (newOrder.getTotalPrice() != null) {
            return newOrder;
        }

        newOrder.setTotalPrice(getTotalPriceByOrderId(newOrder.getId()));
        System.out.println(newOrder);
        return repository.save(newOrder);
    }
}
