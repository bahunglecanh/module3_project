package hunglcb.example.projectmd3.repository;

import hunglcb.example.projectmd3.model.Order;

import java.util.List;

public interface IOrderRepository {
    List<Order> findAll();
}
