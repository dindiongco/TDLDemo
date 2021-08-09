package com.qa.selenium;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import com.qa.index.IndexPage;

public class IndexTest {
	
	private WebDriver driver;
	
//	@BeforeEach
//	public void setup() {
//		this.driver = new ChromeDriver();
//		this.driver.manage().window().maximize();
//	}
//	
//	@Test
//	public void testCreate() {
//		
//		String testString = "Get coffee";
//		
//		IndexPage index = PageFactory.initElements(driver, IndexPage.class);
//		
//		this.driver.get(index.URL);
//		
//		index.create(testString);
//		
//		assertThat(index.checkTask().toLowerCase().contains(testString));
//	}
//	
//	@AfterEach
//	public void teardown() {
//		this.driver.close();
//	}
}
