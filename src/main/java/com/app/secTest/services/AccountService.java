package com.app.secTest.services;

import java.util.List;

import com.app.secTest.model.AppRole;
import com.app.secTest.model.AppUser;

public interface AccountService {

	AppUser addNewUser(AppUser appUser);
	AppRole addNewRole(AppRole appRole);
	void addRoletouser(String userName , String roleName);
	AppUser loaduserByUserName(String userName);
	List<AppUser> listUsers(); 
}
  