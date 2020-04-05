package com.org.db;

public class Queries {
	
	// User Queries
	public final static String GET_USER_BY_LOGINID_PASSWORD = "select userId from user_table where loginId = ? and password = ? and active='1'";
	
	// Location Queries
	public final static String GET_ALL_FLOORS = "select floorId, name from floor_lookup_table where active='1'";
	public final static String GET_SECTIONS_BY_FLOOR = "select sectionId, name from section_lookup_table where sectionId in (select sectionId from section_floor_mapper_table where floorId = ?) and active='1'";
	public final static String GET_CLASSROOM_BY_FLOOR = "select classroomId, name from classroom_lookup_table where classroomId in (select classroomId from classroom_floor_mapper_table where floorId = ?) and active='1'";
	public final static String GET_FLOOR_BY_FLOORID = "select name from floor_lookup_table where floorId = ? and active='1'";
	public final static String GET_SECTION_BY_SECTIONID = "select name from section_lookup_table where sectionId = ? and active='1'";
	public final static String GET_CLASSROOM_BY_CLASSROOMID = "select name from classroom_lookup_table where classroomId = ? and active='1'";
	
	// Complaint Queries
	public final static String GET_ALL_COMPLAINTS = "select complaintId, description, loggedDate, image, complaintStatusId, userId, floorId, sectionId, classroomId from complaints_table where active='1'";
	public final static String GET_COMPLAINT_STATUS_BY_NAME = "select complaintStatusId from complaint_status_lookup_table where name = ? and active='1'";

	public final static String INSERT_COMPLAINTS = "insert into `complaints_table`(`active`,`description`,`loggedDate`,`image`,`complaintStatusId`,`userId`,`floorId`, `sectionId`, `classroomId`) "
			+ "values ('1',?,?,?,?,?,?,?,?)";
	public final static String INSERT_COMPLAINTS_TEMP = "insert into `complaints_table`(`active`,`description`,`loggedDate`,`complaintStatusId`,`userId`,`floorId`, `sectionId`, `classroomId`) "
			+ "values ('1',?,?,?,?,?,?,?)";
}
