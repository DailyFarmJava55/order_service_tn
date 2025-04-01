package telran.order_service.controller;

import static telran.order_service.api.ApiConstants.CANCEL_ORDER;
import static telran.order_service.api.ApiConstants.CREATE_ORDER;
import static telran.order_service.api.ApiConstants.GET_ORDERS_BY_CUSTOMER;
import static telran.order_service.api.ApiConstants.GET_ORDER_BY_ID;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ResponseEntity<OrderResponseDto> createOrder(@RequestBody @Valid OrderRequestDto request) {
        OrderResponseDto created = orderService.createOrder(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping(GET_ORDERS_BY_CUSTOMER)
    public ResponseEntity<List<OrderResponseDto>> getOrdersByCustomer(@PathVariable UUID customerId) {
        List<OrderResponseDto> orders = orderService.getOrdersByCustomerId(customerId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping(GET_ORDER_BY_ID)
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable @Valid UUID orderId) {
        OrderResponseDto order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }
    
    @PutMapping(CANCEL_ORDER)
    public ResponseEntity<Void> cancelOrder(@PathVariable @Valid UUID orderId) {
        orderService.cancelOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
