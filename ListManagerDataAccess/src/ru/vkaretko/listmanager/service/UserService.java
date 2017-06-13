package ru.vkaretko.listmanager.service;

import java.util.List;

import ru.vkaretko.listmanager.model.ListItem;
import ru.vkaretko.listmanager.model.User;

public interface UserService {
	
	User authenticateUser(String username);
	
	List<ListItem> getListItems(Long userId);
	
}
