package Beans;

public class ProfessorUser {
	
	private int id = -1;
	private String password;
	private String email;
	private String fName;
	private String lName;
	private String school;
	private String department;

	
	public ProfessorUser(int id, String password, String email, String fName, String lName, String school, String department)
	{
		this.id = id;
		this.password = password;
		this.email = email;
		this.fName = fName;
		this.lName = lName;
		this.school = school;
		this.department = department;
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

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}


}
