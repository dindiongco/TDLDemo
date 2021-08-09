package com.qa.index;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class IndexPage {
	
	public final String URL = "http://127.0.0.1:5500/tdl-third-attempt/index.html";
	
	@FindBy(id = "task-value")
	private WebElement createTask;
	
	@FindBy(id = "create-button")
	private WebElement createBtn;
	
	@FindBy(id = "task-title")
	private WebElement taskTitle;
	
	public void create(String task) {
		createTask.sendKeys(task);
		createBtn.click();
	}
	
	public String checkTask() {
		return taskTitle.getText();
	}

}
