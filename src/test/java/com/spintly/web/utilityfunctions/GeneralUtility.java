package com.spintly.web.utilityfunctions;

import com.spintly.api.services.PartnerWebPortalService;
import com.spintly.api.utilityFunctions.ApiUtility;
import com.spintly.base.core.DriverBase;
import com.spintly.base.core.DriverContext;
import com.spintly.base.core.PageBase;
import com.spintly.base.support.properties.PropertyUtility;
import com.spintly.base.utilities.ApiHelper;
import com.spintly.base.utilities.RandomDataGenerator;

import com.spintly.base.support.properties.PropertyUtility;
import com.spintly.base.utilities.ExcelHelper;
import com.spintly.base.utilities.PdfHelper;
import com.spintly.web.pages.customers.CustomerPage;
import com.spintly.web.pages.customers.CustomersListPage;
import com.spintly.web.pages.home.Home;
import com.spintly.web.pages.login.LoginPage;
import com.spintly.web.support.*;
import cucumber.api.java.es.E;
import io.appium.java_client.android.AndroidDriver;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.commons.io.comparator.LastModifiedFileComparator;

import org.joda.time.DateTime;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;

import java.awt.*;
import java.io.*;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

import com.spintly.base.core.PageBase;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;


public class GeneralUtility extends DriverBase {
    WebDriverActions actions = new WebDriverActions();
    LoginPage loginPage = new LoginPage();

    CustomerPage customerPage = new CustomerPage();
    CustomersListPage customersListPage = new CustomersListPage();
    ApiUtility apiUtility = new ApiUtility();

    PdfHelper pdfHelper = new PdfHelper();
    ExcelHelper excelHelper = new ExcelHelper();

    Home home = new Home();

    public static String DEVICE = "";

    public void partnerLogin(String phone, String password) {
//        action.sendKeys(loginPage.Textbox_PartnerPhone(), phone);
//        action.sendKeys(loginPage.Textbox_PartnerPassword(), password);
//        action.click(loginPage.Button_PartnerLogin());
//        testStepAssert.isElementDisplayed(dashboardPage.Label_Total_Active_Customers(), "I should successfully login to partner site", "Logged in successfully to partner site", "Error in login in to partner site ");
    }

    public void navigateMenu(String menu) throws InterruptedException {
        switch (menu) {
        }
//        dashboardPage.waitForPageLoad();
    }


    public void loginAndAllowNotifications() {
        String device = System.getProperty("test.Device");
        String phone = PropertyUtility.getDataProperties("admin.username");
        String password = PropertyUtility.getDataProperties("admin.password");

        switch (device) {
            case "device1":
                break;
            case "device2":
                break;
            case "device3":
                if (actions.isElementPresent(loginPage.allowNotificationsLogindevice(true))) {
                    actions.clickInit(loginPage.allowNotificationsLogindevice(false));
                }
                break;
            case "device4":
                break;
        }

        actions.clickInit(loginPage.phoneInput(false));
        actions.adbSendText(phone, true);

        actions.clickInit(loginPage.passwordInput(false));
        actions.sendKeysInit(loginPage.passwordInput(false), password);
        actions.backButtonPhone();

        actions.clickInit(loginPage.signInButton(false));
    }

    public void goToCustomerPage() throws InterruptedException {
        actions.clickInit(home.hamburgerMenu(false));
        actions.clickInit(home.customers(false));

        String customerName = PropertyUtility.getDataProperties("organization.title");
        actions.clickInit(customersListPage.customerSearchBar(false));
        actions.sendKeysInit(customersListPage.customerSearchBar(false), customerName);
        actions.backButtonPhone();

        actions.clickInit(customersListPage.customerLink(customerName, false));
        actions.clickInit(customersListPage.customerLink(customerName, false));
    }

    public void detachDevice(String serialNumber) {
        actions.clickInit(customerPage.detachedDeviceTab(false));
        actions.sendKeysInit(customerPage.detachedDeviceSearch(false), serialNumber);
        actions.clickInit(customerPage.factoryResetButton(false));
        actions.clickInit(customerPage.resetNowButton(false));
        LocalDateTime now = LocalDateTime.now();
        while (actions.isElementPresent(customerPage.resettingDeviceMessage(true))) {
            LocalDateTime now2 = LocalDateTime.now();
            if(now2.equals(now.plusMinutes(1))){ break;}
        }
        try{Thread.sleep(2000);}catch (Exception e){};
    }

    public void returnToMainPage() {
        int count = 0;

        String serialNumber = PropertyUtility.getDataProperties("main.device.serial.no");
        boolean detach = ((String) variableContext.getScenarioContext("DETACHNEWDEVICE")).equalsIgnoreCase("TRUE");
        boolean addDeleted = ((String) variableContext.getScenarioContext("ADDDELETEDDEVICE")).equalsIgnoreCase("TRUE");
        boolean checkDetach = false;
        boolean isAtCustomerPage = false;

        while (!(actions.isElementPresent(home.DashboardLabel(true)))) {
            // Detaching device if required, before proceeding to the Home page.
            if(detach){
                if(actions.isElementPresent(customerPage.detachedDeviceTab(true))){
                    new PartnerWebPortalService().deleteAccessPoint(serialNumber);
                    try{Thread.sleep(5000);}catch (Exception e){}
                    detachDevice(serialNumber);
                    detach = false;
                    variableContext.setScenarioContext("DELTENEWDEVICE","FALSE");
                    variableContext.setScenarioContext("DETACHNEWDEVICE","FALSE");
                }
            }

            // Adding back the detached device
//            if(addDeleted){
//                if(actions.isElementPresent(customerPage.detachedDeviceTab(true))){
//                    new PartnerWebPortalService().addAccessPoint(serialNumber);
//                    String accessPointName = "AppAuto3";
//                    actions.click(customerPage.accessPointsTab(false));
//                    actions.sendKeysInit(customerPage.detachedDeviceSearch(false),accessPointName);
//                    actions.clickInit(customerPage.accessPointName(accessPointName,false));
//                    actions.clickInit(customerPage.configureButton(false));
//                    if(actions.isElementPresent(loginPage.allowNotificationsLogindevice(true))){actions.clickInit(loginPage.allowNotificationsLogindevice(false));}
//                    LocalDateTime now = LocalDateTime.now();
//                    while (actions.isElementPresent(customerPage.configuringDeviceMessage(true))){
//                        LocalDateTime now2 = LocalDateTime.now();
//                        if(now2.equals(now.plusMinutes(1))){ break;}
//                    }
//                    addDeleted = false;
//                }
//            }

            actions.backButtonPhone();

            if (count > 15) {break;}
            count++;
        }
    }


    public void setLoginData() {
        variableContext.setScenarioContext("ORGID", PropertyUtility.getDataProperties("door.state.org.id"));
        System.out.println("Inside Set Login Data");
        String device = System.getProperty("test.Device").toString();
        String env = System.getProperty("test.Environment").toString();

        System.out.println("Device Used is : " + device);
        System.out.println("Environment Used is : " + env);

        if (env.equalsIgnoreCase("prod")) {
            System.out.println("Inside Environment Check : " + env);
            switch (device) {
                case "device2":
                    PropertyUtility.setLoginData("admin.name", "Sidharth Two");
                    PropertyUtility.setLoginData("admin.username", "7428723247");
                    PropertyUtility.setLoginData("admin.password", "partner2332");
                    PropertyUtility.setLoginData("main.user.id", "277406916");
                    PropertyUtility.setLoginData("delete.y.coordinate", "1860");
                    PropertyUtility.setLoginData("deactivate.y.coordinate", "1988");
                    PropertyUtility.setLoginData("edit.y.coordinate", "2108");
                    break;
                case "device3":
                    PropertyUtility.setLoginData("admin.name", "Sidharth Three");
                    PropertyUtility.setLoginData("admin.username", "7428731268");
                    PropertyUtility.setLoginData("admin.password", "partner3617");
                    PropertyUtility.setLoginData("main.user.id", "277406917");
                    PropertyUtility.setLoginData("delete.y.coordinate", "1714");
                    PropertyUtility.setLoginData("deactivate.y.coordinate", "1839");
                    PropertyUtility.setLoginData("edit.y.coordinate", "1980");
                    break;
                case "device4":
                    PropertyUtility.setLoginData("admin.name", "Sidharth Four");
                    PropertyUtility.setLoginData("admin.username", "7428730894");
                    PropertyUtility.setLoginData("admin.password", "partner9412");
                    PropertyUtility.setLoginData("main.user.id", "277406918");
                    PropertyUtility.setLoginData("delete.y.coordinate", "1665");
                    PropertyUtility.setLoginData("deactivate.y.coordinate", "1825");
                    PropertyUtility.setLoginData("edit.y.coordinate", "1957");
                    break;
            }
        } else if (env.equalsIgnoreCase("test")) {
            System.out.println("Inside Environment Check : " + env);
            switch (device) {
                case "device2":
                    PropertyUtility.setLoginData("admin.name", "Sidharth Two");
                    PropertyUtility.setLoginData("admin.username", "9878454565");
                    PropertyUtility.setLoginData("admin.password", "partner3699");
//                    PropertyUtility.setLoginData("door.state.door.id", "5249");
                    PropertyUtility.setLoginData("door.state.door.name", "AppAuto2");
                    PropertyUtility.setLoginData("clickToAccess.door.name", "AppAuto2");
                    PropertyUtility.setLoginData("delete.y.coordinate", "1860");
                    PropertyUtility.setLoginData("deactivate.y.coordinate", "1988");
                    PropertyUtility.setLoginData("edit.y.coordinate", "2108");
                    break;
                case "device3":
                    PropertyUtility.setLoginData("admin.name", "Sidharth Three");
                    PropertyUtility.setLoginData("admin.username", "9845656454");
                    PropertyUtility.setLoginData("admin.password", "partner3474");
//                    PropertyUtility.setLoginData("door.state.door.id", "5248");
                    PropertyUtility.setLoginData("door.state.door.name", "AppAuto3");
                    PropertyUtility.setLoginData("clickToAccess.door.name", "AppAuto3");
                    PropertyUtility.setLoginData("delete.y.coordinate", "1714");
                    PropertyUtility.setLoginData("deactivate.y.coordinate", "1839");
                    PropertyUtility.setLoginData("edit.y.coordinate", "1980");
                    break;
                case "device4":
                    PropertyUtility.setLoginData("admin.name", "Sidharth Four");
                    PropertyUtility.setLoginData("admin.username", "9484515623");
                    PropertyUtility.setLoginData("admin.password", "partner5681");
//                    PropertyUtility.setLoginData("door.state.door.id", "5291");
                    PropertyUtility.setLoginData("door.state.door.name", "AppAuto4");
                    PropertyUtility.setLoginData("clickToAccess.door.name", "AppAuto4");
                    PropertyUtility.setLoginData("delete.y.coordinate", "1665");
                    PropertyUtility.setLoginData("deactivate.y.coordinate", "1825");
                    PropertyUtility.setLoginData("edit.y.coordinate", "1957");
                    break;
            }
        } else if (env.equalsIgnoreCase("us-cloud-prod")) {
            System.out.println("Inside Environment Check : " + env);
            switch (device) {
                case "device2":
                    PropertyUtility.setLoginData("admin.name", "Sidharth Two");
                    PropertyUtility.setLoginData("admin.username", "7428723482");
                    break;
                case "device3":
                    PropertyUtility.setLoginData("admin.name", "Sidharth Three");
                    PropertyUtility.setLoginData("admin.username", "9654842136");
                    break;
                case "device4":
                    PropertyUtility.setLoginData("admin.name", "Sidharth Four");
                    PropertyUtility.setLoginData("admin.username", "7449646566");
                    break;
            }
        }

    }

}


