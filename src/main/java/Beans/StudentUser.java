package Beans;

public class StudentUser {
	
	private int id = -1;
	private String password;
	private String email;
	private String fName;
	private String lName;
	private String major;

	
	public StudentUser(int id, String password, String email, String fName, String lName, String major)
	{
		this.id = id;
		this.password = password;
		this.email = email;
		this.fName = fName;
		this.lName = lName;
		this.major = major;
	}
	
	public int getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getMajor() {
		return major;
	}
	public void setMajor(String major) {
		this.major = major;
	}


}
