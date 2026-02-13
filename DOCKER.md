# DMT Bus Application - Docker Setup

## Prerequisites
- Docker and Docker Compose installed on your system

## Running the Application

### Using Docker Compose (Recommended)

1. **Start the application and database:**
   ```bash
   docker-compose up -d
   ```

2. **View logs:**
   ```bash
   # View all logs
   docker-compose logs -f
   
   # View only app logs
   docker-compose logs -f dmtbus-app
   
   # View only database logs
   docker-compose logs -f postgres
   ```

3. **Stop the application:**
   ```bash
   docker-compose down
   ```

4. **Stop and remove all data (including database):**
   ```bash
   docker-compose down -v
   ```

### Building and Running Manually

1. **Build the Docker image:**
   ```bash
   docker build -t dmtbus-app .
   ```

2. **Run PostgreSQL database:**
   ```bash
   docker run -d \
     --name dmtbus-postgres \
     -e POSTGRES_DB=dmtbus \
     -e POSTGRES_USER=dmtbus \
     -e POSTGRES_PASSWORD=dmtbus \
     -p 5432:5432 \
     postgres:15-alpine
   ```

3. **Run the application:**
   ```bash
   docker run -d \
     --name dmtbus-app \
     --link dmtbus-postgres:postgres \
     -e SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/dmtbus \
     -e DB_USERNAME=dmtbus \
     -e DB_PASSWORD=dmtbus \
     -p 8080:8080 \
     dmtbus-app
   ```

## Accessing the Application

- **Application:** http://localhost:8080
- **API Documentation:** http://localhost:8080/swagger-ui.html
- **Health Check:** http://localhost:8080/actuator/health

## Environment Variables

The application supports the following environment variables:

- `SPRING_DATASOURCE_URL`: Database connection URL (default: jdbc:postgresql://localhost:5432/dmtbus)
- `DB_USERNAME`: Database username (default: dmtbus)
- `DB_PASSWORD`: Database password (default: dmtbus)

## Database

The PostgreSQL database will be automatically initialized with the schema and seed data using Flyway migrations located in `src/main/resources/db/migration/postgresql/`.

Data is persisted in a Docker volume named `postgres_data`.

## Troubleshooting

1. **Database connection issues:**
   - Ensure PostgreSQL container is healthy: `docker-compose ps`
   - Check logs: `docker-compose logs postgres`

2. **Application startup issues:**
   - Check application logs: `docker-compose logs dmtbus-app`
   - Verify database is accessible from the app container

3. **Port conflicts:**
   - If port 8080 or 5432 is already in use, modify the ports in docker-compose.yml

## Development

For development with hot reload, you can mount the source code:

```yaml
# Add this to the dmtbus-app service in docker-compose.yml
volumes:
  - ./src:/app/src
  - ./build:/app/build
```

Then rebuild the container when source changes are made.
