CREATE DATABASE if NOT EXISTS population;

USE population;

CREATE TABLE if not exists users(
id INT(10) AUTO_INCREMENT PRIMARY KEY ,
login VARCHAR(20),
`password` VARCHAR(40),
`type` VARCHAR(6)
);

INSERT INTO population.users (login, `password`, `type`) 
VALUES ('admin', '21232f297a57a5a743894a0e4a801fc3', 'admin');

CREATE USER 'admin' IDENTIFIED BY '21232f297a57a5a743894a0e4a801fc3';
GRANT ALL PRIVILEGES ON *.* TO 'admin' WITH GRANT OPTION;
FLUSH PRIVILEGES;