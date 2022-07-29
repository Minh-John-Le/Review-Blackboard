package Beans;

import java.sql.Date;

public class SchoolReview {
	private String body;
	private int quality;
	private int safety;
	private int location;
	private int infrastructure;
	private Date year;
	private int schoolId;
	private int scrId;
	private int author;
	private int attFromYear;
	private int attToYear;
	
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public int getQuality() {
		return quality;
	}
	public void setQuality(int quality) {
		this.quality = quality;
	}
	public int getSafety() {
		return safety;
	}
	public void setSafety(int safety) {
		this.safety = safety;
	}
	public int getLocation() {
		return location;
	}
	public void setLocation(int location) {
		this.location = location;
	}
	public int getInfrastructure() {
		return infrastructure;
	}
	public void setInfrastructure(int infrastructure) {
		this.infrastructure = infrastructure;
	}
	public Date getYear() {
		return year;
	}
	public void setYear(Date year) {
		this.year = year;
	}
	public int getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(int schoolId) {
		this.schoolId = schoolId;
	}
	public int getScrId() {
		return scrId;
	}
	public void setScrId(int scrId) {
		this.scrId = scrId;
	}
	public int getAuthor() {
		return author;
	}
	public void setAuthor(int author) {
		this.author = author;
	}
	public int getAttFromYear() {
		return attFromYear;
	}
	public void setAttFromYear(int attFromYear) {
		this.attFromYear = attFromYear;
	}
	public int getAttToYear() {
		return attToYear;
	}
	public void setAttToYear(int attToYear) {
		this.attToYear = attToYear;
	}
}