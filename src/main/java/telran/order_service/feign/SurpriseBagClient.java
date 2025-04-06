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

	@GetMapping("/surprise_bag/{id}")
    SurpriseBagResponse getSurpriseBagById(@PathVariable UUID id);
	
	@PutMapping("/surprise_bag/{id}/increment")
	void incrementAvailableCount(@PathVariable UUID id, @RequestBody QuantityUpdateRequest request);
	
	@PutMapping("/surprise_bag/{id}/decrement")
	void decrementAvailableCount(@PathVariable UUID id, @RequestBody QuantityUpdateRequest request);
}
