-- drop database if exits 
DROP DATABASE IF EXISTS mybnb; 

-- create database 
CREATE DATABASE mybnb; 

-- use database 
USE mybnb;

-- create table acc_occupancy 
CREATE TABLE acc_occupancy (
    acc_id VARCHAR(10), 
    vacancy INT, 
    CONSTRAINT pk_acc_id PRIMARY KEY (acc_id)
);

-- create table reservations
CREATE TABLE reservations (
    resv_id VARCHAR(8),
    name VARCHAR(128), 
    email VARCHAR(128), 
    acc_id VARCHAR(10), -- foreign key 
    arrival_date DATE, 
    duration INT, 
    CONSTRAINT pk_resv_id PRIMARY KEY (resv_id),
    CONSTRAINT fk_acc_id 
        FOREIGN KEY (acc_id)
        REFERENCES acc_occupancy(acc_id)
);