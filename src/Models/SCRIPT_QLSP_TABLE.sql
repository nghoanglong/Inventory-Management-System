use Inventory_Management_System

-- CREATE TABLE

CREATE TABLE QLSP(
	id_sp INT IDENTITY(1, 1),
	loai_sp VARCHAR(100),
	ten_sp VARCHAR(100),
	num_sp INT,
	img IMAGE
)

-- DROP TABLE

drop table QLSP

-- DEMO SELECT TABLE

select *
from QLSP;