create table employee (
	id int not null auto_increment, 
	empname varchar(50), 
	empage int, 
	context varchar(255),
	deptid int,
	primary key (id),
	foreign key (deptid) references department(id)
)engine=innodb default charset=gb2312;