package telran.order_service.feign;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import telran.order_service.dto.QuantityUpdateRequest;
import telran.order_service.dto.SurpriseBagResponse;
@Component
@FeignClient(name = "surprise-bag", url = "${surprise-bag.url}")
public interface SurpriseBagClient {

	@GetMapping("/internal/surprise-bag/{id}")
    SurpriseBagResponse getSurpriseBagById(@PathVariable UUID id);
	
	@PutMapping("/internal/surprise-bag/increment/{id}")
	void incrementAvailableCount(@PathVariable UUID id, @RequestBody QuantityUpdateRequest request);
	
	@PutMapping("/internal/surprise-bag/decrement/{id}")
	void decrementAvailableCount(@PathVariable UUID id, @RequestBody QuantityUpdateRequest request);
}
