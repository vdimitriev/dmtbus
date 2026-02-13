-- V1__Initial_Schema.sql
-- Creates the initial database schema for the DMT Bus application

-- Countries table
CREATE TABLE countries (
    id BIGSERIAL PRIMARY KEY,
    code VARCHAR(3) NOT NULL UNIQUE,
    name VARCHAR(100) NOT NULL,
    name_local VARCHAR(100)
);

-- Cities table
CREATE TABLE cities (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    name_local VARCHAR(100),
    postal_code VARCHAR(20),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    country_id BIGINT NOT NULL,
    CONSTRAINT fk_cities_country FOREIGN KEY (country_id) REFERENCES countries(id)
);

CREATE INDEX idx_cities_country_id ON cities(country_id);
CREATE INDEX idx_cities_name ON cities(name);

-- Bus stations table
CREATE TABLE bus_stations (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    name_local VARCHAR(150),
    address VARCHAR(255),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    phone_number VARCHAR(50),
    is_main_station BOOLEAN DEFAULT FALSE,
    city_id BIGINT NOT NULL,
    CONSTRAINT fk_bus_stations_city FOREIGN KEY (city_id) REFERENCES cities(id)
);

CREATE INDEX idx_bus_stations_city_id ON bus_stations(city_id);
CREATE INDEX idx_bus_stations_name ON bus_stations(name);

-- Bus companies table
CREATE TABLE bus_companies (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    registration_number VARCHAR(50),
    address VARCHAR(255),
    phone_number VARCHAR(50),
    email VARCHAR(100),
    website VARCHAR(255),
    logo_url VARCHAR(500),
    is_active BOOLEAN DEFAULT TRUE,
    country_id BIGINT,
    CONSTRAINT fk_bus_companies_country FOREIGN KEY (country_id) REFERENCES countries(id)
);

CREATE INDEX idx_bus_companies_country_id ON bus_companies(country_id);
CREATE INDEX idx_bus_companies_name ON bus_companies(name);

-- Bus lines table
CREATE TABLE bus_lines (
    id BIGSERIAL PRIMARY KEY,
    line_number VARCHAR(50),
    name VARCHAR(200) NOT NULL,
    description VARCHAR(500),
    is_international BOOLEAN DEFAULT FALSE,
    is_active BOOLEAN DEFAULT TRUE,
    distance_km INTEGER,
    estimated_duration_minutes INTEGER,
    bus_company_id BIGINT NOT NULL,
    origin_station_id BIGINT NOT NULL,
    destination_station_id BIGINT NOT NULL,
    CONSTRAINT fk_bus_lines_company FOREIGN KEY (bus_company_id) REFERENCES bus_companies(id),
    CONSTRAINT fk_bus_lines_origin FOREIGN KEY (origin_station_id) REFERENCES bus_stations(id),
    CONSTRAINT fk_bus_lines_destination FOREIGN KEY (destination_station_id) REFERENCES bus_stations(id)
);

CREATE INDEX idx_bus_lines_company_id ON bus_lines(bus_company_id);
CREATE INDEX idx_bus_lines_origin_station_id ON bus_lines(origin_station_id);
CREATE INDEX idx_bus_lines_destination_station_id ON bus_lines(destination_station_id);

-- Bus line stops (intermediate stops)
CREATE TABLE bus_line_stops (
    id BIGSERIAL PRIMARY KEY,
    stop_order INTEGER NOT NULL,
    arrival_offset_minutes INTEGER,
    departure_offset_minutes INTEGER,
    distance_from_origin_km INTEGER,
    bus_line_id BIGINT NOT NULL,
    bus_station_id BIGINT NOT NULL,
    CONSTRAINT fk_bus_line_stops_line FOREIGN KEY (bus_line_id) REFERENCES bus_lines(id) ON DELETE CASCADE,
    CONSTRAINT fk_bus_line_stops_station FOREIGN KEY (bus_station_id) REFERENCES bus_stations(id)
);

CREATE INDEX idx_bus_line_stops_line_id ON bus_line_stops(bus_line_id);
CREATE INDEX idx_bus_line_stops_station_id ON bus_line_stops(bus_station_id);

-- Schedules table
CREATE TABLE schedules (
    id BIGSERIAL PRIMARY KEY,
    departure_time TIME NOT NULL,
    arrival_time TIME NOT NULL,
    day_of_week VARCHAR(20) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    valid_from DATE,
    valid_until DATE,
    notes VARCHAR(500),
    bus_line_id BIGINT NOT NULL,
    CONSTRAINT fk_schedules_bus_line FOREIGN KEY (bus_line_id) REFERENCES bus_lines(id) ON DELETE CASCADE
);

CREATE INDEX idx_schedules_bus_line_id ON schedules(bus_line_id);
CREATE INDEX idx_schedules_day_of_week ON schedules(day_of_week);

