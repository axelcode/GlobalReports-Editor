package com.globalreports.editor.configuration.template;

public class GRTemplate {
	private String pathTemplate;
	private String title;
	private String description;
	private String nameGRS;
	private String nameImage;
	
	public GRTemplate(String rootTemplate) {
		this.pathTemplate = rootTemplate;
	}
	public void setTitle(String value) {
		this.title = value;
	}
	public String getTitle() {
		return title;
	}
	public void setDescription(String value) {
		this.description = value;
	}
	public String getDescription() {
		return description;
	}
	public void setNameTemplate(String value) {
		this.nameGRS = value;
	}
	public String getNameTemplate() {
		return nameGRS;
	}
}
