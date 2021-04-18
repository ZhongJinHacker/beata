create database beata_stock;

use beata_stock;

create table `stock`(
    id int unsigned auto_increment,
    name varchar(50),
    PRIMARY KEY (id)
) engine=innodb DEFAULT CHARSET=utf8mb4;