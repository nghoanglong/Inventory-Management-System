﻿USE Inventory_Management_System;

--- CREATE TABLE

CREATE TABLE ACCOUNT(
	-- Table lưu trữ thông tin account của user
	id_account VARCHAR(100),
	username VARCHAR(100),
	pwd VARCHAR(100),
	account_role INT

	CONSTRAINT PK_ACCOUNT PRIMARY KEY(id_account)
)

CREATE TABLE USERS(
	-- Table lưu trữ thông tin user

	id_user VARCHAR(100),
	id_account VARCHAR(100),
	fullname VARCHAR(100),
	age DATE,
	email VARCHAR(100)

	CONSTRAINT PK_USER PRIMARY KEY(id_user),
	CONSTRAINT FK_USER_ACCOUNT FOREIGN KEY(id_account) REFERENCES ACCOUNT(id_account) ON DELETE CASCADE
)


CREATE TABLE CUSTOMER_INFO(
	-- Table lưu trữ thông tin khách hàng

	id_cus VARCHAR(100),
	name_cus VARCHAR(100),
	phone_cus VARCHAR(100),
	address_cus VARCHAR(100)

	CONSTRAINT PK_CUSTOMER_INFO PRIMARY KEY(id_cus)
)

CREATE TABLE MNG_ORDERS(
	-- Table lưu trữ thông tin đơn hàng

	id_ord VARCHAR(100),
	id_user VARCHAR(100),
	id_cus VARCHAR(100),
	type_ord VARCHAR(100),
	date_ord DATE,
	state_ord INT

	CONSTRAINT PK_MNGORD PRIMARY KEY(id_ord),
	CONSTRAINT FK_MNGORD_USERS FOREIGN KEY(id_user) REFERENCES USERS(id_user) ON DELETE SET NULL,
	CONSTRAINT FK_MNGORD_CUSTOMERINF FOREIGN KEY(id_cus) REFERENCES CUSTOMER_INFO(id_cus) ON DELETE SET NULL
)


CREATE TABLE PRODUCTION(
	-- Table lưu trữ thông tin sản phẩm

	id_prod VARCHAR(100),
	name_prod VARCHAR(100),
	type_prod VARCHAR(100),
	price INT,
	num_exist INT,
	state_prod INT

	CONSTRAINT PK_PRODUCTION PRIMARY KEY(id_prod)
)

CREATE TABLE MNG_REQUESTS(
	-- Table lưu trữ thông tin đơn hàng

	id_ord VARCHAR(100),
	id_prod VARCHAR(100),
	num_ord INT,
	admin_state INT,
	warehouse_mng_state INT,
	date_2state_return DATE

	CONSTRAINT PK_MNG_REQUEST PRIMARY KEY(id_ord, id_prod),
	CONSTRAINT FK_MNGRQ_MNGORD FOREIGN KEY(id_ord) REFERENCES MNG_ORDERS(id_ord) ON DELETE CASCADE,
	CONSTRAINT FK_MNGRQ_PRODUCTION FOREIGN KEY(id_prod) REFERENCES PRODUCTION(id_prod) ON DELETE CASCADE
)


-- Dưới đây là những lệnh hỗ trợ
ALTER TABLE USERS NOCHECK CONSTRAINT ALL;
ALTER TABLE ACCOUNT NOCHECK CONSTRAINT ALL;
ALTER TABLE CUSTOMER_INFO NOCHECK CONSTRAINT ALL;
ALTER TABLE PRODUCTION NOCHECK CONSTRAINT ALL;
ALTER TABLE MNG_ORDERS NOCHECK CONSTRAINT ALL;
ALTER TABLE MNG_REQUEST NOCHECK CONSTRAINT ALL;


DROP TABLE ACCOUNT;
DROP TABLE CUSTOMER_INFO;
DROP TABLE PRODUCTION;
DROP TABLE USERS;
DROP TABLE MNG_ORDERS;
DROP TABLE MNG_REQUESTS;

SET DATEFORMAT dmy;
-- INSERT TABLE DEMO

-- Phần insert demo để có thể sign in vào app
-- ACCOUNT
INSERT INTO ACCOUNT VALUES('ACCOUNT01', 'hoanglong', '123long', 1);
INSERT INTO ACCOUNT VALUES('ACCOUNT02', 'thetiem', '123tiem', 2);
INSERT INTO ACCOUNT VALUES('ACCOUNT03', 'duongtung', '123tung', 3);
INSERT INTO ACCOUNT VALUES('ACCOUNT04', 'xuanson', '123son', 1);

-- USERS
INSERT INTO USERS VALUES('USER01', (SELECT id_account FROM ACCOUNT WHERE id_account='ACCOUNT01'), 'Nguyen Hoang Long', '2020-12-17', 'hoanglong@gmail.com');
INSERT INTO USERS VALUES('USER02', (SELECT id_account FROM ACCOUNT WHERE id_account='ACCOUNT02'), 'Le The Tiem', '2020-12-18', 'thetiem@gmail.com');
INSERT INTO USERS VALUES('USER03', (SELECT id_account FROM ACCOUNT WHERE id_account='ACCOUNT03'), 'Nguyen Duong Tung', '2020-12-19', 'duongtung@gmail.com');
INSERT INTO USERS VALUES('USER04', (SELECT id_account FROM ACCOUNT WHERE id_account='ACCOUNT04'), 'Chu Xuan Son', '2020-12-20', 'xuanson@gmail.com');

-- PRODUCTION
INSERT INTO PRODUCTION VALUES('PRODUCTION01', 'Dell Vostro 1230', 'DELL', 100000000, 50, 1);
INSERT INTO PRODUCTION VALUES('PRODUCTION02', 'Thinkpad 1230', 'Asus', 300000000, 20, 1);
INSERT INTO PRODUCTION VALUES('PRODUCTION03', 'HP 1530"', 'HP', 900000000, 80, 1);
INSERT INTO PRODUCTION VALUES('PRODUCTION04', 'Mac Retina 1230', 'Macbook', 200000000, 20, 1);
INSERT INTO PRODUCTION VALUES('PRODUCTION05', 'Dell 1830', 'DELL', 400000000, 20, 1);


-- Phần SELECT
-- USERS
SELECT *
FROM USERS;

-- ACCOUNT
SELECT *
FROM ACCOUNT;

-- PRODUCTION
SELECT *
FROM PRODUCTION;

-- MNG_ORDERS
SELECT *
FROM MNG_ORDERS;

-- MNG_REQUESTS
SELECT *
FROM MNG_REQUESTS;

-- CUSTOMER_INFO
SELECT *
FROM CUSTOMER_INFO;


-- demo
DELETE FROM ORD_DETAIL
WHERE id_prod IN (SELECT ORD_DETAIL.id_prod
				FROM ORDERS
				INNER JOIN ORD_STATE ON ORDERS.id_ord = ORD_STATE.id_ord
				INNER JOIN ORD_DETAIL ON ORDERS.id_ord = ORD_DETAIL.id_ord
				WHERE ORDERS.type_ord = 'DELETE');
DELETE FROM PRODUCTION
WHERE id_prod IN (SELECT ORD_DETAIL.id_prod
				FROM ORDERS
				INNER JOIN ORD_STATE ON ORDERS.id_ord = ORD_STATE.id_ord
				INNER JOIN ORD_DETAIL ON ORDERS.id_ord = ORD_DETAIL.id_ord
				WHERE ORDERS.type_ord = 'DELETE');

UPDATE PRODUCTION
SET state_prod = 
	( CASE
		 WHEN id_prod IN (SELECT ORD_DETAIL.id_prod
				FROM ORDERS
				INNER JOIN ORD_STATE ON ORDERS.id_ord = ORD_STATE.id_ord
				INNER JOIN ORD_DETAIL ON ORDERS.id_ord = ORD_DETAIL.id_ord
				WHERE ORDERS.type_ord = 'DELETE') THEN 0
		 ELSE 1
	  END
	)

