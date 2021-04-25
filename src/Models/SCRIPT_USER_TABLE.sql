CREATE DATABASE Inventory_Management_System;
USE Inventory_Management_System;

--- CREATE TABLE

CREATE TABLE USERS(
	id_user INT IDENTITY(1, 1),
	username VARCHAR(100),
	pwd VARCHAR(100),
	age DATE,
	role_user INT,
	email VARCHAR(100)
)

-- DROP TABLE

DROP TABLE USERS

-- INSERT TABLE DEMO

INSERT INTO USERS VALUES('HOANG LONG', '123long', '2020-12-17', 1, 'hoanglong@gmail.com');

-- SELECT DEMO

SELECT *
FROM USERS;