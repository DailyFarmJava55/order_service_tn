package telran.order_service.dto;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;

public record OrderRequestDto (
	 @NotNull UUID customerId,
     @NotNull UUID surpriseBagId
) {}
