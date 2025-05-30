package telran.order_service.controller;

import static telran.order_service.api.constants.ApiConstants.CANCEL_ORDER;
import static telran.order_service.api.constants.ApiConstants.CREATE_ORDER;
import static telran.order_service.api.constants.ApiConstants.GET_ORDERS_BY_CUSTOMER;
import static telran.order_service.api.constants.ApiConstants.GET_ORDER_BY_ID;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import telran.order_service.dto.OrderRequestDto;
import telran.order_service.dto.OrderResponseDto;
import telran.order_service.service.OrderService;

@RestController
@RequiredArgsConstructor
public class OrderController {
	
	 private final OrderService orderService;
	
	@PostMapping(CREATE_ORDER)
	@PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody @Valid OrderRequestDto request,
    		 @RequestHeader("x-user-id") UUID userId) {
		if (!request.customerId().equals(userId)) {
            throw new AccessDeniedException("You can only create orders for yourself");
        }
        OrderResponseDto created = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping(GET_ORDERS_BY_CUSTOMER)
    public ResponseEntity<List<OrderResponseDto>> getOrdersByCustomer(@PathVariable UUID customerId,
    		 @RequestHeader("x-user-id") UUID userId) {
    	 if (!customerId.equals(userId)) {
             throw new AccessDeniedException("You can only view your own orders");
         }
        List<OrderResponseDto> orders = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping(GET_ORDER_BY_ID)
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable @Valid UUID orderId,
    		 @RequestHeader("x-user-id") UUID userId) {
        OrderResponseDto order = orderService.getOrderById(orderId);
        if (!order.customerId().equals(userId)) {
            throw new AccessDeniedException("You can only view your own orders");
        }
        return ResponseEntity.ok(order);
    }
    
    @PutMapping(CANCEL_ORDER)
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Void> cancelOrder(@PathVariable @Valid UUID orderId,
    		@RequestHeader("x-user-id") UUID userId) {
    	  OrderResponseDto order = orderService.getOrderById(orderId);
    	  
        if (!order.customerId().equals(userId)) {
            throw new AccessDeniedException("You can only cancel your own orders");
        }
        return ResponseEntity.noContent().build();
    }
}
