package com.app.secTest.services;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.secTest.model.AppRole;
import com.app.secTest.model.AppUser;
import com.app.secTest.repositories.AppRoleRepository;
import com.app.secTest.repositories.AppUserRepository;

@Service
@Transactional
public class AccountServiceImp implements AccountService{

	private AppUserRepository userRepo;
	private AppRoleRepository roleRepo;
	private PasswordEncoder pwdEnc;
	public AccountServiceImp(AppUserRepository userRepo, AppRoleRepository roleRepo , PasswordEncoder pwdEnc) {
		this.userRepo = userRepo;
		this.roleRepo = roleRepo;
		this.pwdEnc = pwdEnc;
	}

	@Override
	public AppUser addNewUser(AppUser appUser) {
		String pwd = appUser.getPassword();
		appUser.setPassword(pwdEnc.encode(pwd));
		return userRepo.save(appUser);
	}

	@Override
	public AppRole addNewRole(AppRole appRole) {
		return roleRepo.save(appRole);
	}

	@Override
	public void addRoletouser(String userName, String roleName) {
		AppUser appUser = userRepo.findByUserName(userName);
		AppRole appRole = roleRepo.findByRoleName(roleName);
		appUser.getAppRoles().add(appRole);
	}

	@Override
	public AppUser loaduserByUserName(String userName) {
		return userRepo.findByUserName(userName);
	}

	@Override
	public List<AppUser> listUsers() {
		return userRepo.findAll();
	}

}
