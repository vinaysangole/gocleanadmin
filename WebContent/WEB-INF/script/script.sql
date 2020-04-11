drop database if exists `gocleandb`;
Create Database `gocleandb`;
Use `gocleandb`;

Drop table if exists `role_lookup_table`;
Create Table `role_lookup_table`(
  `roleId` int(10) primary key auto_increment,
  `roleName` text,
  `description` text,
  `active` tinyint(1) default NULL
);

insert into `role_lookup_table`(`roleName`,`description`,`active`) values
('admin','Admin','1'),
('user','User','1');


Drop table if exists `user_table`;
Create Table `user_table`(
   `userId` int(10) primary key auto_increment,
   `loginId` text,
   `firstName` text,
   `middleName` text,
   `lastName` text,
   `password` text,
   `confirmPassword` text,
   `roleId` int(10),
   `emailId` text,
   `active` tinyint(1) default NULL,
   `isUpdated` tinyint(1) default NULL,
   `updatedDate` datetime default NULL,
   `updatedBy` varchar(50) default NULL,

   foreign key (`roleId`) references `role_lookup_table`(`roleId`)
);

insert into `user_table`(`loginId`,`firstName`,`middleName`,`lastName`,`password`,`confirmPassword`,`roleId`,`emailId`,`active`,`isUpdated`,`updatedDate`,`updatedBy`)values
('admin','admin','admin','admin','admin','admin',1,'nishimauje@gmail.com','1',NULL,'2020:03:12 08:00:00',NULL),
('user','user','user','user','user','user',2,'nikhilmauje@gmail.com','1',NULL,NULL,NULL);


Drop table if exists `floor_lookup_table`;
Create Table `floor_lookup_table`(
	`floorId` int(10) primary key auto_increment,
	`name` text,
	`active` tinyint(1),
	`description` text
);

insert into `floor_lookup_table`(`name`,`active`,`description`) values
('Ground','1','Ground Floor'),
('First','1','First Floor'),
('Second','1','Second Floor'),
('Third','1','Third Floor');


Drop table if exists `section_lookup_table`;
Create Table `section_lookup_table`(
	`sectionId` int(10) primary key auto_increment,
	`name` text,
	`active` tinyint(1),
	`description` text
);

insert into `section_lookup_table`(`name`,`active`,`description`) values
('Classroom','1','Classroom'),
('Library','1','Library'),
('Lab','1','Lab'),
('Washroom','1','Washroom'),
('Corridor','1','Corridor');



Drop table if exists `section_floor_mapper_table`;
Create Table `section_floor_mapper_table`(
	`sectionFloorId` varchar(5) primary key ,
	`floorId` int(10),
	`sectionId` int(10),
	
	foreign key (`floorId`) references `floor_lookup_table`(`floorId`),
	foreign key (`sectionId`) references `section_lookup_table`(`sectionId`)
);

insert into `section_floor_mapper_table`(`sectionFloorId`,`floorId`,`sectionId`) values
('1',1,1),
('2',1,4),
('3',1,5),
('4',2,1),
('5',2,3),
('6',2,4),
('7',2,5),
('8',3,1),
('9',3,2),
('10',3,4),
('11',3,5),
('12',4,1),
('13',4,4),
('14',4,5);


Drop table if exists `classroom_lookup_table`;
Create Table `classroom_lookup_table`(
	`classroomId` int(10) primary key auto_increment,
	`name` text,
	`active` tinyint(1),
	`description` text
);

insert into `classroom_lookup_table`(`classroomId`,`name`,`active`,`description`) values
('0','0','1','Dummy Classroom');

insert into `classroom_lookup_table`(`name`,`active`,`description`) values
('100','1','Ground Floor Classroom - 1'),
('101','1','Ground Floor Classroom - 2'),
('102','1','Ground Floor Classroom - 3'),
('103','1','Ground Floor Classroom - 4'),
('104','1','Ground Floor Classroom - 5'),
('200','1','First Floor Classroom - 1'),
('201','1','First Floor Classroom - 2'),
('202','1','First Floor Classroom - 3'),
('203','1','First Floor Classroom - 4'),
('300','1','Second Floor Classroom - 1'),
('301','1','Second Floor Classroom - 2'),
('302','1','Second Floor Classroom - 3'),
('303','1','Second Floor Classroom - 4'),
('304','1','Second Floor Classroom - 5'),
('305','1','Second Floor Classroom - 6'),
('400','1','Third Floor Classroom - 1'),
('401','1','Third Floor Classroom - 2'),
('402','1','Third Floor Classroom - 3'),
('403','1','Third Floor Classroom - 4'),
('404','1','Third Floor Classroom - 5'),
('405','1','Third Floor Classroom - 6');


Drop table if exists `classroom_floor_mapper_table`;
Create Table `classroom_floor_mapper_table`(
	`classroomFloorId` varchar(5) primary key ,
	`floorId` int(10),
	`classroomId` int(10),
	
	foreign key (`floorId`) references `floor_lookup_table`(`floorId`),
	foreign key (`classroomId`) references `classroom_lookup_table`(`classroomId`)
);

insert into `classroom_floor_mapper_table`(`classroomFloorId`,`floorId`,`classroomId`) values
('1',1,2),
('2',1,3),
('3',1,4),
('4',1,5),
('5',1,6),
('6',2,7),
('7',2,8),
('8',2,9),
('9',2,10),
('10',3,11),
('11',3,12),
('12',3,13),
('13',3,14),
('14',3,15),
('15',3,16),
('16',4,17),
('17',4,18),
('18',4,19),
('19',4,20),
('20',4,21),
('21',4,22);


Drop table if exists `complaint_status_lookup_table`;
Create Table `complaint_status_lookup_table`(
	`complaintStatusId` int(10) primary key auto_increment,
	`name` text,
	`active` tinyint(1),
	`description` text
);

insert into `complaint_status_lookup_table`(`name`,`active`,`description`) values
('Complaint Logged','1','Complaint Logged'),
('In Progress','1','In Progress'),
('Completed','1','Completed');


Drop table if exists `complaints_table`;
Create Table `complaints_table`(
	`complaintId` int(10) primary key auto_increment,
	`active` tinyint(1),
	`description` text,
	`loggedDate` datetime default NULL,
	`image` LONGBLOB default NULL,
	`complaintStatusId` int(10),
	`userId` int(10),
	`floorId` int(10),
	`sectionId` int(10),
	`classroomId` int(10),
	
	foreign key (`complaintStatusId`) references `complaint_status_lookup_table`(`complaintStatusId`),
	foreign key (`userId`) references `user_table`(`userId`),
	foreign key (`floorId`) references `floor_lookup_table`(`floorId`),
	foreign key (`sectionId`) references `section_lookup_table`(`sectionId`),
	foreign key (`classroomId`) references `classroom_lookup_table`(`classroomId`)
);

insert into `complaints_table`(`active`,`description`,`loggedDate`,`image`,`complaintStatusId`,`userId`, `floorId`, `sectionId`, `classroomId`) values
('1','There is garbage in ground floor','2020:03:12 08:00:00',null,3,2,1,1,2),
('1','There is garbage in first floor','2020:03:15 08:00:00',null,2,2,2,4,null),
('1','There is garbage in second floor','2020:03:18 08:00:00',null,1,2,3,5,null);