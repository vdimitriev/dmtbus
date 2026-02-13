package mk.dmt.bus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// Convert Railway's DATABASE_URL from postgresql:// to jdbc:postgresql://
		String databaseUrl = System.getenv("DATABASE_URL");
		if (databaseUrl != null && databaseUrl.startsWith("postgresql://")) {
			String jdbcUrl = "jdbc:" + databaseUrl;
			System.setProperty("JDBC_DATABASE_URL", jdbcUrl);
			System.out.println("Converted DATABASE_URL to JDBC format");
		}

		SpringApplication.run(Application.class, args);
	}

}
