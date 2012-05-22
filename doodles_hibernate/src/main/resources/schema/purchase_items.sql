create table purchase_items (
	id int unsigned not null auto_increment,
	order_id int unsigned not null,
	item_id int unsigned not null,
	price double, 
	quantity int,
	purchase_date datetime,
	primary key (id), 
	foreign key (order_id) references orders(id),
	foreign key (item_id) references item(id)
) engine=innodb default charset=gb2312;