package com.spintly.web.stepdefinitions.StartStop;

import com.spintly.SuiteSetup;
import com.spintly.base.core.DriverBase;
import com.spintly.base.core.DriverContext;
import com.spintly.base.support.properties.PropertyUtility;
import com.spintly.base.utilities.ApiHelper;
import com.spintly.web.support.WebDriverActions;
import cucumber.api.java.en.Given;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;

public class StartStopSteps extends DriverBase {
    @Given("I start the app")
    public void i_start_the_app() throws Throwable {
        ApiHelper apiHelper = new ApiHelper();
        WebDriverActions action = new WebDriverActions();

        SuiteSetup.getObject().getObject().useDriverInstance("ORIGINAL");

        action.toggleDeviceWifi("enable");
        action.setBluetooth("enable");
        apiHelper.checkInternetConnection();
    }

    @Given("I stop the app")
    public void i_stop_the_app() throws Throwable {
        AppiumDriver appiumDriver = DriverContext.getObject().getDriver();
        System.out.println("closeApp");
        appiumDriver.quit();
    }
}
