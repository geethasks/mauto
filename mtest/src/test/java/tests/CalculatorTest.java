package tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.BaseTest;
import pages.CalculatorPage;

public class CalculatorTest extends BaseTest {

    @DataProvider(name = "deviceData")
    public Object[][] deviceData() {
        return new Object[][]{
            // Android emulator example
       //     {"Android", "Pixel_2_API_27", "", "emulator-5554", "com.android.calculator2", "com.android.calculator2.Calculator", "emulator"},
            // Android real device example
            {"Android", "Galaxy_S21+5G", "", "RFCR70KJQ7W", "com.sec.android.app.popupcalculator", "com.sec.android.app.popupcalculator.Calculator", "real"},
            // iOS example (adjust as needed)
            // {"iOS", "iPhone 13", "15.4", "device_udid_here", "com.apple.calculator", "", "real"}
        };
    }

    @Test(dataProvider = "deviceData")
    public void testAddition(String platformName, String deviceName, String platformVersion, String udid,
                             String appPackage, String appActivity, String deviceType) {

        CalculatorPage calculatorPage = new CalculatorPage(driver, platformName, deviceType);
        calculatorPage.performAddition();
        String result = calculatorPage.getResult();
        System.out.println("Calculated Result on " + deviceName + ": " + result);
    }
}
