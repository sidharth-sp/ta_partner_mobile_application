package com.spintly;

import com.spintly.base.core.VariableContext;
import com.spintly.base.core.DriverContext;
import com.spintly.base.support.logger.LogUtility;
import com.spintly.base.support.properties.PropertyUtility;
import com.spintly.base.utilities.*;
import cucumber.api.Scenario;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;
import org.apache.commons.lang3.SystemUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.PageLoadStrategy;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import static io.appium.java_client.service.local.flags.GeneralServerFlag.BASEPATH;

@SuppressWarnings({"All"})
public class SuiteSetup extends EventFiringWebDriver {
    private static LogUtility logger = new LogUtility(SuiteSetup.class);
    private static AppiumDriver driver = null;
    private static final Thread CLOSE_THREAD = new Thread() {

        @Override
        public void run() {
//            driver.quit();
//            service.stop();
        }
    };
    private static SuiteSetup setupManager;
    private static String TARGET_PLATFORM;

    static {
        TARGET_PLATFORM = PropertyUtility.getProperty("target.platform");
        logger.detail("PLATFORM : " + TARGET_PLATFORM);
        if (TARGET_PLATFORM.equalsIgnoreCase("ANDROID")) {
            try {
                driver = createAndroidDriverInstance();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        if (driver != null) {
            driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
            DriverContext.getObject().setPrimaryInstanceKey("ORIGINAL");
            DriverContext.getObject().storeDriverInstance("ORIGINAL", driver);
            DriverContext.getObject().setDriver(driver);
        }
        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
    }

    private SuiteSetup() {
        super(driver);
    }

    public static SuiteSetup getObject() {
        if (setupManager == null) {
            setupManager = new SuiteSetup();
        }
        return setupManager;
    }

    public static AppiumDriver getDriver() {
        if (DriverContext.getObject().getDriver() != null)
            return DriverContext.getObject().getDriver();
        else
            return null;
    }

    public static void setDriver(AndroidDriver newDriver) {
        DriverContext.getObject().setDriver(newDriver);
        driver = driver;
    }

    public static AppiumDriver createAndroidDriverInstance() throws MalformedURLException {
        AppiumDriver driver = null;
        DesiredCapabilities desiredCapabilities = getAndroidDesiredCapabilities();

        int port = 4723;
        System.out.println("Port Used is : " + port);

        try{
            driver = new AndroidDriver(new URL("http", "127.0.0.1", port, "/wd/hub"), desiredCapabilities);
        }catch(Exception e){
            System.out.println("Initialization error : \n"+e);
        }

        return driver;
    }

    private static DesiredCapabilities getAndroidDesiredCapabilities() {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();

        String appName = "partner";
        String env = PropertyUtility.getProperty("environment");

        if(System.getProperty("Parallel").equalsIgnoreCase("true")){
            if(System.getProperty("appName") != null){
                appName = System.getProperty("appName");
            }
        }

        String appURL = FileUtility.getSuiteResource("", "src/main/resources/APK/"+appName+"/"+env+"/testApp.apk");
        String appPackage = setAppPackage(appName,env);
        String activity = setAppActivity(appName);

        String UDID = "1603201121002MR";
        System.out.println("UDID Used is : " + UDID);

        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "UiAutomator2");
        desiredCapabilities.setCapability(MobileCapabilityType.UDID, UDID);

        if(System.getProperty("Parallel").equalsIgnoreCase("true")){
            if(System.getProperty("starting") != null || System.getProperty("stopping") != null){
                desiredCapabilities.setCapability("appPackage", appPackage);
                desiredCapabilities.setCapability(MobileCapabilityType.APP,appURL);
                desiredCapabilities.setCapability("appActivity", activity);
                desiredCapabilities.setCapability("noReset", "false");
                desiredCapabilities.setCapability("fullReset", "false");
            }
        }

        desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, 300);
        return desiredCapabilities;
    }

    public static AppiumDriverLocalService getAppiumService() {
        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withIPAddress("127.0.0.1")
                .usingPort(4723)
                .usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
                .withArgument(BASEPATH, "/wd/hub")
                .withArgument(GeneralServerFlag.SESSION_OVERRIDE);

        return AppiumDriverLocalService.buildService(builder);
    }

    public void useDriverInstance(String instanceKey) {
        try {
            DriverContext.getObject().useDriverInstance(instanceKey);
            logger.detail("Executing on Driver Instance : " + instanceKey);
        } catch (Exception ex) {
            logger.detail("Fetching Driver Instance Failed : " + instanceKey);
            VariableContext.getObject().setScenarioContext("FAILURE", "TRUE");
        }
    }

    public String getCurrentInstanceKey() {
        return DriverContext.getObject().getCurrentKey();
    }


    public void createNewAndroidDriverInstance(String key, String browser) throws MalformedURLException {
        AppiumDriver newAndroidDriverInstance = createAndroidDriverInstance();
        DriverContext.getObject().storeDriverInstance(key, newAndroidDriverInstance);
    }

    @Override
    public void close() {
        if (Thread.currentThread() != CLOSE_THREAD) {
            throw new UnsupportedOperationException("You shouldn't close this AndroidDriver. It's shared and will close when the JVM exits.");
        }
        super.close();
    }

    public static String setAppPackage(String appName,String env){
        if(appName.equalsIgnoreCase("saams")){
            switch (env){
                case "test":
                    PropertyUtility.setLoginData("app.package","com.mrinq.smartaccess.test");
                    break;
                case "test1":
                    PropertyUtility.setLoginData("app.package","com.mrinq.smartaccess.test1");
                    break;
                case "test2":
                    PropertyUtility.setLoginData("app.package","com.mrinq.smartaccess.test2");
                    break;
            }
        } else if (appName.equalsIgnoreCase("partner")) {
            switch (env){
                case "test1":
                    PropertyUtility.setLoginData("app.package","com.spintly.partner.test1");
                    break;
                case "test2":
                    PropertyUtility.setLoginData("app.package","com.spintly.partner.test2");
                    break;
            }
        }
        return PropertyUtility.getDataProperties("app.package");
    }

    public static String setAppActivity(String appName){
        String activity = "";
        switch (appName){
            case "saams":
                activity = "com.spintly.smartaccess.Activities.Splash";
                break;
            case "partner":
                activity = "com.spintlypartnermobile.SplashActivity";
                break;
        }
        return activity;
    }

}
