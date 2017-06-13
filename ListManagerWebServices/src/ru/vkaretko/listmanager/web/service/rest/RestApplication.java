package ru.vkaretko.listmanager.web.service.rest;

import javax.ws.rs.core.Application;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/rest")
public class RestApplication extends Application{
	@SuppressWarnings("unchecked")
	@Override
	public Set<Class<?>> getClasses() {
		return new HashSet<Class<?>>(
				Arrays.asList(
						UserResource.class,
						ListItemResource.class));
	}
}
