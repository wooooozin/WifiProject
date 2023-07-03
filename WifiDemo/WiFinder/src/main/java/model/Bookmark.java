package model;

public class Bookmark {
	private String bookmarkId;
	private String bookmarkName;
	private String priority;
	private String regitDate;
	private String modifDate;
	
	public String getBookmarkId() {
		return bookmarkId;
	}
	
	public void setBookmarkId(String bookmarkId) {
		this.bookmarkId = bookmarkId;
	}
	
	public String getBookmarkName() {
		return bookmarkName;
	}
	
	public void setBookmarkName(String bookmarkName) {
		this.bookmarkName = bookmarkName;
	}
	
	public String getPriority() {
		return priority;
	}
	
	public void setPriority(String priority) {
		this.priority = priority;
	}
	
	public String getRegitDate() {
		return regitDate;
	}
	
	public void setRegitDate(String regitDate) {
		this.regitDate = regitDate;
	}
	
	public String getModifDate() {
		return modifDate;
	}
	
	public void setModifDate(String updateDate) {
		this.modifDate = updateDate;
	}
}
