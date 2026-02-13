-- V2__Seed_Data.sql
-- Seeds initial data for North Macedonia

-- Insert North Macedonia and neighboring countries
INSERT INTO countries (code, name, name_local) VALUES
('MK', 'North Macedonia', 'Северна Македонија'),
('RS', 'Serbia', 'Србија'),
('BG', 'Bulgaria', 'България'),
('GR', 'Greece', 'Ελλάδα'),
('AL', 'Albania', 'Shqipëria'),
('XK', 'Kosovo', 'Kosovë');

-- Insert major cities in North Macedonia
INSERT INTO cities (name, name_local, postal_code, latitude, longitude, country_id) VALUES
('Skopje', 'Скопје', '1000', 41.9981, 21.4254, (SELECT id FROM countries WHERE code = 'MK')),
('Bitola', 'Битола', '7000', 41.0297, 21.3292, (SELECT id FROM countries WHERE code = 'MK')),
('Kumanovo', 'Куманово', '1300', 42.1322, 21.7144, (SELECT id FROM countries WHERE code = 'MK')),
('Prilep', 'Прилеп', '7500', 41.3449, 21.5544, (SELECT id FROM countries WHERE code = 'MK')),
('Tetovo', 'Тетово', '1200', 42.0069, 20.9715, (SELECT id FROM countries WHERE code = 'MK')),
('Ohrid', 'Охрид', '6000', 41.1231, 20.8016, (SELECT id FROM countries WHERE code = 'MK')),
('Veles', 'Велес', '1400', 41.7158, 21.7753, (SELECT id FROM countries WHERE code = 'MK')),
('Strumica', 'Струмица', '2400', 41.4378, 22.6427, (SELECT id FROM countries WHERE code = 'MK')),
('Gostivar', 'Гостивар', '1230', 41.7958, 20.9086, (SELECT id FROM countries WHERE code = 'MK')),
('Kavadarci', 'Кавадарци', '1430', 41.4328, 22.0114, (SELECT id FROM countries WHERE code = 'MK')),
('Struga', 'Струга', '6330', 41.1775, 20.6783, (SELECT id FROM countries WHERE code = 'MK')),
('Kočani', 'Кочани', '2300', 41.9164, 22.4128, (SELECT id FROM countries WHERE code = 'MK')),
('Kičevo', 'Кичево', '6250', 41.5128, 20.9589, (SELECT id FROM countries WHERE code = 'MK')),
('Štip', 'Штип', '2000', 41.7458, 22.1903, (SELECT id FROM countries WHERE code = 'MK')),
('Gevgelija', 'Гевгелија', '1480', 41.1419, 22.5025, (SELECT id FROM countries WHERE code = 'MK'));

-- Insert cities from neighboring countries for international routes
INSERT INTO cities (name, name_local, postal_code, latitude, longitude, country_id) VALUES
('Belgrade', 'Београд', '11000', 44.7866, 20.4489, (SELECT id FROM countries WHERE code = 'RS')),
('Niš', 'Ниш', '18000', 43.3209, 21.8958, (SELECT id FROM countries WHERE code = 'RS')),
('Sofia', 'София', '1000', 42.6977, 23.3219, (SELECT id FROM countries WHERE code = 'BG')),
('Thessaloniki', 'Θεσσαλονίκη', '54624', 40.6401, 22.9444, (SELECT id FROM countries WHERE code = 'GR')),
('Athens', 'Αθήνα', '10431', 37.9838, 23.7275, (SELECT id FROM countries WHERE code = 'GR')),
('Tirana', 'Tiranë', '1001', 41.3275, 19.8187, (SELECT id FROM countries WHERE code = 'AL')),
('Pristina', 'Prishtinë', '10000', 42.6629, 21.1655, (SELECT id FROM countries WHERE code = 'XK'));

-- Insert main bus stations
INSERT INTO bus_stations (name, name_local, address, latitude, longitude, phone_number, is_main_station, city_id) VALUES
('Skopje Main Bus Station', 'Автобуска станица Скопје', 'Bul. Jane Sandanski bb', 41.9981, 21.4254, '+389 2 2466 011', true, (SELECT id FROM cities WHERE name = 'Skopje' AND postal_code = '1000')),
('Bitola Bus Station', 'Автобуска станица Битола', 'Ul. Nikola Tesla bb', 41.0297, 21.3292, '+389 47 237 033', true, (SELECT id FROM cities WHERE name = 'Bitola')),
('Kumanovo Bus Station', 'Автобуска станица Куманово', 'Ul. 11 Oktomvri bb', 42.1322, 21.7144, '+389 31 423 403', true, (SELECT id FROM cities WHERE name = 'Kumanovo')),
('Prilep Bus Station', 'Автобуска станица Прилеп', 'Ul. Marksova bb', 41.3449, 21.5544, '+389 48 423 555', true, (SELECT id FROM cities WHERE name = 'Prilep')),
('Tetovo Bus Station', 'Автобуска станица Тетово', 'Ul. Ilindenska bb', 42.0069, 20.9715, '+389 44 331 212', true, (SELECT id FROM cities WHERE name = 'Tetovo')),
('Ohrid Bus Station', 'Автобуска станица Охрид', 'Turistička bb', 41.1231, 20.8016, '+389 46 260 339', true, (SELECT id FROM cities WHERE name = 'Ohrid')),
('Veles Bus Station', 'Автобуска станица Велес', 'Ul. Marshal Tito bb', 41.7158, 21.7753, '+389 43 231 456', true, (SELECT id FROM cities WHERE name = 'Veles')),
('Strumica Bus Station', 'Автобуска станица Струмица', 'Ul. Goce Delčev bb', 41.4378, 22.6427, '+389 34 345 678', true, (SELECT id FROM cities WHERE name = 'Strumica')),
('Gostivar Bus Station', 'Автобуска станица Гостивар', 'Ul. Braќa Ginoski bb', 41.7958, 20.9086, '+389 42 213 456', true, (SELECT id FROM cities WHERE name = 'Gostivar')),
('Štip Bus Station', 'Автобуска станица Штип', 'Ul. Vasil Glavinov bb', 41.7458, 22.1903, '+389 32 392 234', true, (SELECT id FROM cities WHERE name = 'Štip')),
('Gevgelija Bus Station', 'Автобуска станица Гевгелија', 'Ul. 7 Noemvri bb', 41.1419, 22.5025, '+389 34 212 345', true, (SELECT id FROM cities WHERE name = 'Gevgelija')),
('Belgrade Bus Station', 'Београдска аутобуска станица', 'Železnička 4', 44.7866, 20.4489, '+381 11 2636 299', true, (SELECT id FROM cities WHERE name = 'Belgrade')),
('Thessaloniki KTEL', 'ΚΤΕΛ Θεσσαλονίκης', 'Monastiriou 319', 40.6401, 22.9444, '+30 2310 595 408', true, (SELECT id FROM cities WHERE name = 'Thessaloniki')),
('Sofia Central Bus Station', 'Централна автогара София', 'bul. Knyaginya Maria Luiza 100', 42.6977, 23.3219, '+359 2 900 21 00', true, (SELECT id FROM cities WHERE name = 'Sofia'));

-- Insert sample bus companies
INSERT INTO bus_companies (name, registration_number, address, phone_number, email, website, is_active, country_id) VALUES
('Galeb Transport', 'MK-001234', 'Skopje, North Macedonia', '+389 2 2465 000', 'info@galeb.mk', 'https://galeb.mk', true, (SELECT id FROM countries WHERE code = 'MK')),
('Transkop', 'MK-002345', 'Bitola, North Macedonia', '+389 47 238 000', 'info@transkop.mk', 'https://transkop.mk', true, (SELECT id FROM countries WHERE code = 'MK')),
('Makedonia Express', 'MK-003456', 'Skopje, North Macedonia', '+389 2 2467 000', 'info@makexp.mk', 'https://makexp.mk', true, (SELECT id FROM countries WHERE code = 'MK')),
('Struga Trans', 'MK-004567', 'Struga, North Macedonia', '+389 46 781 000', 'info@strugatrans.mk', 'https://strugatrans.mk', true, (SELECT id FROM countries WHERE code = 'MK')),
('Vardar Express', 'MK-005678', 'Veles, North Macedonia', '+389 43 232 000', 'info@vardarexp.mk', 'https://vardarexp.mk', true, (SELECT id FROM countries WHERE code = 'MK'));

-- Insert sample domestic bus lines
INSERT INTO bus_lines (line_number, name, description, is_international, is_active, distance_km, estimated_duration_minutes, bus_company_id, origin_station_id, destination_station_id) VALUES
('D001', 'Skopje - Bitola', 'Direct line from Skopje to Bitola via Veles and Prilep', false, true, 167, 180,
    (SELECT id FROM bus_companies WHERE name = 'Galeb Transport'),
    (SELECT id FROM bus_stations WHERE name = 'Skopje Main Bus Station'),
    (SELECT id FROM bus_stations WHERE name = 'Bitola Bus Station')),
('D002', 'Skopje - Ohrid', 'Direct line from Skopje to Ohrid via Tetovo and Gostivar', false, true, 170, 195,
    (SELECT id FROM bus_companies WHERE name = 'Struga Trans'),
    (SELECT id FROM bus_stations WHERE name = 'Skopje Main Bus Station'),
    (SELECT id FROM bus_stations WHERE name = 'Ohrid Bus Station')),
('D003', 'Skopje - Kumanovo', 'Regular line from Skopje to Kumanovo', false, true, 40, 45,
    (SELECT id FROM bus_companies WHERE name = 'Makedonia Express'),
    (SELECT id FROM bus_stations WHERE name = 'Skopje Main Bus Station'),
    (SELECT id FROM bus_stations WHERE name = 'Kumanovo Bus Station')),
('D004', 'Skopje - Strumica', 'Line from Skopje to Strumica via Štip', false, true, 145, 150,
    (SELECT id FROM bus_companies WHERE name = 'Vardar Express'),
    (SELECT id FROM bus_stations WHERE name = 'Skopje Main Bus Station'),
    (SELECT id FROM bus_stations WHERE name = 'Strumica Bus Station'));

-- Insert sample international bus lines
INSERT INTO bus_lines (line_number, name, description, is_international, is_active, distance_km, estimated_duration_minutes, bus_company_id, origin_station_id, destination_station_id) VALUES
('I001', 'Skopje - Belgrade', 'International line from Skopje to Belgrade', true, true, 430, 420,
    (SELECT id FROM bus_companies WHERE name = 'Galeb Transport'),
    (SELECT id FROM bus_stations WHERE name = 'Skopje Main Bus Station'),
    (SELECT id FROM bus_stations WHERE name = 'Belgrade Bus Station')),
('I002', 'Skopje - Thessaloniki', 'International line from Skopje to Thessaloniki', true, true, 260, 270,
    (SELECT id FROM bus_companies WHERE name = 'Makedonia Express'),
    (SELECT id FROM bus_stations WHERE name = 'Skopje Main Bus Station'),
    (SELECT id FROM bus_stations WHERE name = 'Thessaloniki KTEL')),
('I003', 'Skopje - Sofia', 'International line from Skopje to Sofia', true, true, 245, 300,
    (SELECT id FROM bus_companies WHERE name = 'Transkop'),
    (SELECT id FROM bus_stations WHERE name = 'Skopje Main Bus Station'),
    (SELECT id FROM bus_stations WHERE name = 'Sofia Central Bus Station'));

-- Add intermediate stops for Skopje - Bitola line
INSERT INTO bus_line_stops (stop_order, arrival_offset_minutes, departure_offset_minutes, distance_from_origin_km, bus_line_id, bus_station_id) VALUES
(1, 60, 65, 55, (SELECT id FROM bus_lines WHERE line_number = 'D001'), (SELECT id FROM bus_stations WHERE name = 'Veles Bus Station')),
(2, 120, 125, 117, (SELECT id FROM bus_lines WHERE line_number = 'D001'), (SELECT id FROM bus_stations WHERE name = 'Prilep Bus Station'));

-- Add intermediate stops for Skopje - Ohrid line
INSERT INTO bus_line_stops (stop_order, arrival_offset_minutes, departure_offset_minutes, distance_from_origin_km, bus_line_id, bus_station_id) VALUES
(1, 40, 45, 42, (SELECT id FROM bus_lines WHERE line_number = 'D002'), (SELECT id FROM bus_stations WHERE name = 'Tetovo Bus Station')),
(2, 90, 95, 95, (SELECT id FROM bus_lines WHERE line_number = 'D002'), (SELECT id FROM bus_stations WHERE name = 'Gostivar Bus Station'));

-- Add intermediate stops for Skopje - Strumica line
INSERT INTO bus_line_stops (stop_order, arrival_offset_minutes, departure_offset_minutes, distance_from_origin_km, bus_line_id, bus_station_id) VALUES
(1, 80, 85, 80, (SELECT id FROM bus_lines WHERE line_number = 'D004'), (SELECT id FROM bus_stations WHERE name = 'Štip Bus Station'));

-- Add schedules for domestic lines (weekdays)
INSERT INTO schedules (departure_time, arrival_time, day_of_week, is_active, bus_line_id) VALUES
-- Skopje - Bitola (D001)
('06:00', '09:00', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('08:00', '11:00', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('10:00', '13:00', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('14:00', '17:00', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('17:00', '20:00', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('06:00', '09:00', 'TUESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('08:00', '11:00', 'TUESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('10:00', '13:00', 'TUESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('14:00', '17:00', 'TUESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('17:00', '20:00', 'TUESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('06:00', '09:00', 'WEDNESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('08:00', '11:00', 'WEDNESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('10:00', '13:00', 'WEDNESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('14:00', '17:00', 'WEDNESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('17:00', '20:00', 'WEDNESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('06:00', '09:00', 'THURSDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('08:00', '11:00', 'THURSDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('10:00', '13:00', 'THURSDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('14:00', '17:00', 'THURSDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('17:00', '20:00', 'THURSDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('06:00', '09:00', 'FRIDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('08:00', '11:00', 'FRIDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('10:00', '13:00', 'FRIDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('14:00', '17:00', 'FRIDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('17:00', '20:00', 'FRIDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('07:00', '10:00', 'SATURDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('10:00', '13:00', 'SATURDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('15:00', '18:00', 'SATURDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('08:00', '11:00', 'SUNDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001')),
('14:00', '17:00', 'SUNDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D001'));

-- Skopje - Ohrid (D002)
INSERT INTO schedules (departure_time, arrival_time, day_of_week, is_active, bus_line_id) VALUES
('06:30', '09:45', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('09:00', '12:15', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('13:00', '16:15', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('16:30', '19:45', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('06:30', '09:45', 'TUESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('09:00', '12:15', 'TUESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('13:00', '16:15', 'TUESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('16:30', '19:45', 'TUESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('06:30', '09:45', 'WEDNESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('09:00', '12:15', 'WEDNESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('13:00', '16:15', 'WEDNESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('16:30', '19:45', 'WEDNESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('06:30', '09:45', 'THURSDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('09:00', '12:15', 'THURSDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('13:00', '16:15', 'THURSDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('16:30', '19:45', 'THURSDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('06:30', '09:45', 'FRIDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('09:00', '12:15', 'FRIDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('13:00', '16:15', 'FRIDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('16:30', '19:45', 'FRIDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('07:00', '10:15', 'SATURDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('11:00', '14:15', 'SATURDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('16:00', '19:15', 'SATURDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('09:00', '12:15', 'SUNDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002')),
('15:00', '18:15', 'SUNDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D002'));

-- Skopje - Kumanovo (D003) - More frequent
INSERT INTO schedules (departure_time, arrival_time, day_of_week, is_active, bus_line_id) VALUES
('06:00', '06:45', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D003')),
('07:00', '07:45', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D003')),
('08:00', '08:45', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D003')),
('09:00', '09:45', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D003')),
('10:00', '10:45', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D003')),
('12:00', '12:45', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D003')),
('14:00', '14:45', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D003')),
('16:00', '16:45', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D003')),
('18:00', '18:45', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D003')),
('20:00', '20:45', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'D003'));

-- International schedules - Skopje - Belgrade (I001)
INSERT INTO schedules (departure_time, arrival_time, day_of_week, is_active, bus_line_id) VALUES
('07:00', '14:00', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I001')),
('15:00', '22:00', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I001')),
('22:00', '05:00', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I001')),
('07:00', '14:00', 'WEDNESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I001')),
('15:00', '22:00', 'WEDNESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I001')),
('22:00', '05:00', 'WEDNESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I001')),
('07:00', '14:00', 'FRIDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I001')),
('15:00', '22:00', 'FRIDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I001')),
('22:00', '05:00', 'FRIDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I001')),
('08:00', '15:00', 'SATURDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I001')),
('09:00', '16:00', 'SUNDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I001'));

-- International schedules - Skopje - Thessaloniki (I002)
INSERT INTO schedules (departure_time, arrival_time, day_of_week, is_active, bus_line_id) VALUES
('06:00', '10:30', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I002')),
('10:00', '14:30', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I002')),
('15:00', '19:30', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I002')),
('06:00', '10:30', 'TUESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I002')),
('10:00', '14:30', 'TUESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I002')),
('15:00', '19:30', 'TUESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I002')),
('06:00', '10:30', 'WEDNESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I002')),
('10:00', '14:30', 'WEDNESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I002')),
('15:00', '19:30', 'WEDNESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I002')),
('06:00', '10:30', 'THURSDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I002')),
('10:00', '14:30', 'THURSDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I002')),
('15:00', '19:30', 'THURSDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I002')),
('06:00', '10:30', 'FRIDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I002')),
('10:00', '14:30', 'FRIDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I002')),
('15:00', '19:30', 'FRIDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I002')),
('08:00', '12:30', 'SATURDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I002')),
('14:00', '18:30', 'SATURDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I002')),
('10:00', '14:30', 'SUNDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I002')),
('16:00', '20:30', 'SUNDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I002'));

-- International schedules - Skopje - Sofia (I003)
INSERT INTO schedules (departure_time, arrival_time, day_of_week, is_active, bus_line_id) VALUES
('07:30', '12:30', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I003')),
('14:00', '19:00', 'MONDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I003')),
('07:30', '12:30', 'WEDNESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I003')),
('14:00', '19:00', 'WEDNESDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I003')),
('07:30', '12:30', 'FRIDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I003')),
('14:00', '19:00', 'FRIDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I003')),
('09:00', '14:00', 'SATURDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I003')),
('10:00', '15:00', 'SUNDAY', true, (SELECT id FROM bus_lines WHERE line_number = 'I003'));

