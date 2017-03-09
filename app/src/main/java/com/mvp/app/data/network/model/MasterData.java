package com.mvp.app.data.network.model;

import java.io.Serializable;
import java.util.List;

public class MasterData implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Service> services;
	private String appWebLink;
	/**
	 * @return the services
	 */
	public List<Service> getServices() {
		return services;
	}
	/**
	 * @param services the services to set
	 */
	public void setServices(List<Service> services) {
		this.services = services;
	}

	public String getAppWebLink() {
		return appWebLink;
	}

	public void setAppWebLink(String appWebLink) {
		this.appWebLink = appWebLink;
	}
}
