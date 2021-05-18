USE Inventory_Management_System;

--- CREATE TABLE

CREATE TABLE USERS(
	-- Table lưu trữ thông tin user

	id_user VARCHAR(100),
	fullname VARCHAR(100),
	username VARCHAR(100),
	pwd VARCHAR(100),
	age DATE,
	role_user INT,
	email VARCHAR(100)

	CONSTRAINT PK_USER PRIMARY KEY(id_user)
)


CREATE TABLE QLSP(
	-- Table lưu trữ thông tin sản phẩm

	id_sp VARCHAR(100),
	ten_sp VARCHAR(100),
	loai_sp VARCHAR(100),
	gia INT,
	num_sp INT

	CONSTRAINT PK_QLSP PRIMARY KEY(id_sp)
)


CREATE TABLE TTKH(
	-- Table lưu trữ thông tin khách hàng

	id_kh VARCHAR(100),
	name_kh VARCHAR(100),
	phone_kh VARCHAR(100),
	address_kh VARCHAR(100)

	CONSTRAINT PK_TTKH PRIMARY KEY(id_kh)
)


CREATE TABLE QLDH(
	-- Table lưu trữ thông tin đơn hàng

	id_dh VARCHAR(100),
	id_user VARCHAR(100),
	id_kh VARCHAR(100),
	loai_dh VARCHAR(20),
	date_dh DATE

	CONSTRAINT PK_QLDH PRIMARY KEY(id_dh),
	CONSTRAINT FK_USER FOREIGN KEY(id_user) REFERENCES USERS(id_user) ON DELETE SET NULL,
	CONSTRAINT FK_TTKH FOREIGN KEY(id_kh) REFERENCES TTKH(id_kh) ON DELETE SET NULL
)



CREATE TABLE CTDH(
	-- Table lưu trữ thông tin của một đơn hàng nhất định
	-- Ví dụ đơn hàng nhập có id_dh = 001 có bao nhiêu yêu cầu nhập máy DELL, bao nhiêu yêu cầu nhập máy MAC

	id_ctdh VARCHAR(100),
	id_sp VARCHAR(100),
	sl_yc INT

	CONSTRAINT PK_CTDH PRIMARY KEY(id_ctdh, id_sp),
	CONSTRAINT FK_QLSP FOREIGN KEY(id_sp) REFERENCES QLSP(id_sp) ON DELETE CASCADE,
	CONSTRAINT FK_CTDH_QLDH FOREIGN KEY(id_ctdh) REFERENCES QLDH(id_dh) ON DELETE CASCADE
)


CREATE TABLE TRANGTHAI_DH(
	-- Table lưu trữ thông tin trạng thái của một đơn hàng

	id_ttdh VARCHAR(100),
	id_dh VARCHAR(100),
	admin_state INT,
	qlkho_state INT,
	date_return_2state DATE

	CONSTRAINT PK_TTDH PRIMARY KEY(id_ttdh, id_dh),
	CONSTRAINT FK_TRANGTHAIDH_QLDH FOREIGN KEY(id_dh) REFERENCES QLDH(id_dh) ON DELETE CASCADE
)

-- Dưới đây là những lệnh hỗ trợ insert các table mà ko cần quan tâm đến các constraint
-- vui lòng ko chạy những lệnh này
ALTER TABLE USERS NOCHECK CONSTRAINT ALL;
ALTER TABLE QLSP NOCHECK CONSTRAINT ALL;
ALTER TABLE QLDH NOCHECK CONSTRAINT ALL;
ALTER TABLE CTDH NOCHECK CONSTRAINT ALL;
ALTER TABLE TRANGTHAI_DH NOCHECK CONSTRAINT ALL;
ALTER TABLE TT_KH NOCHECK CONSTRAINT ALL;

-- INSERT TABLE DEMO

-- Phần insert demo để có thể sign in vào app
-- USERS
INSERT INTO USERS VALUES('USER01', 'Nguyen Hoang Long' ,'hoanglongsairoi', '123long', '2020-12-17', 1, 'hoanglong@gmail.com');
INSERT INTO USERS VALUES('USER02', 'Le The Tiem', 'thetiem', '123tiem', '2020-12-18', 2, 'thetiem@gmail.com');
INSERT INTO USERS VALUES('USER03', 'Nguyen Duong Tung', 'duongtung', '123tung', '2020-12-19', 3, 'duongtung@gmail.com');
INSERT INTO USERS VALUES('USER04', 'Chu Xuan Son', 'xuanson', '123son', '2020-12-20', 1, 'xuanson@gmail.com');

-- demo insert QLDH
INSERT INTO QLDH(id_dh, loai_dh) VALUES('QLDH101', 'Sell');
INSERT INTO CTDH VALUES((SELECT id_dh FROM QLDH WHERE id_dh = 'QLDH101'), (SELECT id_sp FROM QLSP WHERE id_sp = 'QLSP-1078314005'), 50);

-- SELECT DEMO
SELECT *
FROM USERS;


-- DROP TABLE: Phần bổ sung
-- Để drop được các table trên cần drop các constraint dưới theo thứ tự

-- DROP CONSTRAINT QLSP
-- (1)
ALTER TABLE QLSP
DROP CONSTRAINT PK_QLSP

-- DROP CONSTRAINT TRANGTHAI_DH
-- (2)
ALTER TABLE TRANGTHAI_DH
DROP CONSTRAINT FK_TTDH_QLDH

-- (3)
ALTER TABLE TRANGTHAI_DH
DROP CONSTRAINT PK_TTDH

-- DROP CONSTRAINT CTDH
-- (4)
ALTER TABLE CTDH
DROP CONSTRAINT FK_QLSP

-- (5)
ALTER TABLE CTDH
DROP CONSTRAINT FK_QLDH;

-- (6)
ALTER TABLE CTDH
DROP CONSTRAINT PK_CTDH;

-- DROP CONSTRAINT QLDH
-- (7)
ALTER TABLE QLDH
DROP CONSTRAINT FK_TTKH

-- (8)
ALTER TABLE QLDH
DROP CONSTRAINT FK_USER

-- (9)
ALTER TABLE QLDH
DROP CONSTRAINT PK_QLDH;


-- DROP CONSTRAINT TTKH
-- (10)
ALTER TABLE TT_KH
DROP CONSTRAINT PK_TTKH

-- DROP CONSTRAINT USERS
-- (11)
ALTER TABLE USERS
DROP CONSTRAINT PK_USER;

-- Thực hiện drop các table
-- (12)
DROP TABLE USERS
DROP TABLE QLSP
DROP TABLE QLDH
DROP TABLE CTDH
DROP TABLE TT_KH
DROP TABLE TRANGTHAI_DH


