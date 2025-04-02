package telran.order_service.api.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
		 LocalDateTime timestamp,
	        String errorCode,
	        String message
		) {

}
