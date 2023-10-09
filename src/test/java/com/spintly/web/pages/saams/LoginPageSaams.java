package com.spintly.web.pages.saams;

import com.spintly.api.utilityFunctions.ApiUtility;
import com.spintly.base.core.DriverBase;
import com.spintly.base.core.DriverContext;
import com.spintly.base.core.PageBase;
import com.spintly.base.support.properties.PropertyUtility;
import com.spintly.base.utilities.ExcelHelper;
import com.spintly.base.utilities.PdfHelper;
import com.spintly.base.utilities.RandomDataGenerator;
import com.spintly.web.pages.login.LoginPage;
import com.spintly.web.support.WebDriverActions;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.WebElement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;

public class LoginPageSaams extends PageBase {
    String envlocator = PropertyUtility.getDataProperties("envlocator");
    //Phone Input
    public WebElement Textbox_Phone() { return findElement("//input[@placeholder='Enter Phone Number']", LocatorType.XPath); }
    public WebElement Button_Next(){ return findElement("//span[contains(.,'Next')]/parent::button",LocatorType.XPath);}
    public WebElement Textbox_Password() { return findElement("filled-Password-small", LocatorType.Id); }
    public WebElement Button_Login() { return findElement("//span[@class='MuiButton-label']", LocatorType.XPath,false,false); }
    public WebElement
    phoneInput(boolean ignoreException) {
        return findElement("com.mrinq.smartaccess"+envlocator+":id/input_phone", LocatorType.Id,ignoreException);
    }

    //Next
    public WebElement nextButton(boolean ignoreException) {
        return findElement("com.mrinq.smartaccess"+envlocator+":id/loginBtnText", LocatorType.Id,ignoreException);
    }

    //Password Input
    public WebElement passwordInput(boolean ignoreException) {
        return findElement("com.mrinq.smartaccess"+envlocator+":id/input_password", LocatorType.Id,ignoreException);
    }

    //Login Button
    public WebElement loginButton(boolean ignoreException) {
        return findElement("com.mrinq.smartaccess"+envlocator+":id/loginBtnText", LocatorType.Id,ignoreException);
    }

    public WebElement retryInternetButton(boolean ignoreException){ return findElement("//android.widget.TextView[@text='Please check your internet connection']/following-sibling::android.widget.Button",LocatorType.XPath,ignoreException);}

    //Accept Location Button
    public WebElement smartAccessApp() {
        return findElement("//android.widget.TextView[@content-desc=\"Smart Access, has 3 notifications\"]", LocatorType.XPath);
    }

    public WebElement extensionMainDoor() {
        return findElement("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.RelativeLayout/androidx.drawerlayout.widget.DrawerLayout/android.widget.FrameLayout/android.widget.LinearLayout/androidx.viewpager.widget.ViewPager/android.view.ViewGroup/android.widget.LinearLayout/android.view.ViewGroup/androidx.recyclerview.widget.RecyclerView/android.widget.LinearLayout[3]/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.LinearLayout/android.widget.ImageButton",
                LocatorType.XPath);
    }

    public WebElement acceptBLE(boolean ignoreException) {
        return findElement("//android.widget.Button[@text='ACCEPT']", LocatorType.XPath,ignoreException);
    }

    public WebElement whileUsingAppButton(boolean ignoreException) {
        return findElement("//android.widget.Button[@text='While using the app']", LocatorType.XPath,ignoreException);
    }

    public WebElement whileUsingAppButtonDevice3(boolean ignoreException) {
        return findElement("//android.widget.Button[@text='WHILE USING THE APP']", LocatorType.XPath,ignoreException);
    }

    public WebElement allowLocationDevice2(boolean ignoreException) {
        return findElement("//android.widget.Button[@text='Allow']", LocatorType.XPath,ignoreException);
    }

    public WebElement allowLocationDevice3(boolean ignoreException) {
        return findElement("//android.widget.Button[@text='ALLOW']", LocatorType.XPath,ignoreException);
    }

    public WebElement skipUpdate(boolean ignoreException) {
        return findElement("//android.widget.Button[@text='SKIP']", LocatorType.XPath,ignoreException);
    }

    public WebElement closeCalibration(boolean ignoreException) {
        return findElement("//android.widget.Button[@text='Close']", LocatorType.XPath,ignoreException);
    }

    public WebElement userDoesNotExistMessage(){ return findElement("//android.widget.TextView[@text='User does not exist. Please contact your admin.']",LocatorType.XPath);}

    public WebElement forgotPasswordLabel(boolean ignoreException){ return findElement("com.mrinq.smartaccess"+envlocator+":id/forgotPassword",LocatorType.Id,ignoreException);}
    public WebElement forgotPasswordOTPInput(){ return findElement("com.mrinq.smartaccess"+envlocator+":id/otpView",LocatorType.Id);}
    public WebElement loginWithOTP(){ return findElement("com.mrinq.smartaccess"+envlocator+":id/otpLoginButton",LocatorType.Id);}

    public WebElement nextLoginForgotPassword(){ return findElement("com.mrinq.smartaccess"+envlocator+":id/loginButton",LocatorType.Id);}

}
