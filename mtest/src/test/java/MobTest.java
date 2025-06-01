
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class MobTest {

    private AppiumDriver driver;

    @BeforeClass
    public void setup() throws MalformedURLException, URISyntaxException {

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", "Android");
        // caps.setCapability("deviceName", "Galaxy_S21+5G");  // Use `adb devices` output if needed
        caps.setCapability("deviceName", "Pixel_2_API_27");  // Use `adb devices` output if needed
        caps.setCapability("automationName", "UiAutomator2");
        // caps.setCapability("app", "/path/to/app.apk"); // Provide your actual APK path

        //driver = new AppiumDriver<>(new URL("http://localhost:4723/wd/hub"), caps);
        caps.setCapability("appPackage", "com.sec.android.app.popupcalculator");
        caps.setCapability("appActivity", "com.sec.android.app.popupcalculator.Calculator");

        // Corrected Appium Server URL
        URI uri = new URI("http://127.0.0.1:4723/");
        URL url = uri.toURL();

        // Assign driver to static instance
        driver = new AndroidDriver(url, caps);
        // System.out.println("Calculator app launched successfully!");

    }

    @Test
    public void testAssertions() throws InterruptedException {
        //driver.get("https://www.google.com");
        // System.out.println("Title: " + driver.getTitle());
        // Example: Find the '1' button
        //System.out.println("Page source   :" + driver.getPageSource());

        WebElement oneButton = driver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_01"));
        WebElement twoButton = driver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_02"));

        // Example: Click the '1' button
        oneButton.click();
        driver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_add")).click();
        twoButton.click();

        // perform add
        // Before clicking equal sign
        WebElement tempArea = driver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_tv_result"));
        System.out.println("TempArea  :" + tempArea.getText());
        WebElement result = driver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_edt_formula"));
        System.out.println("result before :" + result.getText());
        driver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_equal")).click();

        System.out.println("result after :" + result.getText());

        // You'll need to add more interactions and assertions here
        Thread.sleep(2000); // Just to see the action, avoid in real tests

    }

    
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
    // private static AppiumDriver driver;

    // public static void main(String[] args) throws InterruptedException, Exception {
    //     try {
    //         openCalculator();
    //         // Example: Find the '1' button
    //       //  WebElement oneButton = driver.findElement(By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_1"));
    //      //   System.out.println("Found the '1' button: " + oneButton.isDisplayed());
    //         // Example: Click the '1' button
    //      //   oneButton.click();
    //         // You'll need to add more interactions and assertions here
    //         Thread.sleep(2000); // Just to see the action, avoid in real tests
    //     } catch (NullPointerException e) {
    //         e.printStackTrace(); // Print the exception details to see what went wrong
    //     } finally {
    //         if (driver != null) {
    //             driver.quit(); // Close the app and session
    //         }
    //     }
    // }
    // public static void openCalculator() throws Exception {
    //     DesiredCapabilities cap = new DesiredCapabilities();
    //     cap.setCapability("deviceName", "Galaxy S21+5G");
    //     cap.setCapability("udid", "RFCR70KJQ7W");
    //     cap.setCapability("platformName", "Android");
    //     cap.setCapability("platformVersion", "11");
    //    // cap.setCapability("automationName", "UiAutomator2");
    //     cap.setCapability("appPackage", "com.sec.android.app.popupcalculator");
    //     cap.setCapability("appActivity", "com.sec.android.app.popupcalculator.Calculator");
    //     // Corrected Appium Server URL
    //     URI uri = new URI("http://127.0.0.1:4723/");
    //     URL url = uri.toURL();
    //     // Assign driver to static instance
    //     driver = new AndroidDriver(url, cap);
    //     System.out.println("Calculator app launched successfully!");
    //     // Fix getTitle() issue
    // }
}
