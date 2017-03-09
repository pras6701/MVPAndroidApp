package com.mvp.app.data.network.model;

import java.io.Serializable;
import java.util.List;

public class BaseResponse implements Serializable{

	public enum ResponseStatus{
		SUCCESS,FAILURE
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ResponseStatus status;
	private List<String> message;
	private boolean isAccessTokenValid;
	/**
	 * @return the status
	 */
	public ResponseStatus getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(ResponseStatus status) {
		this.status = status;
	}
	/**
	 * @return the message
	 */
	public List<String> getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(List<String> message) {
		this.message = message;
	}
	/**
	 * @return the isAccessTokenValid
	 */
	public boolean isAccessTokenValid() {
		return isAccessTokenValid;
	}
	/**
	 * @param isAccessTokenValid the isAccessTokenValid to set
	 */
	public void setAccessTokenValid(boolean isAccessTokenValid) {
		this.isAccessTokenValid = isAccessTokenValid;
	}
	
}
