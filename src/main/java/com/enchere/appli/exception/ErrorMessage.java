package com.enchere.appli.exception;
import java.util.Date;

public class ErrorMessage {
	private int statusCode;
	private Date timestamp;
	private String message;
	private String description;
	
	public ErrorMessage(int statu,Date date,String message,String desc) {
		setStatusCode(statu);
		setTimestamp(date);
		setMessage(message);
		setDescription(desc);
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
