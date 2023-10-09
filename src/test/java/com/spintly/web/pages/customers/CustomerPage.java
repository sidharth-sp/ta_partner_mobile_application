package com.spintly.web.pages.customers;

import com.spintly.base.core.PageBase;
import com.spintly.base.support.properties.PropertyUtility;
import org.openqa.selenium.WebElement;

public class CustomerPage extends PageBase {
    String envlocator = PropertyUtility.getDataProperties("envlocator");

    public WebElement gatewaysTab(boolean ignoreException){ return findElement("//android.widget.TextView[@text='Gateways']",LocatorType.XPath,ignoreException);}

    public WebElement meshIOsTab(boolean ignoreException){ return findElement("//android.widget.TextView[@text='Mesh IOs']",LocatorType.XPath,ignoreException);}

    public WebElement accessPointsTab(boolean ignoreException){ return findElement("//android.widget.TextView[@text='Access Points']",LocatorType.XPath,ignoreException);}

    public WebElement detachedDeviceTab(boolean ignoreException){ return findElement("//android.widget.TextView[@text='Detached Device']",LocatorType.XPath,ignoreException);}

    public WebElement allowPositionDevice3(boolean ignoreException){ return findElement("//android.widget.Button[@text='ALLOW']",LocatorType.XPath,ignoreException);}

    public WebElement accessPointsScrollable( boolean ignoreException){ return findElement("//android.widget.ScrollView",LocatorType.XPath,ignoreException);}

    public WebElement accessPointName(String name, boolean ignoreException){ return findElement(String.format("//android.widget.TextView[@text='%s']",name),LocatorType.XPath,ignoreException);}

    public WebElement accessPointDelete(String name, boolean ignoreException){ return findElement(String.format("//android.widget.TextView[@text='%s']/parent::android.view.ViewGroup/following-sibling::android.view.ViewGroup",name),LocatorType.XPath);}

    public WebElement accessPointConfirmDelete(boolean ignoreException){ return findElement("//android.widget.TextView[@text='DELETE']",LocatorType.XPath,ignoreException);}
    public WebElement configureButton(boolean ignoreException){ return findElement("//android.widget.TextView[@text='CONFIGURE']",LocatorType.XPath,ignoreException);}

    public WebElement firmwareUpdate(boolean ignoreException){ return findElement("//android.widget.TextView[@text='Firmware Update']",LocatorType.XPath,ignoreException);}

    public WebElement configuredSuccessfullyMessage(boolean ignoreException){ return findElement("//android.widget.TextView[@text='The Device is now configured in the mesh network succesfully!']",LocatorType.XPath,ignoreException);}

    public WebElement resettingDeviceMessage(boolean ignoreException){ return findElement("//android.widget.TextView[@text=' Resetting  the device']",LocatorType.XPath,ignoreException);}

    public WebElement configuringDeviceMessage(boolean ignoreException){ return findElement("//android.widget.TextView[@text='Configuring the device']",LocatorType.XPath,ignoreException);}
    public WebElement detachedDeviceSearch(boolean ignoreException){ return findElement("//android.widget.EditText[@resource-id='searchInput']",LocatorType.XPath,ignoreException);}

    public WebElement factoryResetButton(boolean ignoreException){ return findElement("//android.widget.TextView[@text='FACTORY RESET']",LocatorType.XPath,ignoreException);}

    public WebElement resetNowButton(boolean ignoreException){ return findElement("//android.widget.TextView[@text='RESET NOW']",LocatorType.XPath,ignoreException);}

    public WebElement resetButton(boolean ignoreException){ return findElement("//android.widget.TextView[@text='RESET']",LocatorType.XPath,ignoreException);}


}
