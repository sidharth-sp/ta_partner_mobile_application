package com.spintly.web.stepdefinitions.login;

import com.spintly.api.services.AccessManagementService;
import com.spintly.api.services.AppService;
import com.spintly.api.utilityFunctions.ApiUtility;
import com.spintly.base.core.DriverBase;
import com.spintly.base.core.DriverContext;
import com.spintly.base.managers.ResultManager;
import com.spintly.base.support.properties.PropertyUtility;
import com.spintly.base.utilities.ApiHelper;
import com.spintly.base.utilities.RandomDataGenerator;
import com.spintly.web.pages.customers.CustomerPage;
import com.spintly.web.pages.home.Home;
import com.spintly.web.pages.login.LoginPage;
import com.spintly.web.support.WebDriverActions;
import com.spintly.web.utilityfunctions.GeneralUtility;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.cucumber.datatable.DataTable;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.WebElement;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoginSteps extends DriverBase {
    LoginPage loginPage = new LoginPage();
    Home home = new Home();

    CustomerPage customerPage = new CustomerPage();
    WebDriverActions actions = new WebDriverActions();

    @Given("I enter the username")
    public void i_enter_the_username() throws Throwable {
            String device = System.getProperty("test.Device");
            switch (device){
                case "device1":
                    break;
                case "device2":
                    break;
                case "device3":
                    if (actions.isElementPresent(loginPage.allowNotificationsLogindevice(true))) {actions.click(loginPage.allowNotificationsLogindevice(false));}
                    break;
                case "device4":
                    break;
            }

        String phone = PropertyUtility.getDataProperties("admin.username");

        if(System.getProperty("Parallel").equalsIgnoreCase("true")){
            if(System.getProperty("username") != null){
                System.out.println("Inside Login Username");
                phone = System.getProperty("username");
            }
        }
        actions.click(loginPage.phoneInput(false));
        actions.clear(loginPage.phoneInput(false));

        actions.adbSendText(phone,true);
    }

    @Given("I enter the password")
    public void i_enter_the_password() throws Throwable {
        String password = PropertyUtility.getDataProperties("admin.password");

        if(System.getProperty("Parallel").equalsIgnoreCase("true")){
            if(System.getProperty("password") != null){
                System.out.println("Inside Login Password");
                password = System.getProperty("password");
            }
        }

        actions.click(loginPage.passwordInput(false));
        actions.sendKeys(loginPage.passwordInput(false), password);
        actions.backButtonPhone();
    }

    @Given("I return to the Homepage")
    public void i_return_to_the_homepage() throws Throwable {
        new GeneralUtility().returnToMainPage();
    }

    @Given("I click on {string} button")
    public void i_enter_the_password(String button) throws Throwable {
        switch (button) {
            case "Detached Devices Tab":
                actions.click(customerPage.detachedDeviceTab(false));
                break;
            case "Phone - Back":
                actions.backButtonPhone();
                break;
            case "configure":
                actions.click(customerPage.configureButton(false));
                if(actions.isElementPresent(loginPage.allowNotificationsLogindevice(true))){actions.click(loginPage.allowNotificationsLogindevice(false));}
                LocalDateTime now = LocalDateTime.now();
                while (actions.isElementPresent(customerPage.configuringDeviceMessage(true))){
                    LocalDateTime now2 = LocalDateTime.now();
                    if(now2.equals(now.plusMinutes(1))){ break;}
                }
                break;
            case "Access Points tab":
                actions.click(customerPage.accessPointsTab(false));
                break;
            case "Customers":
                actions.click(home.customers(false));
                break;
            case "hamburger-menu":
                actions.click(home.hamburgerMenu(false));
                break;
            case "login":
                actions.click(loginPage.signInButton(false));
                break;
        }
    }

    @Given("I should login to the Partner App")
    public void i_should_login_to_the_partner_app() throws Throwable {
        boolean pass = actions.isElementPresent(home.DashboardLabel(false));
        testStepAssert.isTrue(pass,"User Should Be Logged Into The Partner App","User Is Logged Into The Partner App","User Is Not Logged Into The Partner App");
    }

}
