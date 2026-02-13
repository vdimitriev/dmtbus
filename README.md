# DMT Bus - Intercity Bus Lines Tracking Application

A comprehensive Spring Boot application for tracking intercity bus connections in North Macedonia, with support for international routes to neighboring countries.

## Features

- **Country Management**: Manage countries including North Macedonia and neighboring countries (Serbia, Bulgaria, Greece, Albania, Kosovo)
- **City Management**: Manage cities within countries with geolocation support
- **Bus Station Management**: Track bus stations with addresses, contact info, and coordinates
- **Bus Company Management**: Manage bus operators and their details
- **Bus Line Management**: Create and manage domestic and international bus routes with intermediate stops
- **Schedule Management**: Define departure/arrival times for each day of the week
- **Search Functionality**: Search for bus connections between cities by date or day of week

## Technology Stack

- **Java 17**
- **Spring Boot 4.0.2**
- **Spring Data JPA**
- **Flyway** for database migrations
- **H2 Database** (development)
- **PostgreSQL** (production)
- **Gradle** build system

## Getting Started

### Prerequisites

- Java 17 or higher
- Gradle 9.x (or use the included Gradle Wrapper)

### Running the Application

1. Clone the repository
2. Run with Gradle:
   ```bash
   ./gradlew bootRun
   ```
3. Access the API at `http://localhost:8080`
4. Access H2 Console at `http://localhost:8080/h2-console`
   - JDBC URL: `jdbc:h2:mem:dmtbus`
   - Username: `sa`
   - Password: (empty)

### Running for Production (PostgreSQL)

1. Create a PostgreSQL database named `dmtbus`
2. Update `application-prod.properties` with your database credentials
3. Run with the prod profile:
   ```bash
   ./gradlew bootRun --args='--spring.profiles.active=prod'
   ```

## API Documentation

### Countries API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/countries` | Get all countries |
| GET | `/api/countries/{id}` | Get country by ID |
| GET | `/api/countries/code/{code}` | Get country by code (e.g., "MK") |
| POST | `/api/countries` | Create a new country |
| PUT | `/api/countries/{id}` | Update a country |
| DELETE | `/api/countries/{id}` | Delete a country |

### Cities API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/cities` | Get all cities |
| GET | `/api/cities/{id}` | Get city by ID |
| GET | `/api/cities/country/{countryId}` | Get cities by country ID |
| GET | `/api/cities/country/code/{countryCode}` | Get cities by country code |
| GET | `/api/cities/search?name={name}` | Search cities by name |
| POST | `/api/cities` | Create a new city |
| PUT | `/api/cities/{id}` | Update a city |
| DELETE | `/api/cities/{id}` | Delete a city |

### Bus Stations API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/stations` | Get all stations |
| GET | `/api/stations/{id}` | Get station by ID |
| GET | `/api/stations/city/{cityId}` | Get stations by city |
| GET | `/api/stations/country/{countryCode}` | Get stations by country |
| GET | `/api/stations/main` | Get main stations only |
| GET | `/api/stations/search?name={name}` | Search stations by name |
| POST | `/api/stations` | Create a new station |
| PUT | `/api/stations/{id}` | Update a station |
| DELETE | `/api/stations/{id}` | Delete a station |

### Bus Companies API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/companies` | Get all companies |
| GET | `/api/companies/{id}` | Get company by ID |
| GET | `/api/companies/country/{countryCode}` | Get companies by country |
| GET | `/api/companies/active` | Get active companies |
| GET | `/api/companies/search?name={name}` | Search companies by name |
| POST | `/api/companies` | Create a new company |
| PUT | `/api/companies/{id}` | Update a company |
| PATCH | `/api/companies/{id}/toggle-status` | Toggle company active status |
| DELETE | `/api/companies/{id}` | Delete a company |

### Bus Lines API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/lines` | Get all bus lines |
| GET | `/api/lines/{id}` | Get bus line by ID |
| GET | `/api/lines/active` | Get active bus lines |
| GET | `/api/lines/international` | Get international lines |
| GET | `/api/lines/domestic` | Get domestic lines |
| GET | `/api/lines/company/{companyId}` | Get lines by company |
| GET | `/api/lines/city/{cityId}` | Get lines by city |
| GET | `/api/lines/country/{countryCode}` | Get lines by country |
| GET | `/api/lines/route?originCityId={}&destinationCityId={}` | Search routes |
| POST | `/api/lines` | Create a new bus line |
| PUT | `/api/lines/{id}` | Update a bus line |
| PATCH | `/api/lines/{id}/toggle-status` | Toggle line active status |
| DELETE | `/api/lines/{id}` | Delete a bus line |

### Schedules API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/schedules` | Get all schedules |
| GET | `/api/schedules/{id}` | Get schedule by ID |
| GET | `/api/schedules/line/{busLineId}` | Get schedules by bus line |
| GET | `/api/schedules/line/{busLineId}/day/{dayOfWeek}` | Get schedules by line and day |
| POST | `/api/schedules` | Create a new schedule |
| POST | `/api/schedules/line/{busLineId}/weekly` | Create weekly schedules |
| PUT | `/api/schedules/{id}` | Update a schedule |
| PATCH | `/api/schedules/{id}/toggle-status` | Toggle schedule status |
| DELETE | `/api/schedules/{id}` | Delete a schedule |

### Search API

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/search?originCityId={}&destinationCityId={}&date={}` | Search connections by date |
| GET | `/api/search/day?originCityId={}&destinationCityId={}&dayOfWeek={}` | Search connections by day of week |

## Sample Data

The application comes pre-loaded with sample data including:

### Countries
- North Macedonia (MK)
- Serbia (RS)
- Bulgaria (BG)
- Greece (GR)
- Albania (AL)
- Kosovo (XK)

### Cities in North Macedonia
- Skopje, Bitola, Kumanovo, Prilep, Tetovo, Ohrid, Veles, Strumica, Gostivar, Kavadarci, Struga, Kočani, Kičevo, Štip, Gevgelija

### International Cities
- Belgrade (Serbia), Niš (Serbia), Sofia (Bulgaria), Thessaloniki (Greece), Athens (Greece), Tirana (Albania), Pristina (Kosovo)

### Bus Companies
- Galeb Transport
- Transkop
- Makedonia Express
- Struga Trans
- Vardar Express

### Sample Bus Lines
- **Domestic**: Skopje-Bitola, Skopje-Ohrid, Skopje-Kumanovo, Skopje-Strumica
- **International**: Skopje-Belgrade, Skopje-Thessaloniki, Skopje-Sofia

## Example API Usage

### Search for buses from Skopje to Bitola on Monday
```bash
curl "http://localhost:8080/api/search/day?originCityId=1&destinationCityId=2&dayOfWeek=MONDAY"
```

### Get all cities in North Macedonia
```bash
curl "http://localhost:8080/api/cities/country/code/MK"
```

### Create a new bus line
```bash
curl -X POST http://localhost:8080/api/lines \
  -H "Content-Type: application/json" \
  -d '{
    "lineNumber": "D005",
    "name": "Skopje - Tetovo",
    "busCompanyId": 1,
    "originStationId": 1,
    "destinationStationId": 5,
    "distanceKm": 42,
    "estimatedDurationMinutes": 45,
    "isInternational": false
  }'
```

## Future Enhancements

- Ticket pricing information
- Real-time bus tracking
- User authentication and authorization
- Booking integration
- Mobile application support
- Multi-language support (Macedonian, Albanian, English)
- Email notifications for schedule changes

## License

This project is open source and available under the MIT License.

