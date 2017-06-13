package ru.vkaretko.listmanager.service.test;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ru.vkaretko.listmanager.model.ListItem;
import ru.vkaretko.listmanager.service.ListItemService;
import ru.vkaretko.listmanager.service.impl.ListItemServiceImpl;
import ru.vkaretko.listmanager.test.DataSourceSetupUtil;

public class UserServiceImplTests {
	
	@Before
	public void setup() throws Exception {
		DataSourceSetupUtil.setup();
	}
	
	@After
	public void tearDown() throws Exception {
		DataSourceSetupUtil.tearDown();
	}
	
	@Test
	public void whenAddingAListItem_givenInvalidInput_itShouldReturnNull() {
		// Arrange
		final ListItemService listItemService = new ListItemServiceImpl();
		
		// Act
		ListItem listItem = listItemService.addListItem(1L, "");
		
		// Assert
		Assert.assertNull(listItem);
	}
	
	@Test
	public void whenAddingAListItem_givenValidInput_itShouldReturnAListItemWithAnId() throws SQLException {
		// Arrange
		final long initialListItemCount = DataSourceSetupUtil.getListItemsCount();
		final ListItemService listItemService = new ListItemServiceImpl();
		
		// Act
		ListItem listItem = listItemService.addListItem(1L, "Testing 123");
		
		// Assert
		Assert.assertNotNull(listItem);
		Assert.assertNotNull(listItem.getId());
		Assert.assertTrue(listItem.getId() > 0);
		
		Assert.assertEquals(initialListItemCount + 1, DataSourceSetupUtil.getListItemsCount());
	}
	
	@Test
	public void whenUpdatingAListItem_givenInvalidInput_itShouldReturnNull() {
		// Arrange
		final ListItemService listItemService = new ListItemServiceImpl();
		
		// Act
		ListItem listItem = listItemService.updateListItem(1L, 2L, "Abc");
		
		// Assert
		Assert.assertNull(listItem);
	}
	
	@Test
	public void whenUpdatingAListItem_givenWrongOwner_itShouldDoNothing() {
		// Arrange
		final ListItemService listItemService = new ListItemServiceImpl();
		final ListItem addedListItem = listItemService.addListItem(1L, "Testing 123");
		
		// Act
		ListItem listItem = listItemService.updateListItem(2L, addedListItem.getId(), "Whatever");
		
		// Assert
		Assert.assertNull(listItem);
	}
	
	@Test
	public void whenUpdatingAListItem_givenValidInput_itShouldReturnTheUpdatedListItem() throws Exception {
		// Arrange
		final ListItemService listItemService = new ListItemServiceImpl();
		final ListItem addedListItem = listItemService.addListItem(1L, "Testing 123");
		final long initialListItemCount = DataSourceSetupUtil.getListItemsCount();
		final String updatedValue = "Abc 123";
		
		// Act
		ListItem listItem = listItemService.updateListItem(1L, addedListItem.getId(), updatedValue);
		
		// Assert
		Assert.assertNotNull(listItem);
		Assert.assertEquals(addedListItem.getId(), listItem.getId());
		Assert.assertEquals(updatedValue, listItem.getValue());
		
		Assert.assertEquals(initialListItemCount, DataSourceSetupUtil.getListItemsCount());
	}
	
	@Test
	public void whenDeletingAListItem_givenInvalidInput_itShouldDoNothing() throws Exception {
		// Arrange
		final ListItemService listItemService = new ListItemServiceImpl();
		listItemService.addListItem(1L, "Testing 123");
		final long initialListItemCount = DataSourceSetupUtil.getListItemsCount();
		
		// Act
		listItemService.deleteListItem(1L, 1000L);
		
		// Assert
		Assert.assertEquals(initialListItemCount, DataSourceSetupUtil.getListItemsCount());
	}
	
	@Test
	public void whenDeletingAListItem_givenWrongOwner_itShouldDoNothing() throws Exception {
		// Arrange
		final ListItemService listItemService = new ListItemServiceImpl();
		final ListItem addedListItem = listItemService.addListItem(1L, "Testing 123");
		final long initialListItemCount = DataSourceSetupUtil.getListItemsCount();
		
		// Act
		listItemService.deleteListItem(2L, addedListItem.getId());
		
		// Assert
		Assert.assertEquals(initialListItemCount, DataSourceSetupUtil.getListItemsCount());
	}
	
	@Test
	public void whenDeletingAListItem_givenValidInput_itShouldDelete() throws SQLException {
		// Arrange
		final ListItemService listItemService = new ListItemServiceImpl();
		final ListItem addedListItem = listItemService.addListItem(1L, "Testing 123");
		final long initialListItemCount = DataSourceSetupUtil.getListItemsCount();
		
		// Act
		listItemService.deleteListItem(1L, addedListItem.getId());
		
		// Assert
		Assert.assertEquals(initialListItemCount - 1, DataSourceSetupUtil.getListItemsCount());
	}
}
