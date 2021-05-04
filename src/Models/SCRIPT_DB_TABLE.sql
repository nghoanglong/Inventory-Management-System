CREATE DATABASE Inventory_Management_System;

USE Inventory_Management_System;

--- CREATE TABLE

CREATE TABLE USERS(
                      id_user INT IDENTITY(1, 1) PRIMARY KEY,
                      username VARCHAR(100),
                      pwd VARCHAR(100),
                      age DATE,
                      role_user INT,
                      email VARCHAR(100)
)

CREATE TABLE QLSP(
                     id_sp INT IDENTITY(1, 1) PRIMARY KEY,
                     ten_sp VARCHAR(100),
                     loai_sp VARCHAR(100),
                     gia VARCHAR(100),
                     num_sp INT
)

-- DROP TABLE

DROP TABLE USERS
DROP TABLE QLSP

    -- INSERT TABLE DEMO
-- USERS
    INSERT INTO USERS VALUES('HOANG LONG', '123long', '2020-12-17', 1, 'hoanglong@gmail.com');

-- QLSP
INSERT INTO QLSP VALUES('DELL Vostro', 'laptop', 10000000, 50);

-- SELECT DEMO

SELECT *
FROM USERS;

SELECT *
FROM QLSP;