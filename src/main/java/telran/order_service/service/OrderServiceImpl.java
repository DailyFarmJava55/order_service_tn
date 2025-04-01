package telran.order_service.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import telran.order_service.dao.OrderRepository;
import telran.order_service.dto.OrderRequestDto;
import telran.order_service.dto.OrderResponseDto;
import telran.order_service.dto.SurpriseBagResponse;
import telran.order_service.feign.SurpriseBagClient;
import telran.order_service.model.Order;
import telran.order_service.model.OrderStatus;
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	private final OrderRepository orderRepository;
	private final SurpriseBagClient surpriseBagClient;

	@Override
	public OrderResponseDto createOrder(OrderRequestDto request) {
		
		 SurpriseBagResponse surpriseBag = surpriseBagClient.getSurpriseBagById(request.surpriseBagId());

		    if ( surpriseBag.availableCount() <= 0) {
		        throw new IllegalStateException("SurpriseBag is not available for order");
		    }
		    
		Order order = Order.builder()
				.customerId(request.customerId())
				.surpriseBagId(request.surpriseBagId())
				.status(OrderStatus.PENDING)
				.createdAt(LocalDateTime.now())
				.build();
		
		orderRepository.save(order);
		
		return mapToResponse(order);
	}

	@Override
	public List<OrderResponseDto> getOrdersByCustomerId(UUID customerId) {
		return orderRepository.findByCustomerId(customerId)
				.stream()
				.map(this::mapToResponse)
				.toList();
	}

	@Override
	public OrderResponseDto getOrderById(UUID orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new IllegalArgumentException("Order not found"));
		return mapToResponse(order);
	}
	
	 private OrderResponseDto mapToResponse(Order order) {
	        return new OrderResponseDto(
	                order.getId(),
	                order.getCustomerId(),
	                order.getSurpriseBagId(),
	                order.getStatus(),
	                order.getCreatedAt(),
	                order.getUpdatedAt()
	        );
	    }

	 @Override
	 public void cancelOrder(UUID orderId) {
	     Order order = orderRepository.findById(orderId)
	             .orElseThrow(() -> new IllegalArgumentException("Order not found"));

	     if (order.getStatus() == OrderStatus.CANCELLED) {
	         throw new IllegalStateException("Order is already cancelled");
	     }

	     order.setStatus(OrderStatus.CANCELLED);
	     order.setUpdatedAt(LocalDateTime.now());
	     orderRepository.save(order);
	 }

}
