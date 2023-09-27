package com.spintly.base.core;


import com.spintly.base.support.properties.PropertyUtility;
import com.spintly.base.support.cucumberEvents.StepsStore;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.spintly.base.managers.ResultManager.error;


public class PageBase extends DriverBase {
    private long DRIVER_WAIT_TIME;

    public PageBase() {
        DRIVER_WAIT_TIME = Long.parseLong(PropertyUtility.getProperty("WaitTime"));
    }

    public enum LocatorType {
        Id,
        Name,
        ClassName,
        LinkText,
        PartialLinkText,
        CssSelector,
        TagName,
        XPath,
        AccessibiltyID
    }

    public void waitForPageLoad() {
        new WebDriverWait(DriverContext.getObject().getDriver(), Duration.ofSeconds(DRIVER_WAIT_TIME)).until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    public List<WebElement> findElements(String identifier, LocatorType locatorType, boolean... ignoreWait) {
        AppiumDriver driver = DriverContext.getObject().getDriver();

        boolean wait = true;
        if(ignoreWait.length>0){
            if (ignoreWait[0] == true) {
               wait = false;
            }
        }

        List<WebElement> elements = null;
        try {
            switch (locatorType) {
                case AccessibiltyID: {
                   if(wait){ WaitUntilElementsAreDisplayed(AppiumBy.accessibilityId(identifier));}
                    elements = driver.findElements(AppiumBy.accessibilityId(identifier));
                    break;
                }
                case Id: {
                    if(wait){ WaitUntilElementsAreDisplayed(AppiumBy.id(identifier));}
                    elements = driver.findElements(AppiumBy.id(identifier));
                    break;
                }
                case Name: {
                    if(wait){ WaitUntilElementsAreDisplayed(AppiumBy.name(identifier));}
                    elements = driver.findElements(AppiumBy.name(identifier));
                    break;
                }
                case ClassName: {
                    if(wait){ WaitUntilElementsAreDisplayed(AppiumBy.className(identifier));}
                    elements = driver.findElements(AppiumBy.className(identifier));
                    break;
                }
                case XPath: {
                    if(wait){ WaitUntilElementsAreDisplayed(AppiumBy.xpath(identifier));}
                    elements = driver.findElements(AppiumBy.xpath(identifier));
                    break;
                }
                case LinkText: {
                    if(wait){ WaitUntilElementsAreDisplayed(By.linkText(identifier));}
                    elements = driver.findElements(By.linkText(identifier));
                    break;
                }
                case PartialLinkText: {
                    if(wait){ WaitUntilElementsAreDisplayed(By.partialLinkText(identifier));}
                    elements = driver.findElements(By.partialLinkText(identifier));
                    break;
                }
                case CssSelector: {
                    if(wait){ WaitUntilElementsAreDisplayed(AppiumBy.cssSelector(identifier));}
                    elements = driver.findElements(AppiumBy.cssSelector(identifier));
                    break;
                }
                case TagName: {
                    if(wait){WaitUntilElementsAreDisplayed(By.tagName(identifier));}
                    elements = driver.findElements(By.tagName(identifier));
                    break;
                }
            }
        } catch (Exception e) {

        } finally {
            updateWaitTime(Long.parseLong(PropertyUtility.getProperty("WaitTime")));
        }
        return elements;
    }

    public WebElement findElement(String identifier, LocatorType locatorType, boolean... ignoreException) {
        AppiumDriver driver = DriverContext.getObject().getDriver();
        updateWaitTime(ignoreException);
        WebElement element = null;
        boolean retry = false;

        boolean wait = true;
        if(ignoreException.length>0){
            if (ignoreException[0] == true) {
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
                wait = false;
            }
        }

        do {
            try {
                switch (locatorType) {
                    case AccessibiltyID: {
                        if(wait){ WaitUntilElementIsDisplayed(AppiumBy.accessibilityId(identifier));}
                        element = driver.findElement(AppiumBy.accessibilityId(identifier));
                        break;
                    }
                    case Id: {
                        if(wait){ WaitUntilElementIsDisplayed(AppiumBy.id(identifier));}
                        element = driver.findElement(AppiumBy.id(identifier));
                        break;
                    }
                    case Name: {
                        if(wait){ WaitUntilElementIsDisplayed(AppiumBy.name(identifier));}
                        element = driver.findElement(AppiumBy.name(identifier));
                        break;
                    }
                    case ClassName: {
                        if(wait){ WaitUntilElementIsDisplayed(AppiumBy.className(identifier));}
                        element = driver.findElement(AppiumBy.className(identifier));
                        break;
                    }
                    case XPath: {
                        if(wait){ WaitUntilElementIsDisplayed(AppiumBy.xpath(identifier));}
                        element = driver.findElement(AppiumBy.xpath(identifier));
                        break;
                    }
                    case LinkText: {
                        if(wait){ WaitUntilElementIsDisplayed(By.linkText(identifier));}
                        element = driver.findElement(By.linkText(identifier));
                        break;
                    }
                    case PartialLinkText: {
                        if(wait){ WaitUntilElementIsDisplayed(By.partialLinkText(identifier));}
                        element = driver.findElement(By.partialLinkText(identifier));
                        break;
                    }
                    case CssSelector: {
                        if(wait){ WaitUntilElementIsDisplayed(AppiumBy.cssSelector(identifier));}
                        element = driver.findElement(AppiumBy.cssSelector(identifier));
                        break;
                    }
                    case TagName: {
                        if(wait){ WaitUntilElementIsDisplayed(By.tagName(identifier));}
                        element = driver.findElement(By.tagName(identifier));
                        break;
                    }
                }
            } catch (StaleElementReferenceException e) {
                if (retry) {
                    error("Element with [Locator : " + identifier + " ] by type [ " + locatorType + " ] should be displayed", "Element with [Locator : " + identifier + " ] by type [ " + locatorType + " ] is not displayed. Please refer error logs for more details.",
                            e.getMessage(), true);
                    break;
                }
                retry = true;
            } catch (NoSuchElementException e) {
                if (ignoreException.length > 0) {
                    if (ignoreException[0] == true) {
                        //ignore exception
                    } else {
                        variableContext.setScenarioContext("ERROR", "Element with [Locator : " + identifier + " ] by type [ " + locatorType + " ] is not displayed. Please refer error logs for more details.");
                        variableContext.setScenarioContext("STEP", StepsStore.get());
                        //throw new NoSuchElementException(identifier);
                        testStepAssert.isFail("Failed due to web element not found in HTML DOM " + identifier);
                    }
                } else {
                    variableContext.setScenarioContext("ERROR", "Element with [Locator : " + identifier + " ] by type [ " + locatorType + " ] is not displayed. Please refer error logs for more details.");
                    variableContext.setScenarioContext("STEP", StepsStore.get());
                    //throw new NoSuchElementException(identifier);
                    testStepAssert.isFail("Failed due to web element not found in HTML DOM " + identifier);
                }
            } finally {
                updateWaitTime(Long.parseLong(PropertyUtility.getProperty("WaitTime")));
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            }
        } while (retry);
        return element;
    }

    private void WaitUntilElementIsDisplayed(By locator) {
        try {
            AppiumDriver driver = DriverContext.getObject().getDriver();
            driver.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME));
            wait.until(ExpectedConditions.presenceOfElementLocated(locator));

        } catch (Exception ex) {
        }
    }

    public void open(String PageUrl) {
        DriverContext.getObject().getDriver().navigate().to(PageUrl);
    }

    public String get() {
        return DriverContext.getObject().getDriver().getCurrentUrl();
    }

    private void updateWaitTime(boolean... ignoreException) {
        if (ignoreException.length > 0)
            if (ignoreException[0] == true)
                DRIVER_WAIT_TIME = 1L;

    }

    private void updateWaitTime(Long waitTime) {
        DRIVER_WAIT_TIME = waitTime;
    }

    private void WaitUntilElementsAreDisplayed(By locator) {
        try {
            AppiumDriver driver = DriverContext.getObject().getDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME));
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(locator,0));

        } catch (Exception ex) {
        }
    }

    public void WaitUntilElementIsDisplayed(WebElement element) {
        try {
            AppiumDriver driver = DriverContext.getObject().getDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(DRIVER_WAIT_TIME));
            wait.until(ExpectedConditions.visibilityOf(element));

        } catch (Exception ex) {
        }
    }


}
