create database beata_order;

use beata_order;
create table `order`(
        id int unsigned auto_increment,
        name varchar(50),
        PRIMARY KEY (id)
) engine=innodb DEFAULT CHARSET=utf8mb4;