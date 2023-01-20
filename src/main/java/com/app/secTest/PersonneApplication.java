package com.app.secTest;

import java.util.ArrayList;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.app.secTest.model.AppRole;
import com.app.secTest.model.AppUser;
import com.app.secTest.services.AccountService;

@SpringBootApplication
public class PersonneApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonneApplication.class, args);
	}

	@Bean
	PasswordEncoder pwdEnc() {
		return new BCryptPasswordEncoder();
	}
	
	/*@Bean
	CommandLineRunner start(AccountService accountService) {
		return args ->{
			accountService.addNewRole(new AppRole(1,"USER"));
			accountService.addNewRole(new AppRole(2,"CUSTOMER_MANAGER"));
			accountService.addNewRole(new AppRole(3,"PRODUCT_MANAGER"));
			accountService.addNewRole(new AppRole(4,"ADMIN"));
			accountService.addNewRole(new AppRole(3,"BILLS_MANAGER"));

			accountService.addNewUser(new AppUser(1,"user1","1234",new ArrayList<AppRole>()));
			accountService.addNewUser(new AppUser(2,"admin","1234",new ArrayList<AppRole>()));
			accountService.addNewUser(new AppUser(3,"user2","1234",new ArrayList<AppRole>()));
			accountService.addNewUser(new AppUser(4,"user3","1234",new ArrayList<AppRole>()));
			accountService.addNewUser(new AppUser(5,"user4","1234",new ArrayList<AppRole>()));

			
			accountService.addRoletouser("user1", "USER");
			accountService.addRoletouser("admin", "USER");
			accountService.addRoletouser("admin", "ADMIN");
			accountService.addRoletouser("user2", "USER");
			accountService.addRoletouser("user2", "CUSTOMER_MANAGER");
			accountService.addRoletouser("user3", "USER");
			accountService.addRoletouser("user3", "PRODUCT_MANAGER");
			accountService.addRoletouser("user4", "USER");
			accountService.addRoletouser("user4", "BILLS_MANAGER");


			
		};
	}*/
}
