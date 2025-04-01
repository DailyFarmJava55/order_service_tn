package telran.order_service.api;

public interface ApiConstants {
	
	 String BASE_ORDERS = "/orders";

	String CREATE_ORDER = BASE_ORDERS;
	String GET_ORDER_BY_ID = BASE_ORDERS + "/{orderId}";
	String GET_ORDERS_BY_CUSTOMER = BASE_ORDERS + "/customer/{customerId}";
	String CANCEL_ORDER = BASE_ORDERS + "/{orderId}/cancel";
}
