package telran.order_service.dao;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.order_service.model.Order;

public interface OrderRepository extends JpaRepository<Order, UUID> {

	List<Order> findByCustomerId(UUID customerId);
}
