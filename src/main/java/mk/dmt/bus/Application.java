package mk.dmt.bus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		// Debug: Print all database-related environment variables
		System.out.println("=== Railway Environment Variables Debug ===");
		System.out.println("DATABASE_URL: " + (System.getenv("DATABASE_URL") != null ? "EXISTS" : "NOT SET"));
		System.out.println("PGHOST: " + System.getenv("PGHOST"));
		System.out.println("PGPORT: " + System.getenv("PGPORT"));
		System.out.println("PGUSER: " + System.getenv("PGUSER"));
		System.out.println("PGDATABASE: " + System.getenv("PGDATABASE"));
		System.out.println("CONSIDERATECONSIDERATION_PGHOST: " + System.getenv("CONSIDERATECONSIDERATION_PGHOST"));
		System.out.println("CONSIDERATECONSIDERATION_PGPORT: " + System.getenv("CONSIDERATECONSIDERATION_PGPORT"));
		System.out.println("CONSIDERATECONSIDERATION_PGUSER: " + System.getenv("CONSIDERATECONSIDERATION_PGUSER"));
		System.out.println("ONSIDERATECONSIDERATION_PGDATABASE: " + System.getenv("ONSIDERATECONSIDERATION_PGDATABASE"));
		System.out.println("POSTGRES_PASSWORD: " + (System.getenv("POSTGRES_PASSWORD") != null ? "EXISTS" : "NOT SET"));
		System.out.println("CONSIDERATECONSIDERATION_PGPASSWORD: " + (System.getenv("CONSIDERATECONSIDERATION_PGPASSWORD") != null ? "EXISTS" : "NOT SET"));
		System.out.println("PGPASSWORD: " + (System.getenv("PGPASSWORD") != null ? "EXISTS" : "NOT SET"));

		// Convert Railway's DATABASE_URL from postgresql:// to jdbc:postgresql://
		String databaseUrl = System.getenv("DATABASE_URL");
		if (databaseUrl != null && databaseUrl.startsWith("postgresql://")) {
			String jdbcUrl = "jdbc:" + databaseUrl;

			// Set as system property (Spring Boot can read this)
			System.setProperty("JDBC_DATABASE_URL", jdbcUrl);
			System.out.println("✓ Converted DATABASE_URL to JDBC format");
			System.out.println("  JDBC_DATABASE_URL=" + jdbcUrl.replaceAll(":[^:@]+@", ":****@"));

			// Also extract individual components from DATABASE_URL if they're not set
			// Format: postgresql://user:password@host:port/database
			try {
				java.net.URI uri = new java.net.URI(databaseUrl);
				if (System.getenv("PGHOST") == null && uri.getHost() != null) {
					System.setProperty("PGHOST", uri.getHost());
					System.out.println("✓ Extracted PGHOST from DATABASE_URL: " + uri.getHost());
				}
				if (System.getenv("PGPORT") == null && uri.getPort() > 0) {
					System.setProperty("PGPORT", String.valueOf(uri.getPort()));
					System.out.println("✓ Extracted PGPORT from DATABASE_URL: " + uri.getPort());
				}
				if (System.getenv("PGDATABASE") == null && uri.getPath() != null) {
					String database = uri.getPath().substring(1); // Remove leading /
					System.setProperty("PGDATABASE", database);
					System.out.println("✓ Extracted PGDATABASE from DATABASE_URL: " + database);
				}
				if (System.getenv("PGUSER") == null && uri.getUserInfo() != null) {
					String[] userInfo = uri.getUserInfo().split(":");
					System.setProperty("PGUSER", userInfo[0]);
					System.out.println("✓ Extracted PGUSER from DATABASE_URL: " + userInfo[0]);
					if (userInfo.length > 1 && System.getenv("POSTGRES_PASSWORD") == null && System.getenv("PGPASSWORD") == null) {
						System.setProperty("POSTGRES_PASSWORD", userInfo[1]);
						System.out.println("✓ Extracted POSTGRES_PASSWORD from DATABASE_URL");
					}
				}
			} catch (Exception e) {
				System.err.println("⚠ Failed to parse DATABASE_URL: " + e.getMessage());
			}
		} else if (databaseUrl != null) {
			System.out.println("⚠ DATABASE_URL exists but doesn't start with postgresql://: " + databaseUrl);
		} else {
			System.out.println("⚠ DATABASE_URL not found, will use individual variables or defaults");
		}
		System.out.println("==========================================");

		SpringApplication.run(Application.class, args);
	}

}
