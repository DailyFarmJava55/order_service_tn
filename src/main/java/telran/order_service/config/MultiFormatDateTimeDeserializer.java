package telran.order_service.config;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

public class MultiFormatDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {
	
	 private static final List<DateTimeFormatter> FORMATTERS = List.of(
	            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
	            DateTimeFormatter.ISO_LOCAL_DATE_TIME // yyyy-MM-dd'T'HH:mm:ss
	    );
	

	 @Override
	    public LocalDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
	        String value = p.getText().trim();
	        for (DateTimeFormatter formatter : FORMATTERS) {
	            try {
	                return LocalDateTime.parse(value, formatter);
	            } catch (Exception e) {
	                // Try next
	            }
	        }
	        throw new IOException("Cannot parse date time: " + value);
	    }
	}
