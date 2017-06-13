package ru.vkaretko.listmanager.data;

import ru.vkaretko.listmanager.model.User;

public interface UserDao {
	User getUser(String username);
}
