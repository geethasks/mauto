package base;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

public class BaseTest {
    protected AppiumDriver driver;

    /**
     * Setup Appium driver before each test method.
     * 
     * @param testArgs test parameters from DataProvider
     */
    @BeforeMethod(alwaysRun = true)
    public void setup(Object[] testArgs) throws MalformedURLException, URISyntaxException {
        String platformName = (String) testArgs[0];       // e.g., "Android" or "iOS"
        String deviceName = (String) testArgs[1];
        String platformVersion = (String) testArgs[2];
        String udid = (String) testArgs[3];
        String appPackage = (String) testArgs[4];         // For iOS, can be bundleId
        String appActivity = (String) testArgs[5];        // For iOS, can be empty or not used

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platformName", platformName);
        caps.setCapability("deviceName", deviceName);
        caps.setCapability("platformVersion", platformVersion);
        caps.setCapability("udid", udid);
        caps.setCapability("automationName", "UiAutomator2"); // For iOS, you might want "XCUITest"
        caps.setCapability("noReset", true);
        caps.setCapability("forceAppLaunch", true);

        if (platformName.equalsIgnoreCase("Android")) {
            caps.setCapability("appPackage", appPackage);
            caps.setCapability("appActivity", appActivity);
            caps.setCapability("appWaitActivity", "*");
            driver = new AndroidDriver(new URI("http://127.0.0.1:4723/").toURL(), caps);
        } else if (platformName.equalsIgnoreCase("iOS")) {
            caps.setCapability("bundleId", appPackage);
            caps.setCapability("automationName", "XCUITest");
            driver = new IOSDriver(new URI("http://127.0.0.1:4723/").toURL(), caps);
        } else {
            throw new IllegalArgumentException("Unsupported platform: " + platformName);
        }
    }

    /**
     * Quit driver after each test method.
     */
    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
