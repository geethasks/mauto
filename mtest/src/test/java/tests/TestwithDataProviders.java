package tests;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class TestwithDataProviders {
    private AppiumDriver driver;

    @DataProvider(name="deviceData")
    public Object[][] deviceData(){
        return new Object[][]{

        //    {"Android","Pixel_2_API_27","","emulator-5554", "com.android.calculator2", "com.android.calculator2.Calculator","com.android.calculator2:id/digit_1", "com.android.calculator2:id/digit_2", "com.android.calculator2:id/op_add",
        //     "com.android.calculator2:id/eq", "com.android.calculator2:id/result"},
              
            {"Android", "Galaxy_S21+5G", "12", "RFCR70KJQ7W", "com.sec.android.app.popupcalculator", "com.sec.android.app.popupcalculator.Calculator",
             "com.sec.android.app.popupcalculator:id/calc_keypad_btn_01", "com.sec.android.app.popupcalculator:id/calc_keypad_btn_02", 
             "com.sec.android.app.popupcalculator:id/calc_keypad_btn_add", "com.sec.android.app.popupcalculator:id/calc_keypad_btn_equal", 
             "com.sec.android.app.popupcalculator:id/calc_edt_formula"}
        };
    }
    

    @Test(dataProvider = "deviceData")
    public void testDevice(String platformName, String deviceName, String platformVersion, String udid, 
                           String appPackage, String appActivity, String buttonOne, String buttonTwo, 
                           String addButton, String equalButton, String resultField) 
            throws MalformedURLException, URISyntaxException {
        
        System.out.println("Running test on " + deviceName + " with " + platformName);

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", platformName);
        caps.setCapability("deviceName", deviceName);
        caps.setCapability("platformVersion", platformVersion);
        caps.setCapability("udid", udid);
        caps.setCapability("automationName", "UiAutomator2");
        caps.setCapability("appPackage", appPackage);
        caps.setCapability("appActivity", appActivity);
        caps.setCapability("noReset", true);
        caps.setCapability("appWaitActivity", "*");

        URL url = new URI("http://127.0.0.1:4723/").toURL();
        driver = new AndroidDriver(url, caps);

        // Perform test actions with dynamic element locators
        performCalculation(buttonOne, buttonTwo, addButton, equalButton, resultField);

        driver.quit();
    }


    private void performCalculation(String buttonOne, String buttonTwo, String addButton, String equalButton, String resultField) {
        WebElement oneButton = driver.findElement(By.id(buttonOne));
        WebElement twoButton = driver.findElement(By.id(buttonTwo));
        WebElement addOperator = driver.findElement(By.id(addButton));
        WebElement equalsButton = driver.findElement(By.id(equalButton));
        WebElement resultArea = driver.findElement(By.id(resultField));

        oneButton.click();
        addOperator.click();
        twoButton.click();
        equalsButton.click();

        System.out.println("Calculated Result: " + resultArea.getText());
    }
}


