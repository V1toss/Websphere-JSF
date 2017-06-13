package ru.vkaretko.listmanager.web.service.rest;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ru.vkaretko.listmanager.model.ListItem;
import ru.vkaretko.listmanager.service.ListItemService;
import ru.vkaretko.listmanager.service.UserService;
import ru.vkaretko.listmanager.service.impl.ListItemServiceImpl;
import ru.vkaretko.listmanager.service.impl.UserServiceImpl;
import ru.vkaretko.listmanager.web.service.rest.viewmodel.ValueViewModel;

public class ListItemResource {
	private final UserService userService;
	private final ListItemService listItemService;
	
	public ListItemResource() {
		userService = new UserServiceImpl();
		listItemService = new ListItemServiceImpl();
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<ListItem> getAll(
			@PathParam("userId") Long userId) {
		return userService.getListItems(userId);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ListItem add(
			@PathParam("userId") Long userId,
			ValueViewModel valueViewModel) {
		return listItemService.addListItem(userId, valueViewModel.getValue());
	}
	
	@PUT
	@Path("/{listItemId}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ListItem update(
			@PathParam("userId") Long userId,
			@PathParam("listItemId") Long listItemId,
			ValueViewModel valueViewModel) {
		return listItemService.updateListItem(userId, listItemId, valueViewModel.getValue());
	}
	
	@DELETE
	@Path("/{listItemId}")
	public void delete(
			@PathParam("userId") Long userId,
			@PathParam("listItemId") Long listItemId) {
		listItemService.deleteListItem(userId, listItemId);
	}

}
