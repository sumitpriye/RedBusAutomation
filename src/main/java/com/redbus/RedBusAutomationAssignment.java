package com.redbus;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RedBusAutomationAssignment {

	public static void main(String[] args) throws InterruptedException {

//	Using chromeOptions to maximize the window
		ChromeOptions chromeoptions = new ChromeOptions();
		chromeoptions.addArguments("--start-maximized");

		WebDriver driver = new ChromeDriver(chromeoptions);
//		/Launching redbus website
		driver.get("https://www.redbus.in/");

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

		By sourceButtonLocator = By.xpath("//div[contains(@class,\"srcDestWrapper\")]");
		WebElement sourceButton = wait.until(ExpectedConditions.visibilityOfElementLocated(sourceButtonLocator));
		sourceButton.click();

		String sourceLocation = "Mumbai";
		String destinationLocation = "Pune";
		selectLocation(driver, wait, sourceLocation);
		selectLocation(driver, wait, destinationLocation);

		By searchButtonLocator = By.xpath("//button[contains(@class,'searchButtonWrapper')]");

		WebElement searchButtonElement = wait.until(ExpectedConditions.elementToBeClickable(searchButtonLocator));
		searchButtonElement.click();

		By eveningButtonLocator = By.xpath("//div[contains(text() ,'18:00-24:00')]");
		WebElement eveningButtonElement = wait.until(ExpectedConditions.elementToBeClickable(eveningButtonLocator));
		eveningButtonElement.click();

		By primoButtonLocator = By.xpath("//div[contains(text() ,'Primo')]");
		WebElement primoButtonElement = wait.until(ExpectedConditions.elementToBeClickable(primoButtonLocator));
		primoButtonElement.click();

		By busesfoundLocator = By.xpath("//span[contains(@class,'subtitle')]");

		if (wait.until(ExpectedConditions.textToBePresentInElementLocated(busesfoundLocator, "buses"))) {
			WebElement busesFoundElement = wait.until(ExpectedConditions.visibilityOfElementLocated(busesfoundLocator));
			System.out.println(busesFoundElement.getText());
		}

		By tuppleWrapperLocator = By.xpath("//li[contains(@class,'tupleWrapper')]");
		By busesNameLocator = By.xpath(".//div[contains(@class,'travelsName')]");

		JavascriptExecutor js = (JavascriptExecutor) driver;

//		handling Lazy loading by scrolling until we see the end Of list text at the bottom of page
		while (true) {
			List<WebElement> tuppleWrapperElements = wait
					.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tuppleWrapperLocator));
			List<WebElement> endOfList = driver.findElements(By.xpath("//span[contains(text(),'End of list')]"));
			if (!endOfList.isEmpty()) {
				break;

			}

			js.executeScript("arguments[0].scrollIntoView({behavior:'smooth'})",
					tuppleWrapperElements.get(tuppleWrapperElements.size() - 3));
		}

		List<WebElement> tuppleWrapperElements = wait
				.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(tuppleWrapperLocator));

//		Printing all the buses name
		for (WebElement tuppleWrapperElement : tuppleWrapperElements) {
			System.out.println(tuppleWrapperElement.findElement(busesNameLocator).getText());
		}

		System.out.println("Total buses Found - " + tuppleWrapperElements.size());
	}

	public static void selectLocation(WebDriver driver, WebDriverWait wait, String locationData) {
		WebElement sourceBoxTextElement = driver.switchTo().activeElement();
		sourceBoxTextElement.sendKeys(locationData);

		By searchCategoryLocator = By.xpath("//div[contains(@class,'searchCategory')]");

		List<WebElement> searchCategories = wait
				.until(ExpectedConditions.numberOfElementsToBeMoreThan(searchCategoryLocator, 1));

		WebElement locationSearchResult = searchCategories.get(0);

		By locationNamelocator = By.xpath(".//div[contains(@class,\"listHeader\")]");
		List<WebElement> locationList = locationSearchResult.findElements(locationNamelocator);

		for (WebElement location : locationList) {

			if (location.getText().equalsIgnoreCase(locationData)) {
				location.click();
				break;
			}
		}

	}

}
