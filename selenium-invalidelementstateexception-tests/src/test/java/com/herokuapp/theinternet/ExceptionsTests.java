package com.herokuapp.theinternet;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class ExceptionsTests {

	private WebDriver driver;

	@Parameters({ "browser" })
	@BeforeMethod(alwaysRun = true)
	private void setUp(String browser) {

//	     Create Driver
		switch (browser) {
		case "chrome":
			driver = new ChromeDriver();
			System.out.println("chrome browser started");
			break;

		case "firefox":
			driver = new FirefoxDriver();
			System.out.println("firefox browser started");
			break;

		default:
			System.out.println("Do not know how to start" + browser + "starting chrome instead");
			driver = new ChromeDriver();
			break;
		}

		driver.manage().window().maximize();

	}

	@Test
	public void invalidElementStateExceptionTest() {

		// open webpage
		String url = "https://practicetestautomation.com/practice-test-exceptions/";
		driver.get(url);
		System.out.println("page opened");
		
		//Clear input field
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		WebElement row1Input = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row1']/input")));
		WebElement editButton = driver.findElement(By.id("edit_btn"));
		editButton.click();
		
		wait.until(ExpectedConditions.elementToBeClickable(row1Input));
		row1Input.clear();
		
		//Type text into the input field
		row1Input.sendKeys("burger");
		
		WebElement saveButton = driver.findElement(By.id("save_btn"));
		saveButton.click();
		
		//Verify text changed
		String value = row1Input.getAttribute("value");
		Assert.assertEquals(value, "burger", "Input1 field value is not expected");
		
		WebElement confirmationMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmation")));
		String actualMessage = confirmationMessage.getText();
		Assert.assertEquals(actualMessage, "Row 1 was saved", "Actual message does not contain the expected message");
	}

	@AfterMethod(alwaysRun = true)
	private void tearDown() {
//      close browser
		driver.quit();
		System.out.println("Test Finished");
	}

}
