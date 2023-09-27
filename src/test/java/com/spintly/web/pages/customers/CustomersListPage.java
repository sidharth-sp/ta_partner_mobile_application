package com.spintly.web.pages.customers;

import com.spintly.base.core.PageBase;
import com.spintly.base.support.properties.PropertyUtility;
import org.openqa.selenium.WebElement;

public class CustomersListPage extends PageBase {
    String envlocator = PropertyUtility.getDataProperties("envlocator");

    public WebElement customerSearchBar(boolean ignoreException){ return findElement("//android.widget.EditText[@resource-id='searchInput']",LocatorType.XPath,ignoreException);}

    public WebElement customerLink(String customerName,boolean ignoreException){ return findElement(String.format("//android.widget.TextView[@text='%s']/parent::android.view.ViewGroup",customerName),LocatorType.XPath);}


}
