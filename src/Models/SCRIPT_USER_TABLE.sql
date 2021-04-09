CREATE DATABASE Inventory_Management_System;
USE Inventory_Management_System;

--- CREATE TABLE

CREATE TABLE USERS(
	username VARCHAR(100),
	pwd VARCHAR(100),
	age DATE,
	role_user INT,
	email VARCHAR(100)
)

-- DROP TABLE

DROP TABLE EMPLOYEE

-- INSERT TABLE DEMO

INSERT INTO USERS VALUES('HOANG LONG', '123long', '2020-12-17', 2, 'hoanglong@gmail.com');
INSERT INTO USERS VALUES('THE TIEM', '123tiem', '2020-12-16', 1, 'thetiem@gmail.com');
INSERT INTO USERS VALUES('XUAN SON', '123son', '2020-12-10', 3, 'xuanson@gmail.com');
INSERT INTO USERS VALUES('DUONG TUNG', '123tung', '2020-12-9', 2, 'duongtung@gmail.com');

-- SELECT DEMO

SELECT *
FROM USERS;