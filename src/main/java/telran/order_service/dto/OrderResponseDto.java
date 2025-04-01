package telran.order_service.dto;

import java.time.LocalDateTime;
import java.util.UUID;

import telran.order_service.model.OrderStatus;

public record OrderResponseDto(
		  	UUID id,
	        UUID customerId,
	        UUID surpriseBagId,
	        int quantity,
	        OrderStatus status,
	        LocalDateTime createdAt,
	        LocalDateTime updatedAt
) {}

