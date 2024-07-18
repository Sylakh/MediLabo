CREATE DATABASE back;
USE back;

Create table customer( 
    id int PRIMARY KEY AUTO_INCREMENT,
    lastname varchar(63) NOT NULL,
    firstname varchar(63) NOT NULL,
    birthday date NOT NULL,
    gender varchar(1) NOT NULL,
    address varchar(63),
    phone varchar(12)
);

commit;

INSERT INTO customer(lastname, firstname, birthday, gender, address, phone) VALUES 
("TestNone", "Test", '1966-12-31', "F", "1 BrookSide St", "100-222-3333"),
("TestBorderline", "Test", '1945-06-24', "M", "2 High St", "200-333-4444"),
("TestInDanger", "Test", '2004-06-18', "M", "3 Club Rd", "300-444-5555"),
("TestEarlyOnSet", "Test", '2002-06-28', "F", "4 Valley Dr", "400-555-6666");

commit;