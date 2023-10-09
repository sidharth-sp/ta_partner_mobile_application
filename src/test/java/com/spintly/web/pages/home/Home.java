package com.spintly.web.pages.home;

import com.spintly.base.core.PageBase;
import com.spintly.base.support.properties.PropertyUtility;
import org.openqa.selenium.WebElement;

public class Home extends PageBase {
    String envlocator = PropertyUtility.getDataProperties("envlocator");
    public WebElement DashboardLabel (boolean ignoreException ){ return findElement("//android.widget.TextView[@text='Dashboard']",LocatorType.XPath,ignoreException);}

    public WebElement hamburgerMenu(boolean ignoreException){ return findElement("//android.widget.TextView[@text='Dashboard']/preceding-sibling::android.view.ViewGroup/android.view.ViewGroup",LocatorType.XPath,ignoreException);}

    public WebElement customers(boolean ignoreException){ return findElement("//android.widget.TextView[@text='Customers']",LocatorType.XPath,ignoreException);}

    public WebElement selectPartnerDropdown(boolean ignoreException){ return findElement("//android.widget.TextView[@resource-id=\"iconIcon\"]",LocatorType.XPath,ignoreException);}
}
