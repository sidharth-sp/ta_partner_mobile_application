package com.spintly.web.stepdefinitions.saams;

import com.spintly.api.services.AccessManagementService;
import com.spintly.base.core.DriverBase;
import com.spintly.base.support.properties.PropertyUtility;
import com.spintly.web.pages.saams.HomePageSaams;
import com.spintly.web.support.WebDriverActions;
import cucumber.api.java.en.Then;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class SaamsApp extends DriverBase {

    WebDriverActions actions = new WebDriverActions();
    HomePageSaams homePageSaams = new HomePageSaams();
    AccessManagementService accessManagementService = new AccessManagementService();

    @Then("I assign an access point to the user")
    public void i_assign_an_access_point_to_the_user() throws Throwable {
        String accessPoint = "AppAuto2";
        if(System.getProperty("Parallel").equalsIgnoreCase("true")){
            if(System.getProperty("accessPointName") != null){
                accessPoint = System.getProperty("accessPointName");
            }
        }

        String orgId = "857";
        if(System.getProperty("Parallel").equalsIgnoreCase("true")){
            if(System.getProperty("orgId") != null){
                orgId = System.getProperty("orgId");
            }
        }

        String userPhone = "7722082259";
        if(System.getProperty("Parallel").equalsIgnoreCase("true")){
            if(System.getProperty("userPhone") != null){
                userPhone = System.getProperty("userPhone");
            }
        }

        accessManagementService.assignPermission(accessPoint,userPhone,orgId);
    }

    @Then("I perform clickToAccess on the a door")
    public void i_perform_clickTo_on_OK_button_for_popup() throws Throwable {
        String accessPoint = "AppAuto2";
        if(System.getProperty("Parallel").equalsIgnoreCase("true")){
            if(System.getProperty("accessPointName") != null){
                accessPoint = System.getProperty("accessPointName");
            }
        }

        actions.click(homePageSaams.accessPointButton(accessPoint, false));

//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
//        String accessPoint = PropertyUtility.getDataProperties("clickToAccess.door.name");
//        LocalDateTime now = LocalDateTime.now();
//        String accessTime = String.valueOf(new Date().getTime()).toString().substring(0, 10);
//        variableContext.setScenarioContext("CLICKTOACCESSTIME", accessTime);
//
//        variableContext.setScenarioContext("CLICKTOACCESSUSERTIME", dtf.format(now));

        Thread.sleep(3000);
    }

}
