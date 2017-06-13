package ru.vkaretko.listmanager.service.impl;

import java.util.List;

import ru.vkaretko.listmanager.data.ListItemDao;
import ru.vkaretko.listmanager.data.UserDao;
import ru.vkaretko.listmanager.data.impl.ListItemDaoImpl;
import ru.vkaretko.listmanager.data.impl.UserDaoImpl;
import ru.vkaretko.listmanager.model.ListItem;
import ru.vkaretko.listmanager.model.User;
import ru.vkaretko.listmanager.service.UserService;

public class UserServiceImpl implements UserService {
	private final UserDao userDao;
	private final ListItemDao listItemDao;
	
	public UserServiceImpl() {
		this.userDao = new UserDaoImpl();
		this.listItemDao = new ListItemDaoImpl();
	}
	
	@Override
	public User authenticateUser(String username) {
		User user = null;
		
		if (username != null && !"".equals(username.trim())) {
			user = userDao.getUser(username);
		}
		
		return user;
	}
	
	@Override
	public List<ListItem> getListItems(Long userId) {
		return listItemDao.getListItemsByUserId(userId);
	}
}
