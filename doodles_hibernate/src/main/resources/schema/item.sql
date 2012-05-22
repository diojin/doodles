create table item (
	id int unsigned not null auto_increment,
	name varchar(30) default '', 
	description varchar(100) default '',
	primary key (id)
) engine=InnoDB default charset=gb2312;