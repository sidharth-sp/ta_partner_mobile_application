package com.spintly.web.pages.login;

import com.spintly.base.core.PageBase;
import com.spintly.base.support.properties.PropertyUtility;
import org.openqa.selenium.WebElement;
import java.util.*;

public class LoginPage extends PageBase {
    String envlocator = PropertyUtility.getDataProperties("envlocator");
    String device = System.getProperty("test.Device");
    public WebElement phoneInput(boolean ignoreException) {return findElement("(//android.widget.TextView[@text='Phone number']/following-sibling::android.view.ViewGroup/android.widget.EditText)[1]", LocatorType.XPath,ignoreException);}

    public WebElement passwordInput(boolean ignoreException) {return findElement("(//android.widget.EditText[@resource-id='RNE__Input__text-input'])[2]", LocatorType.XPath,ignoreException);}

    public WebElement countryCodeDropdown(boolean ignoreException){ return findElement("//android.widget.EditText[@resource-id='RNE__Input__text-input'][1]/preceding-sibling::android.widget.TextView/preceding-sibling::android.view.ViewGroup",LocatorType.XPath,ignoreException);}

    public WebElement signInButton(boolean ignoreException) {return findElement("//android.widget.TextView[@text='SIGN IN']", LocatorType.XPath,ignoreException);}

    public WebElement selectCountryButton(boolean ignoreException) {return findElement("//android.widget.TextView[@text='Select the Country']/following-sibling::android.widget.Spinner/android.widget.TextView", LocatorType.XPath,ignoreException);}


    public WebElement allowNotificationsLogindevice(boolean ignoreException){
        String path = "";
        switch (device){
            case "device1":
                path = "//android.widget.Button[@text='While using the app']";
                break;
            case "device3":
                path = "//android.widget.Button[@text='ALLOW']";
                break;
        }
        return findElement(path,LocatorType.XPath,ignoreException);
    }

}
