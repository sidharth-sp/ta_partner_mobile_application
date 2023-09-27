package com.spintly.runner;

import com.spintly.SuiteSetup;
import com.spintly.api.services.*;
import com.spintly.base.core.VariableContext;
import com.spintly.base.core.DriverContext;
import com.spintly.base.core.ReportBase;
import com.spintly.base.support.logger.LogUtility;
import com.spintly.base.support.properties.PropertyUtility;
import com.spintly.base.utilities.*;
import com.spintly.web.support.WebDriverActions;
import com.spintly.web.utilityfunctions.GeneralUtility;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
import cucumber.api.java.Before;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;


public class Hooks {
    private static boolean isFirstTestCase;
    private static LogUtility logger = new LogUtility(Hooks.class);

    static {
        PropertyUtility.loadProperties();
        String autoHome = Hooks.class.getProtectionDomain().getCodeSource().getLocation().getPath().replace("/target/test-classes/", "");
        if (SystemUtils.IS_OS_WINDOWS)
            autoHome = autoHome.substring(0, 1).equals("/") ? autoHome.substring(1) : autoHome;
        FileUtility.autoHome = autoHome;
        PropertyConfigurator.configure(FileUtility.getSuiteResource("", "src/main/resources/Properties/System/log4j.properties"));
    }

    protected AndroidDriver driver;
    public ReportBase reportBase;
    private boolean isTestcaseFailed = false;

    public Hooks() {
        this.reportBase = new ReportBase();
    }

    public synchronized void start(String resultFolder) {
        try {
            this.reportBase.startSuiteFile(resultFolder);
            String device = System.getProperty("DEVICE") == null ? "Windows VM" : System.getProperty("DEVICE");
            logger.detail("********** Initializing Test on Device : "+device.toUpperCase()+" ************");
            SuiteSetup.getObject().getDriver();
        } catch (Exception e) {
            logger.error("Unable to launch Driver");
        }
    }


    @Before
    public void beforeTest(Scenario scenario) throws InterruptedException {
//        WebDriverActions action = new WebDriverActions();

        logger.detail("**********************************************************************************");
        String[] rawFeature = scenario.getId().split("features/")[1].split("/");
        String[] rawFeatureName = rawFeature[rawFeature.length - 1].split(":");
        String tags = scenario.getSourceTagNames().toString();
        logger.detail("FEATURE : " + rawFeatureName[0]+ " | Tags : " + scenario.getSourceTagNames()+"");
        logger.detail("STARTING SCENARIO : " + scenario.getName());
        this.reportBase.startTestCase(scenario.getName(), rawFeatureName[0], tags);

        VariableContext.getObject().setScenarioContext("ORGID",PropertyUtility.getDataProperties("door.state.org.id"));

//        AndroidDriver androidDriver =(AndroidDriver) DriverContext.getObject().getDriver();
//        logger.detail("******************************* GETTING ANDROID DRIVER *********************");
//
//        String appPackage = PropertyUtility.getDataProperties("app.package");
//
//        boolean isRunningInForeground  = androidDriver.queryAppState(appPackage).toString().equalsIgnoreCase("RUNNING_IN_FOREGROUND");
//        boolean isRunningInBackground  = androidDriver.queryAppState(appPackage).toString().equalsIgnoreCase("RUNNING_IN_BACKGROUND");
//        boolean isNotRunning = androidDriver.queryAppState(appPackage).toString().equalsIgnoreCase("NOT_RUNNING");
//
//        System.out.println("Background : "+isNotRunning+"; Foreground : "+isRunningInForeground+"; Killed : "+isNotRunning);
//
//        logger.detail("******************************* CHECKING IF APP IS RUNNING *********************");
//        if( isNotRunning || !(isRunningInForeground) || isRunningInBackground ){
//            logger.detail("******************************* App Crashed Trying to Relaunch *********************");
//            androidDriver.launchApp();
//        }

//        logger.detail("------------ SESSION ID : "+androidDriver.getSessionId()+" ------------");
        new VariableContext().setScenarioContext("PASS_WITH_OBSERVATIONS","FALSE");
        new VariableContext().setScenarioContext("IS_PARTNER","FALSE");

    }

    @After
    public void afterTest(Scenario scenario) {
        boolean bit= false;
//        new GeneralUtility().returnToMainPage();
        try {
            if(((String) VariableContext.getObject().getScenarioContext("RestoreApConfigurationdef")).equals("TRUE"))
            {
                new AccessManagementService().restoreAccessPointConfiguration("RestoreApConfigurationdef");
            }

            if(((String) VariableContext.getObject().getScenarioContext("TURNONINTERNET")).equals("TRUE"))
            {
                new WebDriverActions().toggleDeviceWifi("enable");
            }

            if(((String) VariableContext.getObject().getScenarioContext("TURNONBLUETOOTH")).equals("TRUE"))
            {
                new WebDriverActions().setBluetooth("enable");
                System.out.println("ENABLE BLUETOOTH");
            }

            if(((String) VariableContext.getObject().getScenarioContext("LOGOUTLOGIN")).equals("TRUE"))
            {
//                new GeneralUtility().logoutAndloginAsMainUser();
            }

            if(((String) VariableContext.getObject().getScenarioContext("DELETENEWDEVICE")).equals("TRUE"))
            {
                String serialNumber = PropertyUtility.getDataProperties("main.device.serial.no");
                new PartnerWebPortalService().deleteAccessPoint(serialNumber);
            }

            if(((String) VariableContext.getObject().getScenarioContext("DETACHNEWDEVICE")).equals("TRUE"))
            {
                new GeneralUtility().goToCustomerPage();
                String serialNumber = PropertyUtility.getDataProperties("main.device.serial.no");
                new GeneralUtility().detachDevice(serialNumber);
            }

//            DriverContext.getObject().closeAllDriverInstanceExceptOriginal();
//            SuiteSetup.getObject().useDriverInstance("ORIGINAL");

            if(scenario.getStatus().toString().equals("UNDEFINED"))
            {
                this.reportBase.endTestCase(true, false);
                logger.detail("SKIPPED TEST SCENARIO : " + scenario.getName() + " | Inconclusive Count : " + this.reportBase.inconclusive());
            }
            else {
                this.reportBase.endTestCase(scenario.isFailed(), false);

                if (!scenario.isFailed() || !this.reportBase.isVerificationFailed()) {
                    String Failure = (String) VariableContext.getObject().getScenarioContext("FAILURE");
                    if (Failure.equals("TRUE")) {
                        logger.detail("SKIPPED TEST SCENARIO : " + scenario.getName() + " | Inconclusive Count : " + this.reportBase.inconclusive());
                        bit = true;
                    } else if (((String) VariableContext.getObject().getScenarioContext("PASS_WITH_OBSERVATIONS")).equals("TRUE")) {
                        logger.detail("TEST SCENARIO WITH OBSERVATIONS : " + scenario.getName());
                        bit = true;
                    } else {
                        logger.detail("PASSING TEST SCENARIO : " + scenario.getName());
                        bit = true;
                    }
                    VariableContext.getObject().setScenarioContext("FAILURE", "FALSE");
                    VariableContext.getObject().setScenarioContext("PASS_WITH_OBSERVATIONS", "FALSE");

                } else if (scenario.isFailed() || this.reportBase.isVerificationFailed()) {
                    try {
                        bit = true;
                        logger.detail("FAILED TEST SCENARIO : " + scenario.getName());
                        logger.debug("PAGE SOURCE :" + StringUtils.normalizeSpace(DriverContext.getObject().getDriver().getPageSource()));

                    } catch (Exception e) {
                    }
                }
                if (PropertyUtility.targetPlatform.equalsIgnoreCase("WEB")) {
                   JavascriptExecutor js = (JavascriptExecutor) DriverContext.getObject().getDriver();
                   js.executeScript("Object.keys(localStorage).filter(x => x.startsWith('CognitoIdentityServiceProvider')).forEach(x => localStorage.removeItem(x))");
                }

            }
        } catch (Exception e) {
            if(bit==false) {
                this.reportBase.endTestCase(scenario.isFailed(), true);
                if (PropertyUtility.targetPlatform.equalsIgnoreCase("WEB")) {
                    JavascriptExecutor js = (JavascriptExecutor) DriverContext.getObject().getDriver();
                    js.executeScript("Object.keys(localStorage).filter(x => x.startsWith('CognitoIdentityServiceProvider')).forEach(x => localStorage.removeItem(x))");
                }
                logger.detail("SKIPPED TEST SCENARIO : " + scenario.getName() + " | Skipped Count : " + this.reportBase.skipped());
            }

        }finally {
            VariableContext.getObject().clearScenarioContext();
//            ((AndroidDriver)DriverContext.getObject().getDriver()).closeApp();
        }


    }

    public void tearDown() throws IOException {
        try {
            this.reportBase.endSuiteFile();
        }
        catch (Exception ex) { }
    }

//    @AfterStep
//    public void afterStep() {
////        WebDriverActions action = new WebDriverActions();
////        action.waitForJStoLoad();
//    }
}
