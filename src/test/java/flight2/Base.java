package flight2;

import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;

public class Base {

	WebDriver driver;
	ExtentReports report;

	@AfterMethod
	public void afterMethod() throws Exception {
		// load the property file
		Properties prop = new Properties();
		prop.load(new FileInputStream("src/test/resources/f2settings.property"));

		// Deletes all the cookies
		driver.manage().deleteAllCookies();

		// load home page again
		driver.navigate().to(prop.getProperty("url"));

		// refresh the page
		driver.navigate().refresh();

		// click on one way trip option
		driver.findElement(By.xpath(prop.getProperty("oneway"))).click();

		Thread.sleep(5000);
	}

	@BeforeTest
	public void beforeTest() throws Exception {

		// load the property file
		Properties prop = new Properties();
		prop.load(new FileInputStream("src/test/resources/f2settings.property"));

		// for cross browser testing take input from user
		System.out.println("Select a browser: ");
		System.out.println("1.Chrome\n2.Firefox");

		// use scanner for accepting input
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();

		switch (num) {
			case 1:
				System.setProperty(prop.getProperty("ckey"), prop.getProperty("cvalue"));
				driver = new ChromeDriver();
				break;
			case 2:
				System.setProperty(prop.getProperty("fkey"), prop.getProperty("fvalue"));
				driver = new FirefoxDriver();
				break;
			default:
				System.out.println("Invalid Input.");
				break;
		}

		// closing the scanner input stream
		sc.close();

		// maximize the window
		driver.manage().window().maximize();

		// adding implicit wait
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

		// tc_open01 - To open the website
		driver.get(prop.getProperty("url"));

		// creating extent report
		report = new ExtentReports();

		// specifying that we need html report, with name flight2.html
		report.attachReporter(new ExtentHtmlReporter("flight2.html"));

	}

	@AfterTest
	public void afterTest() {

		// quit the browser
		driver.quit();

		// //for writing report in physical memory
		report.flush();
	}

}
