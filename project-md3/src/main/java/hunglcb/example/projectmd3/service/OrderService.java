package hunglcb.example.projectmd3.service;

import hunglcb.example.projectmd3.model.Order;
import hunglcb.example.projectmd3.repository.IOrderRepository;
import hunglcb.example.projectmd3.repository.OrderRepository;

import java.util.List;

public class OrderService implements IOrderService {
    private final IOrderRepository orderRepository = new OrderRepository();
    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
