package telran;

import java.util.Scanner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableFeignClients
public class DailyFarmOrderServiceApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(DailyFarmOrderServiceApplication.class, args);
		
		new Thread(() -> {
			try (Scanner scanner = new Scanner(System.in)) {
				while (true) {
					System.out.print("Type 'exit' to shut down the application: ");
					String line = scanner.nextLine();
					if ("exit".equalsIgnoreCase(line.trim())) {
						System.out.println("Shutting down...");
						SpringApplication.exit(context, () -> 0);
						break;
					}
				}
			}
		}, "Shutdown-Listener").start();

	}

}
