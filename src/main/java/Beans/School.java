package Beans;

public class School {
	private String name;
	private String street;
	private String city;
	private String state;
	private String zip;
	private int schoolId;
	
	public School()
	{
		
	}
	
	public School(String name, String street, String city, String state, String zip, int schoolId)
	{
		this.name = name;
		this.street = street;
		this.city = city;
		this.zip = zip;
		this.state = state;
		this.schoolId = schoolId;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	
	@Override
	public String toString() {
		return "School [name=" + name + ", street=" + street + ", city=" + city + ", state=" + state + ", zip=" + zip
				+ ", schoolId=" + schoolId + "]";
	}

}
