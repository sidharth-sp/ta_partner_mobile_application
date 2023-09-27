import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;
import io.appium.java_client.service.local.flags.GeneralServerFlag;

import java.io.File;

import static io.appium.java_client.service.local.flags.GeneralServerFlag.BASEPATH;

public class StartServer {
    private static AppiumDriverLocalService server;


    public static void main(String args[]){
        server = getAppiumService();
        server.start();
        System.out.println("Server Started");
        server.stop();
    }
    public AppiumDriverLocalService getAppiumServiceDefault(){
       return AppiumDriverLocalService.buildDefaultService();
   }

    public static AppiumDriverLocalService getAppiumService(){
        AppiumServiceBuilder builder = new AppiumServiceBuilder ();
        builder.withIPAddress ("127.0.0.1")
                .usingPort (4723)
//                .withAppiumJS (new File ("C:\\Users\\sidha\\AppData\\Roaming\\npm\\node_modules\\appium\\lib\\main.js"))
                .usingDriverExecutable (new File ("C:\\Program Files\\nodejs\\node.exe"))
                .withArgument (BASEPATH, "/wd/hub")
                .withArgument (GeneralServerFlag.SESSION_OVERRIDE);

        return AppiumDriverLocalService.buildService (builder);
    }
}
