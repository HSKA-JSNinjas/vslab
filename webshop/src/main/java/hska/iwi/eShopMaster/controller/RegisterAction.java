package hska.iwi.eShopMaster.controller;


import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import hska.iwi.eShopMaster.DataHandler;
import hska.iwi.eShopMaster.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

public class RegisterAction extends ActionSupport {

    /**
     *
     */
    private static final long serialVersionUID = 3655805600703279195L;
    private String username;
    private String password1;
    private String password2;

    @Override
    public String execute() throws Exception {
        
        // Return string:
        String result = "input";

        ResponseEntity<User> response = new DataHandler().createUser(this.username, this.password1);

        if (response.getStatusCode() == HttpStatus.CREATED) {
            addActionMessage("user registered, please login");
            addActionError("user registered, please login");
            Map<String, Object> session = ActionContext.getContext().getSession();
            session.put("message", "user registered, please login");
            result = "success";
        } else {
            addActionError(getText("error.username.alreadyInUse"));
        }

        return result;

    }
    
	@Override
	public void validate() {
		if (getUsername().length() == 0) {
			addActionError(getText("error.username.required"));
		}
		if (getPassword1().length() == 0) {
			addActionError(getText("error.password.required"));
		}
		if (getPassword2().length() == 0) {
			addActionError(getText("error.password.required"));
		}
		
		if (!getPassword1().equals(getPassword2())) {
			addActionError(getText("error.password.notEqual"));
		}
	}

    public String getUsername() {
        return (this.username);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword1() {
        return (this.password1);
    }

    public void setPassword1(String password) {
        this.password1 = password;
    }
    
    public String getPassword2() {
        return (this.password2);
    }

    public void setPassword2(String password) {
        this.password2 = password;
    }

}
