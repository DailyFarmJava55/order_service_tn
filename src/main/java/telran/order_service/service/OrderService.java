package telran.order_service.service;

import java.util.List;
import java.util.UUID;

import telran.order_service.dto.OrderRequestDto;
import telran.order_service.dto.OrderResponseDto;

public interface OrderService {
	OrderResponseDto createOrder(OrderRequestDto request);
    List<OrderResponseDto> getOrdersByCustomerId(UUID customerId);
    OrderResponseDto getOrderById(UUID orderId);
    void cancelOrder(UUID orderId);
}
