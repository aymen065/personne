package com.app.secTest.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.secTest.model.AppRole;
import com.app.secTest.model.AppUser;
import com.app.secTest.services.AccountService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/app")
public class AppUserController {

	private AccountService accountService;

	public AppUserController(AccountService accountService) {
		super();
		this.accountService = accountService;
	}

	@PostMapping("/createUser")
	public AppUser createUser(@RequestBody AppUser user) {
		return accountService.addNewUser(user);
	}

	@PostMapping("/createRole")
	public AppRole createRole(@RequestBody AppRole role) {
		return accountService.addNewRole(role);
	}

	@PostMapping("/addRoleToUser")
	public AppUser addRoleToUser(@RequestBody RoleUserForm ruf) {
		accountService.addRoletouser(ruf.getUserName(), ruf.getRoleName());
		return accountService.loaduserByUserName(ruf.getUserName());
	}

	@GetMapping("/getAllUsers")
	public List<AppUser> getUsers() {
		return accountService.listUsers();
	}

	@GetMapping("/refreshToken")
	public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String authorizationToken = request.getHeader("Authorization");
		if (authorizationToken != null && authorizationToken.startsWith("Bearer ")) {

			try {
				String refreshToken = authorizationToken.substring(7);
				Algorithm algorithm = Algorithm.HMAC256("mySecret1234");
				JWTVerifier jwtVerifier = JWT.require(algorithm).build();
				DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);
				String userName = decodedJWT.getSubject();
				AppUser appUser = accountService.loaduserByUserName(userName);
				String jwtAccessToken = JWT.create().withSubject(appUser.getUserName())
						.withExpiresAt(new Date(System.currentTimeMillis() + 5 * 30 * 1000))
						.withIssuer(request.getRequestURI().toString())
						.withClaim("roles",
								appUser.getAppRoles().stream().map(ga -> ga.getRoleName()).collect(Collectors.toList()))
						.sign(algorithm);

				Map<String, String> idToken = new HashMap<>();
				idToken.put("access_token", jwtAccessToken);
				idToken.put("refresh_token", refreshToken);
				// response.setHeader("Authorization", jwtAccessToken);
				response.setContentType("application/json");
				new ObjectMapper().writeValue(response.getOutputStream(), idToken);

			} catch (Exception e) {
				throw e;
			}
		} else {
			throw new RuntimeException("Refresh token required !!");
		}
	}

}

class RoleUserForm {
	private String userName;
	private String roleName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}
