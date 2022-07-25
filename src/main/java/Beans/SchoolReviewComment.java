package Beans;

import java.sql.Date;

public class SchoolReviewComment {
	private int scrId;
	private int professor;
	private Date date;
	private String body;
	
	public int getScrId() {
		return scrId;
	}
	public void setScrId(int scrId) {
		this.scrId = scrId;
	}
	public int getProfessor() {
		return professor;
	}
	public void setProfessor(int professor) {
		this.professor = professor;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
}