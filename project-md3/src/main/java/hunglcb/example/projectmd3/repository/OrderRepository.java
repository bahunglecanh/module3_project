package hunglcb.example.projectmd3.repository;
import hunglcb.example.projectmd3.model.Order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository implements IOrderRepository {

    private final String SHOW_ORDERS = "SELECT * FROM orders ORDER BY created_at DESC";

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();

        try(Connection connection = ConnectionDB.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(SHOW_ORDERS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Order o = new Order();
                o.setId(resultSet.getInt("id"));
                o.setAccountId(resultSet.getInt("account_id"));
                o.setShippingAddressId(resultSet.getInt("shipping_address_id"));
                o.setStatus(resultSet.getString("status"));
                o.setTotalAmount(resultSet.getDouble("total_amount"));
                o.setCreatedAt(resultSet.getTimestamp("created_at"));
                orders.add(o);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return orders;
    }
}
