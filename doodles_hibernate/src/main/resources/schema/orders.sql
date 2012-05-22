create table orders (
	id int unsigned not null auto_increment,
	customer_name varchar(30) default '', 
	address varchar(100) default '', 
	primary key ( id )
) engine=InnoDB default charset=gb2312;