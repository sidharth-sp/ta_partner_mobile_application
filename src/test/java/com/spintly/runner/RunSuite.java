package com.spintly.runner;

import com.spintly.base.support.properties.PropertyUtility;
import com.spintly.web.utilityfunctions.GeneralUtility;
import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

@CucumberOptions(features = "target/test-classes/features/web", monochrome = true, tags = "@configureDevice", plugin = {

        "pretty", "html:target/cucumber-report/single",
        "json:target/cucumber-report/single/cucumber.json",
         "com.spintly.base.support.cucumberEvents.EventsHandler"},
        glue = {"com.spintly.web.stepdefinitions", "com.spintly.api", "com.spintly.runner"}
)
public class RunSuite extends AbstractTestNGCucumberTests {
    Hooks hooks;
    @Parameters({"test.Device", "test.Platform", "test.Environment", "test.Category"})
    public RunSuite(@Optional("device1") String device, @Optional("android") String Platform, @Optional("test") String environment, @Optional("regression") String category) {
        System.setProperty("LogFilePath", "Results");
        System.setProperty("Parallel", "false");
        System.setProperty("test.Device",device);
        System.setProperty("test.Environment",environment);
        System.setProperty("runner.class", this.getClass().getSimpleName());
        PropertyUtility.environment=environment;
        PropertyUtility.targetPlatform=Platform;
        this.hooks = new Hooks();
    }

    @BeforeSuite
    @Parameters({"NameWithtimestamp", "test.Platform", "test.Environment","test.Device"})
    public void start(@Optional("") String resultFolder, @Optional("android") String platform, @Optional("QA") String environment, @Optional("device1") String device) {
        this.hooks.start(resultFolder);
    }

    @AfterSuite
    @Parameters({"NameWithtimestamp", "test.Platform", "test.Category","test.Environment"})
    public void afterSuite(@Optional("") String resultFolder, @Optional("android") String platform,  @Optional("regression") String category,@Optional("QA") String environment) {
        try {this.hooks.tearDown();
            com.spintly.base.support.reports.SummaryReportWrapper.main(new String[]{System.getProperty("RESULTMAINDIR")+"\\"+System.getProperty("RESULTDIR"),platform, category,environment});
        } catch (Exception ex) {}
    }

}