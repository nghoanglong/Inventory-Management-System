USE Inventory_Management_System;

--- CREATE TABLE

CREATE TABLE USERS(
	-- Table lưu trữ thông tin user

	id_user VARCHAR(100),
	fullname VARCHAR(100),
	dateOfBirth DATE,
	email VARCHAR(100)

	CONSTRAINT PK_USERS PRIMARY KEY(id_user)
)
// baeba0a7209c391842bb02d1a722c2c8
CREATE TABLE ACCOUNT(
	-- Table lưu trữ thông tin account của user
	id_account VARCHAR(100),
	id_user VARCHAR(100),
	username VARCHAR(100),
	pwd VARCHAR(100),
	account_role INT

	CONSTRAINT PK_ACCOUNT PRIMARY KEY(id_account),
	CONSTRAINT FK_ACCOUNT_USERS FOREIGN KEY(id_user) REFERENCES USERS(id_user) ON DELETE CASCADE
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

	CONSTRAINT PK_MNG_ORDERS PRIMARY KEY(id_ord),
	CONSTRAINT FK_MNGORD_USERS FOREIGN KEY(id_user) REFERENCES USERS(id_user) ON DELETE SET NULL,
	CONSTRAINT FK_MNGORD_CUSTOMERINF FOREIGN KEY(id_cus) REFERENCES CUSTOMER_INFO(id_cus) ON DELETE SET NULL
)

CREATE TABLE DELETE_ORD(
	id_del_ord VARCHAR(100),
	id_ord VARCHAR(100),
	admin_state INT,
	warehouse_state INT,
	date_2state_return DATE

	CONSTRAINT PK_DELETE_ORD PRIMARY KEY(id_del_ord),
	CONSTRAINT FK_DELORD_MNGORD FOREIGN KEY(id_ord) REFERENCES MNG_ORDERS(id_ord) ON DELETE CASCADE
)

CREATE TABLE EXPORT_ORD(
	id_export_ord VARCHAR(100),
	id_ord VARCHAR(100),
	admin_state INT,
	warehouse_state INT,
	date_2state_return DATE

	CONSTRAINT PK_EXPORT_ORD PRIMARY KEY(id_export_ord),
	CONSTRAINT FK_EXPRTORD_MNGORD FOREIGN KEY(id_ord) REFERENCES MNG_ORDERS(id_ord) ON DELETE CASCADE
)

CREATE TABLE ADD_ORD(
	id_add_ord VARCHAR(100),
	id_ord VARCHAR(100),
	admin_state INT,
	warehouse_state INT,
	date_2state_return DATE

	CONSTRAINT PK_ADD_ORD PRIMARY KEY(id_add_ord),
	CONSTRAINT FK_ADDORD_MNGORD FOREIGN KEY(id_ord) REFERENCES MNG_ORDERS(id_ord) ON DELETE CASCADE
)

CREATE TABLE IMPORT_ORD(
	id_import_ord VARCHAR(100),
	id_ord VARCHAR(100),
	admin_state INT,
	warehouse_state INT,
	date_2state_return DATE

	CONSTRAINT PK_IMPORT_ORD PRIMARY KEY(id_import_ord),
	CONSTRAINT FK_IMPRTORD_MNGORD FOREIGN KEY(id_ord) REFERENCES MNG_ORDERS(id_ord) ON DELETE CASCADE
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
CREATE TABLE DETAIL_ORD(
	id_ord VARCHAR(100),
	id_prod VARCHAR(100),
	num_ord INT

	CONSTRAINT PK_DETAIL_ORD PRIMARY KEY(id_ord, id_prod),
	CONSTRAINT FK_DETAILORD_ORD FOREIGN KEY(id_ord) REFERENCES MNG_ORDERS(id_ord) ON DELETE CASCADE,
	CONSTRAINT FK_DETAILORD_PROD FOREIGN KEY(id_prod) REFERENCES PRODUCTION(id_prod)
)


-- Dưới đây là những lệnh hỗ trợ
ALTER TABLE USERS NOCHECK CONSTRAINT ALL;
ALTER TABLE ACCOUNT NOCHECK CONSTRAINT ALL;
ALTER TABLE CUSTOMER_INFO NOCHECK CONSTRAINT ALL;
ALTER TABLE PRODUCTION NOCHECK CONSTRAINT ALL;
ALTER TABLE DETAIL_ORD NOCHECK CONSTRAINT ALL;
ALTER TABLE MNG_ORDERS NOCHECK CONSTRAINT ALL;
ALTER TABLE DELETE_ORD NOCHECK CONSTRAINT ALL;
ALTER TABLE EXPORT_ORD NOCHECK CONSTRAINT ALL;
ALTER TABLE ADD_ORD NOCHECK CONSTRAINT ALL;
ALTER TABLE IMPORT_ORD NOCHECK CONSTRAINT ALL;


DROP TABLE ACCOUNT;
DROP TABLE CUSTOMER_INFO;
DROP TABLE PRODUCTION;
DROP TABLE USERS;
DROP TABLE MNG_ORDERS;
DROP TABLE DELETE_ORD;
DROP TABLE EXPORT_ORD;
DROP TABLE ADD_ORD;
DROP TABLE IMPORT_ORD;
DROP TABLE DETAIL_ORD;



SET DATEFORMAT dmy;
-- INSERT TABLE DEMO

-- Phần insert demo để có thể sign in vào app
-- USERS
INSERT INTO USERS VALUES('USER01', 'Nguyen Hoang Long', '2020-12-17', 'hoanglong@gmail.com');
INSERT INTO USERS VALUES('USER02', 'Le The Tiem', '2020-12-18', 'thetiem@gmail.com');
INSERT INTO USERS VALUES('USER03', 'Nguyen Duong Tung', '2020-12-19', 'duongtung@gmail.com');
INSERT INTO USERS VALUES('USER04', 'Chu Xuan Son', '2020-12-20', 'xuanson@gmail.com');

-- ACCOUNT
INSERT INTO ACCOUNT VALUES('ACCOUNT01', (SELECT id_user FROM USERS WHERE id_user = 'USER01'), 'hoanglong', '123long', 1);
INSERT INTO ACCOUNT VALUES('ACCOUNT02', (SELECT id_user FROM USERS WHERE id_user='USER02'), 'thetiem', '123tiem', 2);
INSERT INTO ACCOUNT VALUES('ACCOUNT03', (SELECT id_user FROM USERS WHERE id_user='USER03'), 'duongtung', '123tung', 3);
INSERT INTO ACCOUNT VALUES('ACCOUNT04', (SELECT id_user FROM USERS WHERE id_user='USER04'), 'xuanson', '123son', 1);

-- PRODUCTION
INSERT INTO PRODUCTION VALUES('PRODUCTION01', 'Dell Vostro 1230', 'DELL', 1000000, 50, 1);
INSERT INTO PRODUCTION VALUES('PRODUCTION02', 'Thinkpad 1230', 'Asus', 3000000, 20, 1);
INSERT INTO PRODUCTION VALUES('PRODUCTION03', 'HP 1530', 'HP', 9000000, 80, 1);
INSERT INTO PRODUCTION VALUES('PRODUCTION04', 'Mac Retina 1230', 'Macbook', 20000000, 20, 1);
INSERT INTO PRODUCTION VALUES('PRODUCTION05', 'Dell 1830', 'DELL', 4000000, 20, 1);

INSERT INTO CUSTOMER_INFO VALUES('CUSTOMER01', 'Yen Nhi', '0966958009', 'linh trung, thu duc');
INSERT INTO CUSTOMER_INFO VALUES('CUSTOMER02', 'Duy Nhat', '0966958005', 'tam phu, thu duc');
INSERT INTO CUSTOMER_INFO VALUES('CUSTOMER03', 'Hoang Lan', '09438472323', 'tam binh, thu duc');
INSERT INTO CUSTOMER_INFO VALUES('CUSTOMER04', 'Lan Anh', '0966958002', 'binh chieu, thu duc');
INSERT INTO CUSTOMER_INFO VALUES('CUSTOMER05', 'Minh Hieu', '0966958007', 'binh tho, thu duc');

INSERT INTO MNG_ORDERS VALUES('MNG_ORDERS01', (SELECT id_user FROM USERS WHERE id_user = 'USER01'), (SELECT id_cus FROM CUSTOMER_INFO WHERE id_cus = 'CUSTOMER01'), 'EXPORT', '2021-06-25', 2);
INSERT INTO MNG_ORDERS VALUES('MNG_ORDERS02', (SELECT id_user FROM USERS WHERE id_user = 'USERS-499472378'), (SELECT id_cus FROM CUSTOMER_INFO WHERE id_cus = 'CUSTOMER03'), 'EXPORT', '2021-05-25', 2);
INSERT INTO MNG_ORDERS VALUES('MNG_ORDERS03', (SELECT id_user FROM USERS WHERE id_user = 'USERS1452363000'), (SELECT id_cus FROM CUSTOMER_INFO WHERE id_cus = 'CUSTOMER02'), 'EXPORT', '2021-04-25', 2);
INSERT INTO MNG_ORDERS VALUES('MNG_ORDERS04', (SELECT id_user FROM USERS WHERE id_user = 'USER01'), (SELECT id_cus FROM CUSTOMER_INFO WHERE id_cus = 'CUSTOMER04'), 'EXPORT', '2021-02-25', 2);
INSERT INTO MNG_ORDERS VALUES('MNG_ORDERS05', (SELECT id_user FROM USERS WHERE id_user = 'USERS1452363000'), (SELECT id_cus FROM CUSTOMER_INFO WHERE id_cus = 'CUSTOMER05'), 'EXPORT', '2021-01-25', 2);

INSERT INTO DETAIL_ORD VALUES((SELECT id_ord FROM MNG_ORDERS WHERE id_ord = 'MNG_ORDERS01'), (SELECT id_prod FROM PRODUCTION WHERE id_prod = 'PRODUCTION02'), 3)
INSERT INTO DETAIL_ORD VALUES((SELECT id_ord FROM MNG_ORDERS WHERE id_ord = 'MNG_ORDERS01'), (SELECT id_prod FROM PRODUCTION WHERE id_prod = 'PRODUCTION03'), 2)
INSERT INTO DETAIL_ORD VALUES((SELECT id_ord FROM MNG_ORDERS WHERE id_ord = 'MNG_ORDERS02'), (SELECT id_prod FROM PRODUCTION WHERE id_prod = 'PRODUCTION04'), 2)
INSERT INTO DETAIL_ORD VALUES((SELECT id_ord FROM MNG_ORDERS WHERE id_ord = 'MNG_ORDERS03'), (SELECT id_prod FROM PRODUCTION WHERE id_prod = 'PRODUCTION01'), 5)
INSERT INTO DETAIL_ORD VALUES((SELECT id_ord FROM MNG_ORDERS WHERE id_ord = 'MNG_ORDERS04'), (SELECT id_prod FROM PRODUCTION WHERE id_prod = 'PRODUCTION05'), 4)
INSERT INTO DETAIL_ORD VALUES((SELECT id_ord FROM MNG_ORDERS WHERE id_ord = 'MNG_ORDERS05'), (SELECT id_prod FROM PRODUCTION WHERE id_prod = 'PRODUCTION01'), 8)

INSERT INTO EXPORT_ORD VALUES('EXPORT_ORD01', (SELECT id_ord FROM MNG_ORDERS WHERE id_ord = 'MNG_ORDERS01'), 1, 1, '2021-10-05');
INSERT INTO EXPORT_ORD VALUES('EXPORT_ORD02', (SELECT id_ord FROM MNG_ORDERS WHERE id_ord = 'MNG_ORDERS02'), 1, 1, '2021-06-24');
INSERT INTO EXPORT_ORD VALUES('EXPORT_ORD03', (SELECT id_ord FROM MNG_ORDERS WHERE id_ord = 'MNG_ORDERS03'), 1, 1, '2021-12-17');
INSERT INTO EXPORT_ORD VALUES('EXPORT_ORD04', (SELECT id_ord FROM MNG_ORDERS WHERE id_ord = 'MNG_ORDERS04'), 1, 1, '2021-12-02');
INSERT INTO EXPORT_ORD VALUES('EXPORT_ORD05', (SELECT id_ord FROM MNG_ORDERS WHERE id_ord = 'MNG_ORDERS05'), 1, 1, '2021-12-28');

-------------------------------------------------------------------------- TRIGGER
-- XÓA
CREATE TRIGGER trg_insert_delord ON DELETE_ORD
AFTER INSERT
AS
BEGIN
	UPDATE PRODUCTION
	SET state_prod = 0
	FROM PRODUCTION
	INNER JOIN DETAIL_ORD ON PRODUCTION.id_prod = DETAIL_ORD.id_prod
	INNER JOIN inserted ON DETAIL_ORD.id_ord = inserted.id_ord
END

CREATE TRIGGER trg_update_delord ON DELETE_ORD
AFTER UPDATE
AS
IF UPDATE(warehouse_state)
	BEGIN
		UPDATE PRODUCTION
		set state_prod = (CASE
								WHEN PRODUCTION.id_prod IN (SELECT DETAIL_ORD.id_prod
															FROM DETAIL_ORD 
															INNER JOIN inserted ON inserted.id_ord = DETAIL_ORD.id_ord
															WHERE inserted.warehouse_state = 1) THEN 0
								ELSE 1
						  END)
		FROM PRODUCTION 
		INNER JOIN DETAIL_ORD ON PRODUCTION.id_prod = DETAIL_ORD.id_prod
		INNER JOIN inserted ON DETAIL_ORD.id_ord = inserted.id_ord
	END 
IF UPDATE(date_2state_return)
	BEGIN
		UPDATE MNG_ORDERS
		SET state_ord = (CASE
								WHEN inserted.admin_state = 1 AND inserted.warehouse_state = 1 THEN 1
								ELSE 0
						 END)
		FROM MNG_ORDERS
		INNER JOIN inserted ON inserted.id_ord = MNG_ORDERS.id_ord
	END


-- XUẤT
CREATE TRIGGER trg_insert_exportord ON EXPORT_ORD
AFTER INSERT
AS
BEGIN
	UPDATE PRODUCTION
	SET num_exist = num_exist - (CASE
										WHEN PRODUCTION.id_prod IN(SELECT DETAIL_ORD.id_prod
																   FROM inserted
																   INNER JOIN DETAIL_ORD ON inserted.id_ord = DETAIL_ORD.id_ord) THEN (SELECT DETAIL_ORD.num_ord
																																	   FROM DETAIL_ORD
																																	   INNER JOIN inserted on DETAIL_ORD.id_ord = inserted.id_ord
																																	   WHERE DETAIL_ORD.id_prod = PRODUCTION.id_prod)
										ELSE 0
								END)
	FROM PRODUCTION
	INNER JOIN DETAIL_ORD ON PRODUCTION.id_prod = DETAIL_ORD.id_prod
	INNER JOIN inserted ON inserted.id_ord = DETAIL_ORD.id_ord
END

CREATE TRIGGER trg_update_exportord ON EXPORT_ORD
AFTER UPDATE
AS
IF UPDATE(warehouse_state)
	BEGIN
		UPDATE PRODUCTION
		SET num_exist = num_exist - (CASE
											WHEN PRODUCTION.id_prod IN (SELECT DETAIL_ORD.id_prod
																		FROM DETAIL_ORD 
																		INNER JOIN inserted ON inserted.id_ord = DETAIL_ORD.id_ord
																		WHERE inserted.warehouse_state = 1) THEN 0
											ELSE (SELECT DETAIL_ORD.num_ord * -1
												  FROM DETAIL_ORD
												  INNER JOIN inserted on DETAIL_ORD.id_ord = inserted.id_ord
												  WHERE DETAIL_ORD.id_prod = PRODUCTION.id_prod)
									 END)
		FROM PRODUCTION 
		INNER JOIN DETAIL_ORD ON PRODUCTION.id_prod = DETAIL_ORD.id_prod
		INNER JOIN inserted ON DETAIL_ORD.id_ord = inserted.id_ord
	END
IF UPDATE(date_2state_return)
	BEGIN
		UPDATE MNG_ORDERS
		SET state_ord = (CASE
								WHEN inserted.admin_state = 1 AND inserted.warehouse_state = 1 THEN 1
								ELSE 0
						 END)
		FROM MNG_ORDERS
		INNER JOIN inserted ON inserted.id_ord = MNG_ORDERS.id_ord
	END

-- ADD
CREATE TRIGGER trg_update_addord ON ADD_ORD
AFTER UPDATE
AS
IF UPDATE(warehouse_state)
	BEGIN
		UPDATE PRODUCTION
		SET state_prod = (CASE 
								WHEN PRODUCTION.id_prod IN (SELECT DETAIL_ORD.id_prod
														    FROM DETAIL_ORD
															INNER JOIN inserted ON DETAIL_ORD.id_ord = inserted.id_ord
															WHERE inserted.warehouse_state = 1) THEN 1
								ELSE 0
						  END)
		FROM PRODUCTION
		INNER JOIN DETAIL_ORD ON PRODUCTION.id_prod = DETAIL_ORD.id_prod
		INNER JOIN inserted ON DETAIL_ORD.id_ord = inserted.id_ord
	END
IF UPDATE(date_2state_return)
	BEGIN
		UPDATE MNG_ORDERS
		SET state_ord = (CASE
								WHEN inserted.admin_state = 1 AND inserted.warehouse_state = 1 THEN 1
								ELSE 0
						 END)
		FROM MNG_ORDERS
		INNER JOIN inserted ON inserted.id_ord = MNG_ORDERS.id_ord
	END

-- Nhập
CREATE TRIGGER trg_update_importord ON IMPORT_ORD
AFTER UPDATE
AS
IF UPDATE(warehouse_state)
	BEGIN
		UPDATE PRODUCTION
		SET num_exist = num_exist + (CASE
										WHEN PRODUCTION.id_prod IN (SELECT DETAIL_ORD.id_prod
																    FROM inserted
																    INNER JOIN DETAIL_ORD ON inserted.id_ord = DETAIL_ORD.id_ord
																	WHERE inserted.warehouse_state = 1) THEN (SELECT DETAIL_ORD.num_ord
																											  FROM DETAIL_ORD
																											  INNER JOIN inserted on DETAIL_ORD.id_ord = inserted.id_ord
																											  WHERE DETAIL_ORD.id_prod = PRODUCTION.id_prod)
										ELSE 0
									 END)
		FROM PRODUCTION
		INNER JOIN DETAIL_ORD ON PRODUCTION.id_prod = DETAIL_ORD.id_prod
		INNER JOIN inserted ON DETAIL_ORD.id_ord = inserted.id_ord
	END
IF UPDATE(date_2state_return)
	BEGIN
		UPDATE MNG_ORDERS
		SET state_ord = (CASE
								WHEN inserted.admin_state = 1 AND inserted.warehouse_state = 1 THEN 1
								ELSE 0
						 END)
		FROM MNG_ORDERS
		INNER JOIN inserted ON inserted.id_ord = MNG_ORDERS.id_ord
	END