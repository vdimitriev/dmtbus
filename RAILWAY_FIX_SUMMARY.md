# Railway Deployment Fix Summary

## Problem Fixed
The application was receiving a malformed JDBC URL: `jdbc:postgresql://:/` because Railway's environment variables weren't being properly handled.

## Root Cause
Railway provides the `DATABASE_URL` environment variable in PostgreSQL format:
```
postgresql://user:password@host:port/database
```

But Spring Boot's DataSource expects JDBC format:
```
jdbc:postgresql://host:port/database
```

## Solution Implemented

### 1. Updated Application.java
Added automatic conversion of Railway's `DATABASE_URL` to JDBC format before Spring Boot initialization:

```java
String databaseUrl = System.getenv("DATABASE_URL");
if (databaseUrl != null && databaseUrl.startsWith("postgresql://")) {
    String jdbcUrl = "jdbc:" + databaseUrl;
    System.setProperty("JDBC_DATABASE_URL", jdbcUrl);
}
```

### 2. Updated application-prod.properties
Added fallback chain for database configuration:

```properties
spring.datasource.url=${JDBC_DATABASE_URL:jdbc:postgresql://${PGHOST:localhost}:${PGPORT:5432}/${PGDATABASE:dmtbus}}
spring.datasource.username=${PGUSER:dmtbus}
spring.datasource.password=${POSTGRES_PASSWORD:${PGPASSWORD:dmtbus}}
```

This configuration:
- **First tries**: `JDBC_DATABASE_URL` (converted from Railway's `DATABASE_URL`)
- **Falls back to**: Individual variables (`PGHOST`, `PGPORT`, `PGDATABASE`)
- **Finally defaults to**: localhost for local development

### 3. Fixed Flyway Configuration
Updated Flyway to use PostgreSQL-specific migrations:
```properties
spring.flyway.locations=classpath:db/migration/postgresql
```

## How It Works Now

### On Railway:
1. Railway automatically provides `DATABASE_URL` when PostgreSQL service is added
2. Application converts it to JDBC format on startup
3. Spring Boot uses the converted URL to connect
4. Flyway runs PostgreSQL migrations
5. Application starts successfully

### Locally (with Docker Compose):
1. Docker Compose sets individual variables (`PGHOST`, `PGPORT`, etc.)
2. Application builds JDBC URL from these variables
3. Works exactly the same way

### For Development (no environment variables):
1. Falls back to localhost:5432/dmtbus
2. Uses default credentials

## What You Need to Do

### For Railway Deployment:

1. **Commit and push your changes:**
   ```bash
   git add .
   git commit -m "Fix Railway database connection"
   git push origin main
   ```

2. **Ensure SPRING_PROFILES_ACTIVE is set in Railway:**
   - Go to your application service in Railway
   - Click "Variables"
   - Add: `SPRING_PROFILES_ACTIVE = prod`

3. **Redeploy** (Railway will do this automatically after push)

4. **Verify in logs:**
   - You should see: "Converted DATABASE_URL to JDBC format"
   - You should see: "HikariPool-1 - Starting..."
   - You should NOT see: "Connection to localhost:5432 refused"

## Testing Locally

To test the Railway configuration locally:

```bash
# Set Railway-style DATABASE_URL
export DATABASE_URL="postgresql://dmtbus:dmtbus@localhost:5432/dmtbus"
export SPRING_PROFILES_ACTIVE=prod

# Run the application
java -jar build/libs/dmtbus-0.0.1-SNAPSHOT.jar
```

Or with Docker Compose:
```bash
docker compose up
```

## Troubleshooting

If you still see connection errors:

1. **Check DATABASE_URL exists in Railway:**
   - Go to your app service → Variables
   - Look for `DATABASE_URL` (should reference your Postgres service)

2. **Check Spring profile is activated:**
   - In logs, look for: "The following profiles are active: prod"

3. **Verify PostgreSQL service is running:**
   - In Railway dashboard, ensure Postgres service shows green status

4. **Check Postgres service name:**
   - If your Postgres service has a different name, the `DATABASE_URL` reference might be different
   - Go to Postgres service → Variables to see the correct reference format

## Files Modified

1. `src/main/java/mk/dmt/bus/Application.java` - Added DATABASE_URL conversion
2. `src/main/resources/application-prod.properties` - Updated datasource configuration with fallbacks
3. `railway.json` - Added SPRING_PROFILES_ACTIVE
4. `RAILWAY_TROUBLESHOOTING.md` - Updated with new instructions

## Next Steps

After successful deployment:
1. Test your API endpoints: `https://your-app.railway.app/swagger-ui.html`
2. Check health: `https://your-app.railway.app/actuator/health`
3. Verify database migrations ran successfully in the logs

