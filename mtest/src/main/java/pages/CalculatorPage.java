package pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;

public class CalculatorPage {

    @SuppressWarnings("FieldMayBeFinal")
    private AppiumDriver driver;
    private WebDriverWait wait; // Declare WebDriverWait here

    private By btnOneLocator;
    private By btnTwoLocator;
    private By btnAddLocator;
    private By btnEqualLocator;
    private By resultFieldLocator;

    /**
     * Initializes locators based on platform and device type.
     *
     * @param driver Appium driver instance
     * @param platformName "Android" or "iOS"
     * @param deviceType "emulator" or "real"
     */
    public CalculatorPage(AppiumDriver driver, String platformName, String deviceType) {
        System.out.println("DEBUG: Entering CalculatorPage constructor with platform: " + platformName + ", deviceType: " + deviceType);

        if (driver == null) {
            System.err.println("CRITICAL ERROR: AppiumDriver passed to CalculatorPage constructor is NULL!");
            throw new IllegalArgumentException("AppiumDriver cannot be null in CalculatorPage constructor.");
        }
        this.driver = driver;
        System.out.println("DEBUG: AppiumDriver assigned to CalculatorPage instance.");

        try {
            this.wait = new WebDriverWait(driver, Duration.ofSeconds(30)); // Set the wait duration here
            System.out.println("DEBUG: WebDriverWait initialized successfully with 30-second timeout.");
        } catch (Exception e) {
            System.err.println("CRITICAL ERROR: Failed to initialize WebDriverWait: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize WebDriverWait in CalculatorPage.", e);
        }
        if ("Android".equalsIgnoreCase(platformName)) {
            if ("emulator".equalsIgnoreCase(deviceType)) {
                btnOneLocator = By.id("com.android.calculator2:id/digit_1");
                btnTwoLocator = By.id("com.android.calculator2:id/digit_2");
                btnAddLocator = By.id("com.android.calculator2:id/op_add");
                btnEqualLocator = By.id("com.android.calculator2:id/eq");
                resultFieldLocator = By.id("com.android.calculator2:id/result");
            } else {
                btnOneLocator = By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_01");
                btnTwoLocator = By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_02");
                btnAddLocator = By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_add");
                btnEqualLocator = By.id("com.sec.android.app.popupcalculator:id/calc_keypad_btn_equal");
                resultFieldLocator = By.id("com.sec.android.app.popupcalculator:id/calc_edt_formula");
            }
        } else if ("iOS".equalsIgnoreCase(platformName)) {
            btnOneLocator = AppiumBy.accessibilityId("1");
            btnTwoLocator = AppiumBy.accessibilityId("2");
            btnAddLocator = AppiumBy.accessibilityId("add");
            btnEqualLocator = AppiumBy.accessibilityId("equals");
            resultFieldLocator = AppiumBy.accessibilityId("result");
        } else {
            throw new IllegalArgumentException("Unsupported platform: " + platformName);
        }
    }

    public void performAddition() {
        // driver.findElement(btnOneLocator).click();
        // driver.findElement(btnAddLocator).click();
        // driver.findElement(btnTwoLocator).click();
        // driver.findElement(btnEqualLocator).click();
        System.out.println("Attempting to perform addition...");

        // Wait for '1' button and then click
        wait.until(ExpectedConditions.elementToBeClickable(btnOneLocator)).click();
        System.out.println("Clicked '1'");

        // Wait for '+' button and then click
        wait.until(ExpectedConditions.elementToBeClickable(btnAddLocator)).click();
        System.out.println("Clicked '+'");

        // Wait for '2' button and then click
        wait.until(ExpectedConditions.elementToBeClickable(btnTwoLocator)).click();
        System.out.println("Clicked '2'");

        // Wait for '=' button and then click
        wait.until(ExpectedConditions.elementToBeClickable(btnEqualLocator)).click();
        System.out.println("Clicked '='");

    }

    public String getResult() {
        System.out.println("DEBUG: Entering getResult()...");
        if (wait == null) {
            System.err.println("CRITICAL ERROR: 'wait' object is NULL in getResult() BEFORE any operation!");
            throw new NullPointerException("'wait' object is null in getResult() method.");
        }
        try {
            System.out.println("DEBUG: Waiting for resultFieldLocator: " + resultFieldLocator + " to be visible.");
            WebElement resultElement = wait.until(ExpectedConditions.visibilityOfElementLocated(resultFieldLocator));
            String result = resultElement.getText();
            System.out.println("DEBUG: Retrieved result: " + result);
            return result;
        } catch (Exception e) {
            System.err.println("ERROR: An error occurred while getting the result: " + e.getMessage());
            e.printStackTrace();
            throw e; // Re-throw to propagate the original error
        }
    }
}
