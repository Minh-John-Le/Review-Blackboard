package Beans;

public class Professor {
	private int user_ID = -1;
	private String fname = "";
	private String lname = "";
	private String schoolName = "";
	
	
	public Professor(int user_ID, String fname, String lname, String schoolName)
	{
		this.user_ID = user_ID;
		this.fname = fname;
		this.lname = lname;
		this.schoolName = schoolName;
		
	}
	
	
	public int getUser_ID() {
		return user_ID;
	}
	public void setUser_ID(int user_ID) {
		this.user_ID = user_ID;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	
	
	
	

}
