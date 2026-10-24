package com.redbus;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RedBusAutomationAssignment3 {

	public static void main(String[] args) throws InterruptedException {

		ChromeOptions chromeoptions = new ChromeOptions();
		chromeoptions.addArguments("--start-maximized");

		WebDriver driver = new ChromeDriver(chromeoptions);

		driver.get("https://www.redbus.in/");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

//		driver.manage().window().maximize();

		By sourceButtonLocator = By.xpath("//div[contains(@class,\"srcDestWrapper\")]");
//		WebElement sourceButton = driver.findElement(sourceButtonLocator);
		WebElement sourceButton = wait.until(ExpectedConditions.visibilityOfElementLocated(sourceButtonLocator));
		sourceButton.click();
		WebElement sourceBoxTextElement = driver.switchTo().activeElement();
		sourceBoxTextElement.sendKeys("Mumbai");

		By searchCategoryLocator = By.xpath("//div[contains(@class,'searchCategory')]");

		List<WebElement> searchCategories = wait
				.until(ExpectedConditions.numberOfElementsToBeMoreThan(searchCategoryLocator, 1));

		WebElement locationSearchResult = searchCategories.get(0);

		By locationNamelocator = By.xpath(".//div[contains(@class,\"listHeader\")]");
		List<WebElement> locationList = locationSearchResult.findElements(locationNamelocator);

		for (WebElement location : locationList) {

			if (location.getText().equalsIgnoreCase("Mumbai")) {
				location.click();
				break;
			}
		}

// Destination field

		WebElement destinationBoxTextElement = driver.switchTo().activeElement();
		destinationBoxTextElement.sendKeys("Pune");

		By destinationSearchCategoriesLocator = By.xpath("//div[contains(@class,'searchCategory')]");
		List<WebElement> destinationCategoriesElements = wait
				.until(ExpectedConditions.numberOfElementsToBeMoreThan(destinationSearchCategoriesLocator, 2));

		WebElement destinationLocationCategory = destinationCategoriesElements.get(0);

		By destinationLocationLocator = By.xpath(".//div[contains(@class,'listHeader')]");

		List<WebElement> destinationLocationElements = destinationLocationCategory
				.findElements(destinationLocationLocator);

		for (WebElement destinationElement : destinationLocationElements) {
			if (destinationElement.getText().equalsIgnoreCase("Pune")) {
				destinationElement.click();
				break;
			}
		}

	}

}
