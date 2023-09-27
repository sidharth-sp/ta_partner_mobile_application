package com.spintly.api.services;

import com.spintly.base.core.DriverContext;
import com.spintly.base.support.properties.PropertyUtility;
import com.spintly.base.utilities.ApiHelper;
import com.spintly.base.utilities.RandomDataGenerator;
import io.cucumber.datatable.DataTable;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class PartnerWebPortalService extends DriverContext {

    public void addUserInPartner(@NotNull DataTable data){
        String token = PropertyUtility.getDataProperties("token");
        String partnerId = PropertyUtility.getDataProperties("main.partner.id");

        String name = "PartnerUser"+ RandomDataGenerator.getData("{RANDOM_STRING}",5);
        Map<String, String> dataMap = data.transpose().asMap(String.class, String.class);

        String phone = "99999"+RandomDataGenerator.getData("{RANDOM_NUM}",5);

        String role = dataMap.get("role");
        String roleId = "";

        switch (role){
            case "user":
                roleId = PropertyUtility.getDataProperties("role.endUser");
                break;
            case "admin":
                roleId = PropertyUtility.getDataProperties("role.admin");
                break;
            case "superadmin":
                roleId = PropertyUtility.getDataProperties("role.superAdmin");
                break;
        }

        String jsonString = "{\"name\":\""+name+"\"," +
                "\"roleId\":"+roleId+",\"phoneNumber\":\"+91"+phone+"\"," +
                "\"email\":\"sidharth.k@spintly.com\"}";

        String path = PropertyUtility.getDataProperties("base.api.url")+"/partnerManagement/v3/partners/"+partnerId+"/users";

        Response response = ApiHelper.givenRequestSpecification()
                .header("authorization", token)
                .body(jsonString)
                .when().redirects().follow(false).
                post();

        ApiHelper.genericResponseValidation(response,"ADD USER IN PARTNER");
    }

    public List<String> namesOfAccessPointsInOrg(){
        String token = PropertyUtility.getDataProperties("token");
        String partnerId = PropertyUtility.getDataProperties("main.partner.id");
        String orgId = PropertyUtility.getDataProperties("main.org.id");
        String siteId = PropertyUtility.getDataProperties("main.site.id");
        String networkId = PropertyUtility.getDataProperties("main.network.id");

        String path = PropertyUtility.getDataProperties("base.api.url")+"/infrastructureManagement/v6/partners/"+partnerId+"/organisations/"+orgId+"/accessPoints";

        Response response = ApiHelper.givenRequestSpecification()
                .header("authorization", token)
                .when().redirects().follow(false).
                get(path);

        ApiHelper.genericResponseValidation(response,"GET DEVICES ADDED IN ORG");

        JsonPath jsonPathEvaluator = response.jsonPath();

        List<String> accessPointNames = jsonPathEvaluator.getList("message.accessPoints.name");

        return accessPointNames;
    }

    public String getStateOfDevice(String name){
        String token = PropertyUtility.getDataProperties("token");
        String partnerId = PropertyUtility.getDataProperties("main.partner.id");
        String orgId = PropertyUtility.getDataProperties("main.org.id");
        String siteId = PropertyUtility.getDataProperties("main.site.id");
        String networkId = PropertyUtility.getDataProperties("main.network.id");

        String path = PropertyUtility.getDataProperties("base.api.url")+"/infrastructureManagement/v6/partners/"+partnerId+"/organisations/"+orgId+"/accessPoints";

        Response response = ApiHelper.givenRequestSpecification()
                .header("authorization", token)
                .when().redirects().follow(false).
                get(path);

        ApiHelper.genericResponseValidation(response,"RETURN ACCESS POINT STATUS");

        JsonPath jsonPathEvaluator = response.jsonPath();
        List<String> accessPointNames = jsonPathEvaluator.getList("message.accessPoints.name");
        String state = "";

        for(int i=0;i<accessPointNames.size();i++){
            if(accessPointNames.get(i).equalsIgnoreCase(name)){
                state = jsonPathEvaluator.getString("message.accessPoints["+i+"].resourceState");
            }
        }

        return state;
    }

    public String getIdOfDevice(String name){
        String token = PropertyUtility.getDataProperties("token");
        String partnerId = PropertyUtility.getDataProperties("main.partner.id");
        String orgId = PropertyUtility.getDataProperties("main.org.id");
        String siteId = PropertyUtility.getDataProperties("main.site.id");
        String networkId = PropertyUtility.getDataProperties("main.network.id");

        String path = PropertyUtility.getDataProperties("base.api.url")+"/infrastructureManagement/v6/partners/"+partnerId+"/organisations/"+orgId+"/accessPoints";

        Response response = ApiHelper.givenRequestSpecification()
                .header("authorization", token)
                .when().redirects().follow(false).
                get(path);

        ApiHelper.genericResponseValidation(response,"RETURN ACCESS POINT STATUS");

        JsonPath jsonPathEvaluator = response.jsonPath();
        List<String> accessPointNames = jsonPathEvaluator.getList("message.accessPoints.name");
        String id = "";

        for(int i=0;i<accessPointNames.size();i++){
            if(accessPointNames.get(i).equalsIgnoreCase(name)){
                id = jsonPathEvaluator.get("message.accessPoints["+i+"].id").toString();
            }
        }

        return id;
    }

    public void addAccessPoint(String serialNumber){
        String token = PropertyUtility.getDataProperties("token");
        String partnerId = PropertyUtility.getDataProperties("main.partner.id");
        String orgId = PropertyUtility.getDataProperties("main.org.id");
        String siteId = PropertyUtility.getDataProperties("main.site.id");
        String networkId = PropertyUtility.getDataProperties("main.network.id");

        String jsonString = "{\"name\":\"AppAuto4\",\"installationMethod\":\"new_install\"," +
                "\"configuration\":\"entry_rex\",\"siteId\":"+siteId+"," +
                "\"networkId\":\""+networkId+"\"," +
                "\"devices\":[{\"serialNumber\":\""+serialNumber+"\",\"deviceType\":\"entry\"}]," +
                "\"lockingMechanism\":\"em_or_mag_lock\"," +
                "\"relaySettings\":{\"relayOnTime\":6,\"invertRelayLogic\":false}}";

        String path = PropertyUtility.getDataProperties("base.api.url")+"/infrastructureManagement/v6/partners/"+partnerId+"/organisations/"+orgId+"/accessPoints";

        Response response = ApiHelper.givenRequestSpecification()
                .header("authorization", token)
                .body(jsonString)
                .when().redirects().follow(false).
                post(path);

        JsonPath jsonPathEvaluator = response.jsonPath();

        String accessPointId = jsonPathEvaluator.get("message.accessPointId").toString();

        variableContext.setScenarioContext("NEWACCESSPOINTID",accessPointId);

        ApiHelper.genericResponseValidation(response,"ADDED DEVICE : "+serialNumber);
    }

    public void deleteAccessPoint(String serialNumber){
        String token = PropertyUtility.getDataProperties("token");
        String partnerId = PropertyUtility.getDataProperties("main.partner.id");
        String orgId = PropertyUtility.getDataProperties("main.org.id");
        String siteId = PropertyUtility.getDataProperties("main.site.id");
        String networkId = PropertyUtility.getDataProperties("main.network.id");
        String accessPointId = (String) variableContext.getScenarioContext("NEWACCESSPOINTID");

        String path = PropertyUtility.getDataProperties("base.api.url")+"/infrastructureManagement/v3/partners/1/organisations/"+orgId+"/accessPoints/"+accessPointId;

        Response response = ApiHelper.givenRequestSpecification()
                .header("authorization", token)
                .when().redirects().follow(false).
                delete(path);

        ApiHelper.genericResponseValidation(response,"DELTETED DEVICE : "+ serialNumber);
    }

    public List<String> getDetachedDevices(){
        String token = PropertyUtility.getDataProperties("token");
        String partnerId = PropertyUtility.getDataProperties("main.partner.id");
        String orgId = PropertyUtility.getDataProperties("main.org.id");
        String siteId = PropertyUtility.getDataProperties("main.site.id");
        String networkId = PropertyUtility.getDataProperties("main.network.id");

        String path = PropertyUtility.getDataProperties("base.api.url")+"/infrastructureManagement/v3/partners/"+partnerId+"/organisations/"+orgId+"/detachedDevices";

        Response response = ApiHelper.givenRequestSpecification()
                .header("authorization", token)
                .when().redirects().follow(false).
                get(path);

        ApiHelper.genericResponseValidation(response,"GET DETACHED DEVICES IN ORG");

        JsonPath jsonPathEvaluator = response.jsonPath();

        List<String> devices = jsonPathEvaluator.get("message.devices.serialNumber");

        return devices;
    }

}
