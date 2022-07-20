package Beans;

public class ProfessorReview {
	 private int reviewId = -1;
	 private String publishDate = "";
	 private String content = "";
	 private int studentId = -1;
	 private int professorId = -1;
	 private int quality = -1;
	 private int difficulty = -1;
	 private String courseName = "";
	 private String class_type = "";
	 private String grade = "";
	 private String year = "";
	 private String semester = "";
	 private String comment = "";
	 
	 
	public ProfessorReview(int reviewID, String content, int professorID, int quality, int difficulty,
				String courseName, String class_type, String grade, String year, String semester, String comment)
	{
		this.reviewId = reviewID;
		this.content = content;
		this.professorId = professorID;
		this.quality = quality;
		this.difficulty = difficulty;
		this.courseName = courseName;
		this.class_type = class_type;
		this.grade = grade;
		this.year = year;
		this.semester = semester;
		this.comment = comment;
	}
	
	
	public int getReviewId() {
		return reviewId;
	}
	public void setReviewId(int reviewId) {
		this.reviewId = reviewId;
	}
	public String getPublishDate() {
		return publishDate;
	}
	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getProfessorId() {
		return professorId;
	}
	public void setProfessorId(int professorId) {
		this.professorId = professorId;
	}
	public int getQuality() {
		return quality;
	}
	public void setQuality(int quality) {
		this.quality = quality;
	}
	public int getDifficulty() {
		return difficulty;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	public String getClass_type() {
		return class_type;
	}
	public void setClass_type(String class_type) {
		this.class_type = class_type;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getSemester() {
		return semester;
	}
	public void setSemester(String semester) {
		this.semester = semester;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}
	 
	 
	

}
