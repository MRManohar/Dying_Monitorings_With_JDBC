create table cus_details(
register_date VARCHAR2(10) not null,--1
cus_id VARCHAR2(15) not null,--2
cus_name VARCHAR2(40) not null,--3
user_name VARCHAR2(40) not null,--4
email VARCHAR2(50) not null,--5
mobile_number VARCHAR2(15) not null,--6
password VARCHAR2(20) not null,--7
PRIMARY KEY (cus_id)
);

create table orders(
order_date VARCHAR2(10) not null,--1
order_id NUMBER(10) NOT NULL,--2
cust_id VARCHAR2(15) not null,--3
varpulu NUMBER(10) not null,--4
sapuri NUMBER(10) not null,--5
dupin NUMBER(10) not null,--6
PRIMARY KEY (order_id),
FOREIGN KEY (cust_id) REFERENCES cus_details(cus_id)
);

create sequence order_id minvalue 1 start with 1 cache 10;

create table cus_ids(
order_date varchar2(10) not null,
order_id NUMBER(10) not null,
varpulu NUMBER(10) not null,
sapuri NUMBER(10) not null,
dupin NUMBER(10) not null,
FOREIGN KEY (order_id) REFERENCES orders(order_id)
);

create table workers (
register_date varchar2(10) not null,--1
worker_id VARCHAR2(15) not null,--2
worker_name VARCHAR(40) not null,--3
worker_category varchar2(40) not null,--4
mobile_number VARCHAR2(15) not null,--5
address varchar2(100) not null,--6
PRIMARY KEY (worker_id)
);

create table cus_ids_account(
bill_date varchar2(10) not null,
cus_transaction_ID number(10) not null PRIMARY KEY,
description varchar2(50) not null,
debit number(10),
credit number(10),
balance number(10)
);

create sequence cus_transaction_ID minvalue 1 start with 1 INCREMENT BY 1 cache 10;

create table worker_ids_account(
dates varchar2(10) not null,
worker_transaction_ID number(10) not null PRIMARY KEY,
description varchar2(50) not null,
debit number(10),
credit number(10),
balance number(10)
);

create sequence worker_transaction_ID minvalue 1 start with 1 INCREMENT BY 1 cache 10;

create table firm_account(
bill_date varchar2(10) not null,
transaction_ID number(10) not null PRIMARY KEY,
description varchar2(50) not null,
reference_transaction_ID number(10) not null,
debit number(10),
credit number(10),
balance number(10)
);

create sequence transaction_ID minvalue 1 start with 1 INCREMENT BY 1 cache 10;