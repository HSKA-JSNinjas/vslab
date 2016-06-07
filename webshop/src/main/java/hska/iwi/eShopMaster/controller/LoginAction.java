package hska.iwi.eShopMaster.controller;

import hska.iwi.eShopMaster.model.User;
import hska.iwi.eShopMaster.model.businessLogic.manager.UserManager;
import hska.iwi.eShopMaster.model.businessLogic.manager.impl.UserManagerImpl;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

public class LoginAction extends ActionSupport {

	/**
     *
     */
	private static final long serialVersionUID = -983183915002226000L;
	private String username = null;
	private String password = null;
	private String firstname;
	private String lastname;
	private String role;
	

	@Override
	public String execute() throws Exception {

		// Return string:
		String result = "input";

		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
			protected boolean hasError(HttpStatus statusCode) {
				return false;
			}});

		User u = new User((long) 0, username, password, "");

		ResponseEntity<User> responseEntity = restTemplate.postForEntity("http://localhost:8100/user-api/login" , u, User.class);


		if (responseEntity.getStatusCode() == HttpStatus.OK) {

			u = responseEntity.getBody();
			// Get session to save user role and login:
			Map<String, Object> session = ActionContext.getContext().getSession();

			// Save user object in session:
			session.put("webshop_user", u);
			session.put("message", "");
			firstname= u.getName();
			role = u.getRole();
			result = "success";
		} else {
			addActionError(getText("error.username.wrong"));
		}

		return result;
	}
	
	@Override
	public void validate() {
		if (getUsername().length() == 0) {
			addActionError(getText("error.username.required"));
		}
		if (getPassword().length() == 0) {
			addActionError(getText("error.password.required"));
		}
	}

	public String getUsername() {
		return (this.username);
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return (this.password);
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
