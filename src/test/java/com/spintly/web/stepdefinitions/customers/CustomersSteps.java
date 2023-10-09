package com.spintly.web.stepdefinitions.customers;

import com.spintly.api.services.PartnerWebPortalService;
import com.spintly.base.core.DriverContext;
import com.spintly.base.support.properties.PropertyUtility;
import com.spintly.web.pages.customers.CustomerPage;
import com.spintly.web.pages.customers.CustomersListPage;
import com.spintly.web.pages.home.Home;
import com.spintly.web.pages.login.LoginPage;
import com.spintly.web.support.WebDriverActions;
import com.spintly.web.utilityfunctions.GeneralUtility;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebElement;

import java.time.LocalDateTime;

public class CustomersSteps extends DriverContext {
    Home home = new Home();
    WebDriverActions actions = new WebDriverActions();
    LoginPage loginPage = new LoginPage();

    CustomersListPage customersListPage = new CustomersListPage();

    CustomerPage customerPage = new CustomerPage();
    PartnerWebPortalService partnerWebPortalService = new PartnerWebPortalService();
    GeneralUtility generalUtility = new GeneralUtility();

    @Given("I search and select the customer")
    public void i_search_and_select_the_customer() throws Throwable {
        String customerName = PropertyUtility.getDataProperties("organization.title");

        if(System.getProperty("Parallel").equalsIgnoreCase("true")){
            if(System.getProperty("organisationName") != null){
                customerName = System.getProperty("organisationName");
            }
        }

        actions.click(customersListPage.customerSearchBar(false));
        actions.sendKeys(customersListPage.customerSearchBar(false), customerName);
        actions.backButtonPhone();

        actions.click(customersListPage.customerLink(customerName, false));
        actions.click(customersListPage.customerLink(customerName, false));
    }

    @When("I set the environment for adding and configuring the device")
    public void i_set_the_environment_for_adding_and_configuring_the_device() {
        String serialNumber = PropertyUtility.getDataProperties("main.device.serial.no");
        boolean deviceExists = false;
        String deviceName = "AppAuto4";
        deviceExists = partnerWebPortalService.namesOfAccessPointsInOrg().contains(deviceName);
        boolean resetDevice = false;


        if (deviceExists) {
            variableContext.setScenarioContext("NEWACCESSPOINTID", partnerWebPortalService.getIdOfDevice(deviceName));
            String deviceStatus = partnerWebPortalService.getStateOfDevice(deviceName);
            partnerWebPortalService.deleteAccessPoint(serialNumber);

            switch (deviceStatus) {
                case "configure_pending":
                    System.out.println("DEVICE WAS IN CONFIGURE PENDING STATE");
                    //Do Nothing
                    break;
                case "in_sync":
                    System.out.println("DEVICE WAS IN_SYNC STATE");
                    resetDevice = true;
                    break;
            }
        } else {
            boolean isDeviceDetached = partnerWebPortalService.getDetachedDevices().contains(serialNumber);
            if (isDeviceDetached) {
                resetDevice = true;
            }
        }


        if (resetDevice) {
            System.out.println("DEVICE IS BEING RESET");
            actions.click(home.hamburgerMenu(false));
            actions.click(home.customers(false));
            String customerName = PropertyUtility.getDataProperties("organization.title");
            actions.click(customersListPage.customerSearchBar(false));
            actions.sendKeys(customersListPage.customerSearchBar(false), customerName);
            actions.backButtonPhone();
            actions.click(customersListPage.customerLink(customerName, false));
            actions.click(customersListPage.customerLink(customerName, false));
            actions.click(customerPage.detachedDeviceTab(false));

            actions.sendKeys(customerPage.detachedDeviceSearch(false), serialNumber);
            actions.click(customerPage.factoryResetButton(false));
            actions.click(customerPage.resetNowButton(false));
            if (actions.isElementPresent(loginPage.allowNotificationsLogindevice(true))) {
                actions.clickInit(loginPage.allowNotificationsLogindevice(false));
            }
            LocalDateTime now = LocalDateTime.now();
            while (actions.isElementPresent(customerPage.resettingDeviceMessage(true))) {
                LocalDateTime now2 = LocalDateTime.now();
                if (now2.equals(now.plusMinutes(1))) {
                    break;
                }
            }
        }

        int count = 0;
        while (!(actions.isElementPresent(home.DashboardLabel(true)))) {
            actions.backButtonPhone();
            count++;
            if (count > 10) {
                break;
            }
        }
    }

    @When("I call the show reset button API")
    public void i_call_the_show_reset_button_api() {
        String serialNumber = PropertyUtility.getDataProperties("main.device.serial.no");

        if(System.getProperty("Parallel").equalsIgnoreCase("true")){
            if(System.getProperty("serialNumber") != null){
                serialNumber = System.getProperty("serialNumber");
            }
        }

        partnerWebPortalService.showResetDevice(serialNumber);
    }


    @When("I add an access point through the API")
    public void i_add_an_access_point_through_the_api() {
        String serialNumber = PropertyUtility.getDataProperties("main.device.serial.no");

        if(System.getProperty("Parallel").equalsIgnoreCase("true")){
            if(System.getProperty("serialNumber") != null){
                serialNumber = System.getProperty("serialNumber");
            }
        }

        partnerWebPortalService.addAccessPoint(serialNumber);

//        variableContext.setScenarioContext("DELETENEWDEVICE","TRUE");
    }

    @When("I scroll and select the access point to be configured")
    public void i_scroll_and_select_the_access_point_to_be_configured() {
        boolean stop = false;
        String accessPointName = "AppAuto2";

        if(System.getProperty("Parallel").equalsIgnoreCase("true")){
            if(System.getProperty("accessPointName") != null){
                accessPointName = System.getProperty("accessPointName");
            }
        }

        actions.sendKeys(customerPage.detachedDeviceSearch(false), accessPointName);
        actions.click(customerPage.accessPointName(accessPointName, false));
    }

    @When("I scroll and select the access point to be reset")
    public void i_scroll_and_select_the_access_point_to_be_reset() {
        boolean stop = false;
        String accessPointName = "AppAuto2";

        if(System.getProperty("Parallel").equalsIgnoreCase("true")){
            if(System.getProperty("accessPointName") != null){
                accessPointName = System.getProperty("accessPointName");
            }
        }

        actions.sendKeys(customerPage.detachedDeviceSearch(false), accessPointName);
        actions.click(customerPage.accessPointName(accessPointName, false));
    }

    @Given("I click on the reset device button")
    public void i_click_on_the_reset_device_button() throws InterruptedException{
        variableContext.setScenarioContext("ADDDELETEDDEVICE", "TRUE");
//        String serialNumber = PropertyUtility.getDataProperties("main.device.serial.no");
//
//        if(System.getProperty("Parallel").equalsIgnoreCase("true")){
//            if(System.getProperty("serialNumber") != null){
//                serialNumber = System.getProperty("serialNumber");
//            }
//        }

        actions.click(customerPage.resetButton(false));
        if(actions.isElementPresent(loginPage.allowNotificationsLogindevice(true))){actions.click(loginPage.allowNotificationsLogindevice(false));}


//        actions.sendKeys(customerPage.detachedDeviceSearch(false), serialNumber);
//        actions.click(customerPage.factoryResetButton(false));
//        actions.click(customerPage.resetNowButton(false));
//        if (actions.isElementPresent(loginPage.allowNotificationsLogindevice(true))) {
//            actions.clickInit(loginPage.allowNotificationsLogindevice(false));
//        }
        LocalDateTime now = LocalDateTime.now();
        while (actions.isElementPresent(customerPage.resettingDeviceMessage(true))) {
            LocalDateTime now2 = LocalDateTime.now();
            if (now2.equals(now.plusMinutes(1))) {
                break;
            }
        }
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }
    }

    @When("I search the access point to be deleted and delete it")
    public void i_search_the_access_point_to_be_deleted() throws InterruptedException {
        variableContext.setScenarioContext("DELETENEWDEVICE", "FALSE");
        boolean stop = false;
        String accessPointName = "AppAuto2";

        if(System.getProperty("Parallel").equalsIgnoreCase("true")){
            if(System.getProperty("accessPointName") != null){
                accessPointName = System.getProperty("accessPointName");
            }
        }

        while(!actions.isElementPresent(customerPage.detachedDeviceSearch(true))){
            actions.backButtonPhone();
        }

        actions.clearAndSend(customerPage.detachedDeviceSearch(false), accessPointName);
        actions.click(customerPage.accessPointDelete(accessPointName, false));
        actions.click(customerPage.accessPointConfirmDelete(false));
        Thread.sleep(3000);
    }

    @When("The device should get configured")
    public void the_device_should_get_configure() {
//        variableContext.setScenarioContext("DETACHNEWDEVICE","TRUE");
        boolean pass = actions.isElementPresent(customerPage.configuredSuccessfullyMessage(false));
        testStepVerify.isTrue(pass, "The device should get configured Successfully", "The device is configured Successfully", "The device is not configured");
    }

    @When("I detach the deleted device")
    public void i_detach_the_deleted_device() {
        variableContext.setScenarioContext("ADDDELETEDDEVICE", "TRUE");
        String serialNumber = PropertyUtility.getDataProperties("main.device.serial.no");

        if(System.getProperty("Parallel").equalsIgnoreCase("true")){
            if(System.getProperty("serialNumber") != null){
                serialNumber = System.getProperty("serialNumber");
            }
        }

        actions.sendKeys(customerPage.detachedDeviceSearch(false), serialNumber);
        actions.click(customerPage.factoryResetButton(false));
        actions.click(customerPage.resetNowButton(false));
        if (actions.isElementPresent(loginPage.allowNotificationsLogindevice(true))) {
            actions.clickInit(loginPage.allowNotificationsLogindevice(false));
        }
        LocalDateTime now = LocalDateTime.now();
        while (actions.isElementPresent(customerPage.resettingDeviceMessage(true))) {
            LocalDateTime now2 = LocalDateTime.now();
            if (now2.equals(now.plusMinutes(1))) {
                break;
            }
        }
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }
    }

    @When("the device should get detached")
    public void the_device_should_get_detached() {
        String serialNumber = PropertyUtility.getDataProperties("main.device.serial.no");

        if(System.getProperty("Parallel").equalsIgnoreCase("true")){
            if(System.getProperty("serialNumber") != null){
                serialNumber = System.getProperty("serialNumber");
            }
        }

        actions.clearAndSend(customerPage.detachedDeviceSearch(false), serialNumber);

        boolean pass = actions.isElementPresent(customerPage.factoryResetButton(true));

        testStepVerify.isFalse(pass, "The detached device should not be shown", "The detached device is not shown", "The detached device is shown");
    }

}
