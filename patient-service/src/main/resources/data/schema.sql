CREATE DATABASE IF NOT EXISTS patients_db
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_0900_ai_ci;

USE patients_db;

CREATE TABLE IF NOT EXISTS patients (
    id BIGINT NOT NULL AUTO_INCREMENT,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    birth_date DATE NOT NULL,
    sex VARCHAR(10) NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(50),
    PRIMARY KEY (id)
);