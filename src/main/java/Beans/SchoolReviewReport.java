package Beans;

import java.sql.Date;

public class SchoolReviewReport {
	private int reportId;
	private int reporterId;
	private int scrId;
	private String body;
	private Date date;
	
	public int getReportId() {
		return reportId;
	}
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}
	public int getReporterId() {
		return reporterId;
	}
	public void setReporterId(int reporterId) {
		this.reporterId = reporterId;
	}
	public int getScrId() {
		return scrId;
	}
	public void setScrId(int scrId) {
		this.scrId = scrId;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	

}
