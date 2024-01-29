package org.example;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.runtime.Context;

import com.amazonaws.services.lambda.runtime.RequestHandler;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.chrome.ChromeDriverService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;


public class LambdachromeHandler implements RequestHandler<Object, String> {







    @Override

    public String handleRequest(Object input, Context context) {

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


        System.setProperty("webdriver.chrome.driver", "/opt/chromedriver/88.0.4324.96/chromedriver");
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY,"/tmp/chromedriver.log");
        options.setBinary("/opt/chrome/88.0.4324.150/chrome");



        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS) ;




        System.out.println("driver loaded");

        driver.get("https://smartcompliance.adp.com/");
        try{
            Thread.sleep(5000);
        }catch(Exception e){}

        String title = driver.getTitle();

        System.out.println("title is: "+title);

        driver.findElement(By.id("login-form_username")).sendKeys("SampleCode");
        try{
            Thread.sleep(1000);
        }catch(Exception e){}
        System.out.println("entered values is: "+driver.findElement(By.id("login-form_username")).getAttribute("value"));

        try{
            System.out.println("screenshot started");

            uploadScreenshotsIntoS3(takeSnapShot(driver),"test.png");
            System.out.println("screenshot ended");
        }catch(Exception e){}

        driver.quit();



        return "Page title is: " + title;

    }


    private void uploadScreenshotsIntoS3(File file,String fileName) throws FileNotFoundException {
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
    public static File takeSnapShot(WebDriver webdriver) throws Exception{

        //Convert web driver object to TakeScreenshot

        TakesScreenshot scrShot =((TakesScreenshot)webdriver);

        //Call getScreenshotAs method to create image file

        return scrShot.getScreenshotAs(OutputType.FILE);


    }

}