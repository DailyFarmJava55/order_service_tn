package telran.order_service.dto;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;
import telran.order_service.model.Category;
import telran.order_service.model.Size;

public record SurpriseBagResponse(
		UUID id,
    String description,
    double price,
    int availableCount,
    LocalDateTime pickupTimeStart,
    LocalDateTime pickupTimeEnd,
    Set<Size> size,
    Set<Category> category
		) {}

