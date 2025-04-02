package telran.order_service.service;

import static telran.order_service.api.constants.ErrorCodes.ORDER_ALREADY_CANCELLED;
import static telran.order_service.api.constants.ErrorCodes.ORDER_NOT_FOUND;
import static telran.order_service.api.constants.ErrorCodes.SURPRISE_BAG_NOT_AVAILABLE;
import static telran.order_service.api.constants.ValidationMessages.INVALID_QUANTITY;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import telran.order_service.dao.OrderRepository;
import telran.order_service.dto.OrderRequestDto;
import telran.order_service.dto.OrderResponseDto;
import telran.order_service.dto.QuantityUpdateRequest;
import telran.order_service.dto.SurpriseBagResponse;
import telran.order_service.feign.SurpriseBagClient;
import telran.order_service.model.Order;
import telran.order_service.model.OrderStatus;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
	private final OrderRepository orderRepository;
	private final SurpriseBagClient surpriseBagClient;

	@Override
	@Transactional
	public OrderResponseDto createOrder(OrderRequestDto request) {
		log.info("Creating order: customerId={}, surpriseBagId={}, quantity={}", 
	             request.customerId(), request.surpriseBagId(), request.quantity());
		
		 SurpriseBagResponse surpriseBag = surpriseBagClient.getSurpriseBagById(request.surpriseBagId());

		    if ( surpriseBag.availableCount() <= 0 ) {
		    	log.warn("Surprise bag {} is not available", request.surpriseBagId());
		        throw new IllegalStateException(SURPRISE_BAG_NOT_AVAILABLE);
		    }
		    if (request.quantity() <= 0) {
		    	 log.warn("Invalid quantity: {}", request.quantity());
		        throw new IllegalArgumentException(INVALID_QUANTITY);
		    }
		    
		Order order = Order.builder()
				.customerId(request.customerId())
				.surpriseBagId(request.surpriseBagId())
				.quantity(request.quantity())
				.status(OrderStatus.PENDING)
				.createdAt(LocalDateTime.now())
				.build();
		
		surpriseBagClient.decrementAvailableCount(
			    request.surpriseBagId(),
			    new QuantityUpdateRequest(request.quantity())
			);
		orderRepository.save(order);
		log.info("Order created with ID: {}", order.getId());
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
				.orElseThrow(() -> new IllegalArgumentException(ORDER_NOT_FOUND));
		return mapToResponse(order);
	}
	
	 private OrderResponseDto mapToResponse(Order order) {
	        return new OrderResponseDto(
	                order.getId(),
	                order.getCustomerId(),
	                order.getSurpriseBagId(),
	                order.getQuantity(),
	                order.getStatus(),
	                order.getCreatedAt(),
	                order.getUpdatedAt()
	        );
	    }

	 @Override
	 @Transactional
	 public void cancelOrder(UUID orderId) {
		 log.info("Cancelling order with ID: {}", orderId);
		 
	     Order order = orderRepository.findById(orderId)
	    		 .orElseThrow(() -> {
	                 log.error("Order not found: {}", orderId);
	                 return new IllegalArgumentException(ORDER_NOT_FOUND);
	             });

	     if (order.getStatus() == OrderStatus.CANCELLED) {
	    	 log.warn("Order {} is already cancelled", orderId);
	         throw new IllegalStateException(ORDER_ALREADY_CANCELLED);
	     }

	     order.setStatus(OrderStatus.CANCELLED);
	     order.setUpdatedAt(LocalDateTime.now());
	     
	     surpriseBagClient.incrementAvailableCount(
	    		    order.getSurpriseBagId(),
	    		    new QuantityUpdateRequest(order.getQuantity())
	    		);
	     
	     orderRepository.save(order);
	     log.info("Order {} cancelled successfully", orderId);
	 }

}
