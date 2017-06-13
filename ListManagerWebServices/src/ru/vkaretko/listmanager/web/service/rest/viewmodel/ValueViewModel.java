package ru.vkaretko.listmanager.web.service.rest.viewmodel;

public class ValueViewModel {
	// { "value": "abc" }
		private String value;
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
}
