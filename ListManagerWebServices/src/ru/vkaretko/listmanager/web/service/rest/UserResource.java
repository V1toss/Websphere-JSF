package ru.vkaretko.listmanager.web.service.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import ru.vkaretko.listmanager.model.User;
import ru.vkaretko.listmanager.service.UserService;
import ru.vkaretko.listmanager.service.impl.UserServiceImpl;

@Path("/user")
public class UserResource {
	
private final UserService userService;
	
	public UserResource() {
		userService = new UserServiceImpl();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Long getUserId(@QueryParam("userName") String userName) {
		User user = userService.authenticateUser(userName);
		if (user != null) {
			return user.getId();
		}
		return null;
	}
}
