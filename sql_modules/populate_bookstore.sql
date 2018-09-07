Use bookstore;

insert into User (username, password, email, firstName, lastName, phone, address, manager)
	values('root', md5('root'), 'root@root.com', 'rooty', 'rooto', '012345678',
		'16 root street, root city', 1);
        
insert into Category(name) values ('Romance');
insert into Category(name) values ('Comedy');

insert into Publisher(name, address, phone) values('amr', '16 ebn souad', '01134213');

insert into Book(ISBN,Title,PublicationDate,Threshold,Price,CopiesNumber,CID,PID)
	values(0, 'hello world', curdate() - interval 5 day, 10, 20.5, 10, 1, 1);
    
insert into Book(ISBN,Title,PublicationDate,Threshold,Price,CopiesNumber,CID,PID)
	values(1, 'bye world', curdate() - interval 2 day, 20, 30.8, 20, 2, 1);
    
insert into Author(name) values ('amr naguib');
insert into Author(name) values ('amr mahfouz');

Insert into AuthoredBy(ISBN, AID) values (0, 1);
Insert into AuthoredBy(ISBN, AID) values (0, 2);
Insert into AuthoredBy(ISBN, AID) values (1, 2);
insert into Publisher(name, address, phone) values ('mico',  '16 ebn souad', '01134213');


insert into Book(ISBN,Title,PublicationDate,Threshold,Price,CopiesNumber,CID,PID)
	values(500001, 'hello world', curdate() - interval 5 day, 10, 20.5, 10, 1, 2);