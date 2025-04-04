package telran;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DailyFarmOrderServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DailyFarmOrderServiceApplication.class, args);
	}

}
