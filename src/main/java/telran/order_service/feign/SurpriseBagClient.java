package telran.order_service.feign;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import telran.order_service.dto.SurpriseBagResponse;

@FeignClient(name = "surprise-bag", url = "${surprise-bag.url}")
public interface SurpriseBagClient {

	@GetMapping("/surprise_bag/{id}")
    SurpriseBagResponse getSurpriseBagById(@PathVariable UUID id);
}
