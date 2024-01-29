package org.example.Pages;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.cucumber.datatable.DataTable;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;

public class LoginPage {
	
	public static WebDriver driver =null;
	

	public void launchbrowser() {

//		System.setProperty("webdriver.chrome.driver",  System.getProperty("user.dir")+"/drivers/chromedriver.exe");
//		// Instantiate a ChromeDriver class.
//
//		 driver = new ChromeDriver();
//
//		// Maximize the browser
//		driver.manage().window().maximize();
//		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
//		driver.get("https://www.adp.com/");


		/// new code



		System.out.println("came into the function888");
		ChromeOptions options =  new ChromeOptions();
		// options.setExperimentalOption("prefs", {"profile.managed_default_content_settings.images": 2});
		options.addArguments("--headless");
		options.addArguments("--no-sandbox");
		options.addArguments("--disable-gpu");
		options.addArguments("--single-process");
		options.addArguments("--remote-debugging-port=9222");
		options.addArguments("--window-size=1280,720");
		options.addArguments("disable-infobars");

		options.addArguments("--disable-dev-shm-usage");
		options.addArguments("--disable-dev-tools");
		options.addArguments("--no-zygote");
		options.addArguments("--user-data-dir=/tmp/chrome-user-data");

        /*String br = System.getenv("BROWSER").toLowerCase();
        String br_version =  System.getenv("BROWSER_VERSION").toLowerCase();
        String driver_version =  System.getenv("DRIVER_VERSION").toLowerCase();
        System.out.println("driver_version:"+driver_version);
        System.setProperty("webdriver.chrome.driver", "/opt/chromedriver/"+driver_version+"/chromedriver");*/
		System.setProperty("webdriver.chrome.driver", "/opt/chromedriver/88.0.4324.96/chromedriver");
		System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY,"/tmp/chromedriver.log");
		options.setBinary("/opt/chrome/88.0.4324.150/chrome");



		 driver = new ChromeDriver(options);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS) ;




		System.out.println("driver loaded");

		driver.get("https://www.adp.com/");
		try{
			Thread.sleep(5000);
		}catch(Exception e){}

		String title = driver.getTitle();

		System.out.println("title is: "+title);




	}


	private static void uploadScreenshotsIntoS3(File file,String fileName) throws FileNotFoundException {
		try {
			Regions region = Regions.fromName("ap-south-1");
			AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(region).build();

			String bucket = "uiscreenshots";
			// Put Object
			s3.putObject(bucket, fileName, file);
		}catch(Exception excp){
			System.out.println("err:"+excp.toString());
			excp.printStackTrace();
		}finally {
			file.delete();
		}
	}
	public static void enterDetails(DataTable dataTable)  {
		
		System.out.println(dataTable.asLists().get(1).get(1));
		driver.findElement(By.xpath("//input[@data-sc-field-name='Full_Name']")).sendKeys(dataTable.asLists().get(0).get(1));
		driver.findElement(By.xpath("//input[@data-sc-field-name='Email']")).sendKeys(dataTable.asLists().get(1).get(1));
		driver.findElement(By.xpath("//input[@data-sc-field-name='Phone']")).sendKeys("980765443");
	
//call screenshot here
		try{
			System.out.println("screenshot started");

			uploadScreenshotsIntoS3(takeSnapShot(driver),"test.png");
			System.out.println("screenshot ended");
		}catch(Exception e){
			e.printStackTrace();
		}
	}


	public static File takeSnapShot(WebDriver webdriver) throws Exception{

		//Convert web driver object to TakeScreenshot

		TakesScreenshot scrShot =((TakesScreenshot)webdriver);

		//Call getScreenshotAs method to create image file

		return scrShot.getScreenshotAs(OutputType.FILE);


	}


}
