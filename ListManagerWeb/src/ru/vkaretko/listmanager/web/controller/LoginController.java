package ru.vkaretko.listmanager.web.controller;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import ru.vkaretko.listmanager.model.User;
import ru.vkaretko.listmanager.service.UserService;
import ru.vkaretko.listmanager.service.impl.UserServiceImpl;
import ru.vkaretko.listmanager.web.model.UserBean;

@ManagedBean
@RequestScoped
public class LoginController {
	
private final UserService userService;
	
	@ManagedProperty("#{userBean}")
	private UserBean userBean;
	
	public LoginController() {
		userService = new UserServiceImpl();
	}
	
	public String login() {
		String outcome = null;
		
		User user = userService.authenticateUser(userBean.getUserName());
		if (user != null) {
			userBean.setUserId(user.getId());
			outcome = "list?faces-redirect=true";
		}
		
		return outcome;
	}
	
	public void setUserBean(UserBean userBean) {
		this.userBean = userBean;
	}
}
