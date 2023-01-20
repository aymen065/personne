package com.app.secTest.model;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonProperty;



@Entity 
public class AppUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String userName;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	@ManyToMany(fetch =FetchType.EAGER)
	private Collection<AppRole> appRoles = new ArrayList<AppRole>();
	
	public AppUser() {
		super();
	}
	public AppUser(long id, String userName, String password, Collection<AppRole> appRoles) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.appRoles = appRoles;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Collection<AppRole> getAppRoles() {
		return appRoles;
	}
	public void setAppRoles(Collection<AppRole> appRoles) {
		this.appRoles = appRoles;
	}

	
	
	
	
}
