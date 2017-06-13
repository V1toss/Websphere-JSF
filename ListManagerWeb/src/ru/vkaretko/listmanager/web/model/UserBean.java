package ru.vkaretko.listmanager.web.model;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import ru.vkaretko.listmanager.model.ListItem;
import ru.vkaretko.listmanager.service.UserService;
import ru.vkaretko.listmanager.service.impl.UserServiceImpl;

@ManagedBean
@SessionScoped
public class UserBean {
	
	private final UserService userService;
	
	private Long userId;
	private String userName;
	
	public UserBean() {
		this.userService = new UserServiceImpl();
	}
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public List<ListItem> getListItems() {
		return userService.getListItems(userId);
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
