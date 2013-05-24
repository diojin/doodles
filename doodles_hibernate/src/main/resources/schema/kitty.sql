create table cats(
	id int not null auto_increment,
	subclass char(1),
	weight float(6,2),
	birthdate date,
	color varchar(20),
	sex char(1),
	litter_id int, 
	mother_id int,
	name varchar(42),
	primary key (id),
	foreign key (mother_id) references cats(id)
)engine=innodb default charset=gb2312;