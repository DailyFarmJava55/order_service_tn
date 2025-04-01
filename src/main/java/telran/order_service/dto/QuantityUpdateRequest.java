package telran.order_service.dto;

import jakarta.validation.constraints.Min;

public record QuantityUpdateRequest(
		@Min(1)
        int quantity
		) {

}
