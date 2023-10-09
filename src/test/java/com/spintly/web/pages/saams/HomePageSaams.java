package com.spintly.web.pages.saams;

import com.spintly.base.core.PageBase;
import com.spintly.base.support.properties.PropertyUtility;
import org.openqa.selenium.WebElement;

import java.util.List;

public class HomePageSaams extends PageBase {
    String envlocator = PropertyUtility.getDataProperties("envlocator");
    String hamburger = PropertyUtility.getDataProperties("hamburger.menu.text");

    //Current organisation
    public WebElement currentOrganisation(boolean ignoreException){ return findElement("//android.widget.TextView[@resource-id='com.mrinq.smartaccess"+envlocator+":id/orgTitle']",LocatorType.XPath,ignoreException);}

    //organisations dropdown
    public WebElement organisationsDropdown(boolean ignoreException){ return findElement("//android.widget.ImageView[@resource-id='com.mrinq.smartaccess"+envlocator+":id/drop']",LocatorType.XPath,ignoreException);}

    public WebElement accessHistoryDatePicker(){ return findElement("com.mrinq.smartaccess"+envlocator+":id/datePicker",LocatorType.Id);}

    //AccessPointButton
    public WebElement accessPointButton(String accessPoint,boolean ignoreException){
        return findElement(String.format("//android.widget.TextView[@text='%s']",accessPoint),LocatorType.XPath,ignoreException);
    }

    public List<WebElement> accessPointButtonCount(String accessPoint, boolean ignoreException){
        return findElements(String.format("//android.widget.TextView[@text='%s']",accessPoint),LocatorType.XPath,ignoreException);
    }

    //Menu for a Particular Access Point
    public WebElement accessPointMenu(String accessPoint){
        return findElement(String.format(
                "//android.widget.TextView[@text='%s']/parent::android.widget.LinearLayout/following-sibling::android.widget.LinearLayout/android.widget.ImageButton",
                accessPoint),LocatorType.XPath);
    }

    public WebElement accessPointDoorState(String accessPoint){
        return findElement(String.format(
                "//android.widget.TextView[@text='%s']/parent::android.widget.LinearLayout/following-sibling::android.widget.LinearLayout/android.widget.TextView",
                accessPoint),LocatorType.XPath);
    }

    //Door State Button
    public WebElement doorStateButton(){
        return findElement("//android.widget.TextView[@text='Door State']",LocatorType.XPath);
    }

    //Door State Selector
    public WebElement doorStateSelector(){
        return findElement("//android.widget.EditText",LocatorType.XPath);
    }

    //Door State
    public WebElement doorState(String state){
        return findElement(String.format("//android.widget.CheckedTextView[@text='%s']",state),LocatorType.XPath);
    }

    //Save Button
    public WebElement saveButtonDoorState(){
        return findElement("//android.widget.Button[@resource-id='com.mrinq.smartaccess"+envlocator+":id/saveBtn']",LocatorType.XPath);
    }

    //Hamburger Menu
    public WebElement hamburgerMenu(boolean ignoreException){
        return  findElement("//android.widget.ImageButton[@content-desc=\""+hamburger+"\"]",LocatorType.XPath,ignoreException);
    }

    //Organisation Button
    public WebElement organisationButton(String orgName,boolean ignoreException){ return findElement(String.format("//android.widget.TextView[@text='%s']",orgName),LocatorType.XPath,ignoreException);}

    //DoorStateChangeAcknowledgement
    public WebElement doorStateChangeAcknowledgement(){ return findElement("//android.widget.TextView[@text='Door State Changed!']",LocatorType.XPath);}

    //OK Button for acknowledgement
    public WebElement okButtonDoorStateAcknowlegdement(boolean ignoreException){
        return findElement("//android.widget.Button[@resource-id='com.mrinq.smartaccess"+envlocator+":id/okBtn']",LocatorType.XPath,ignoreException);
    }

    public WebElement okButtonRemoteUnlock(){ return findElement("//android.widget.Button[@text='OK']",LocatorType.XPath);}

    //Click to Access Acknowledgement
    public WebElement clickToAccessAcknowledgement(boolean ignoreException){ return findElement("//android.widget.RelativeLayout[@resource-id='com.mrinq.smartaccess"+envlocator+":id/successSheet']/android.widget.FrameLayout/android.widget.RelativeLayout/android.widget.TextView",LocatorType.XPath,ignoreException);}

    //Admin Console Button
    public WebElement adminConsoleButton(boolean ignoreException){ return findElement("//android.widget.TextView[@text='Admin Console']",LocatorType.XPath,ignoreException);}

    public WebElement managerConsoleButton(boolean ignoreException){ return findElement("//android.widget.TextView[@text='Manager Console']",LocatorType.XPath,ignoreException);}
    //accessHistory Tab
    public WebElement accessHistoryTab(){ return findElement("//android.widget.TextView[@text='Access History']",LocatorType.XPath);}

    //User Management tab
    public WebElement userManagementTab(boolean ignoreException){ return findElement("//android.widget.TextView[@text='User Management']",LocatorType.XPath,ignoreException);}

    //Back Button
    public WebElement backButton(){ return findElement("Navigate up",LocatorType.AccessibiltyID);}

    //Home tab
    public WebElement homeTab(){ return findElement("//android.widget.TextView[@text='Home']",LocatorType.XPath);}

    //BarrierList
    public WebElement barrierList(){ return findElement("//androidx.recyclerview.widget.RecyclerView[@resource-id='com.mrinq.smartaccess"+envlocator+":id/barrierList']",LocatorType.XPath);}

    //Door State bluetooth popup
    public WebElement doorStateBluetoothPopup(){
        return findElement("//android.widget.TextView[@text='Changing door state via Bluetooth...']",LocatorType.XPath);
    }

    public WebElement doorStateCloudPopup(){
        return findElement("//android.widget.TextView[@text='Changing door state via Cloud...']",LocatorType.XPath);
    }

    //Turn On Bluetooth Pop Up Door State
    public WebElement popDSTurnOnBluetooth(){ return findElement("//android.widget.TextView[@text='Bluetooth is turned off. Please turn it on.']",LocatorType.XPath);}

    //Cancel Button Door State Error Pop Up
    public WebElement cancelButtonDoorStateErrorPopUp(){
        return findElement("com.mrinq.smartaccess"+envlocator+":id/cancelBtn",LocatorType.Id);
    }

    //Cancel Turn On Bluetooth
    public WebElement cancelTurnOnBluetooth(boolean ignoreException){
        return findElement("//android.widget.Button[@text='CANCEL']",LocatorType.XPath,ignoreException);
    }

    public WebElement TurnOnBluetoothMessage(boolean ignoreException){
        return findElement("//android.widget.TextView[@text='Bluetooth is turned off. Please turn it on.']",LocatorType.XPath,ignoreException);
    }

    public WebElement moreOptionsButton(boolean ignoreException){ return findElement("//android.widget.ImageView[@content-desc='More options']",LocatorType.XPath,ignoreException);}

    public WebElement logoutButton(boolean ignoreException){ return findElement("//android.widget.TextView[@text='Logout']",LocatorType.XPath,ignoreException);}

    public WebElement okLogoutButton(boolean ignoreException){ return findElement("//android.widget.Button[@text='OK']",LocatorType.XPath,ignoreException);}

    public WebElement homeLabel(boolean ignoreException){ return findElement("//android.widget.TextView[@text='Home']",LocatorType.XPath,ignoreException);}

    public WebElement accessExpiredMessage(boolean ignoreException){ return findElement("//android.widget.TextView[@text='Your access permissions to access points have expired. To renew the access permissions, please contact the organisation Admin']",LocatorType.XPath,ignoreException);}

    public WebElement noMobileAccessText(boolean ignoreException){ return findElement("com.mrinq.smartaccess"+envlocator+":id/noMobileAccessText",LocatorType.Id,ignoreException);}

    public WebElement remoteUnlockPopup(boolean ignoreException){ return findElement("//android.widget.TextView[@resource-id='com.mrinq.smartaccess"+envlocator+":id/alertTitle' and @text='Remote Unlock']",LocatorType.XPath,ignoreException);}


    public WebElement bleOffToast(boolean ignoreException){ return findElement("//android.widget.Toast[1]",LocatorType.XPath,ignoreException);}


    public WebElement gpsCheckInOption(boolean ignoreException){ return findElement("com.mrinq.smartaccess"+envlocator+":id/gps_button_status",LocatorType.Id,ignoreException);}

    public WebElement confirmGPSCheckin(){ return findElement("//android.widget.Button[@text='CHECK-IN']",LocatorType.XPath);}

    public WebElement confirmGPSCheckout(){ return findElement("//android.widget.Button[@text='CHECK-OUT']",LocatorType.XPath);}

    public WebElement userAccessType(String username,String accessType,String time, boolean ignoreException){ return findElement(String.format("//android.widget.TextView[@text='%s']/preceding-sibling::android.widget.TextView[@text='%s']/following-sibling::android.widget.TextView[@text='%s']",username,accessType,time),LocatorType.XPath,ignoreException);}

    public WebElement accessHistoryListScroll(){ return findElement("com.mrinq.smartaccess"+envlocator+":id/historyList",LocatorType.Id);}

    public WebElement orgListScroll(boolean ignoreException){ return findElement("com.mrinq.smartaccess"+envlocator+":id/organisationList",LocatorType.Id,ignoreException);}
    public WebElement about(boolean ignoreException){ return findElement("//android.widget.TextView[@text='About']",LocatorType.XPath,ignoreException);}

    public WebElement deviceLockRequestButton(boolean ignoreException){ return findElement("com.mrinq.smartaccess"+envlocator+":id/mobileLockRequestButton",LocatorType.Id,ignoreException);}

    public WebElement okPhoneChangeRequest(boolean ignoreException){ return findElement("//android.widget.Button[@text='OK']",LocatorType.XPath,ignoreException);}

    public WebElement notificationButton(boolean ignoreException){ return findElement("com.mrinq.smartaccess"+envlocator+":id/notification",LocatorType.Id,ignoreException);}

    public WebElement approveDeviceLockNotification(String username,boolean ignoreException){ return findElement(String.format("//android.widget.TextView[@text='%s has requested to switch to a different device. Click here to Approve it.']",username),LocatorType.XPath);}

    public WebElement userOrganisationRole(boolean ignoreException){ return findElement("com.mrinq.smartaccess"+envlocator+":id/userOrganisationRole",LocatorType.Id,ignoreException);}

}
