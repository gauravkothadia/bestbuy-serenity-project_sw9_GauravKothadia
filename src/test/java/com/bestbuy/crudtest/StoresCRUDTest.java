package com.bestbuy.crudtest;

import com.bestbuy.byestbuyinfo.StoreSteps;
import com.bestbuy.constants.Path;
import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Steps;
import net.serenitybdd.annotations.Title;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

@RunWith(SerenityRunner.class)
public class StoresCRUDTest extends TestBase {
    static String storeName = "store"+TestUtils.getRandomValue();
    static String storeType = "BigBox"+TestUtils.getRandomValue();
    static String address1 = "13513 Ridgedale Dr";
    static String address2 = "abc";
    static String city = "Hopkins";
    static String state = "MN";
    static String zip = "55305";
    static double lat = 44.969658;
    static double lng = -93.449539;
    static String hours = "Mon: 10-9; Tue: 10-9; Wed: 10-9; Thurs: 10-9; Fri: 10-9; Sat: 10-9; Sun: 10-8";

    static int storeId;

    @Steps
    StoreSteps storeSteps;

    @BeforeClass
    public static void ini() {
        RestAssured.basePath = Path.STORES;
    }


    @Title("Test001 - Create a store")
    @Test
    public void test001(){
        Map<String, Object> services = new HashMap<>();
        Map<String, Object> service = new HashMap<>();
        service.put("id", 1);
        service.put("name", "Geko Squad Services");
        services.put("service", service);

        ValidatableResponse response = storeSteps.createStore(storeName, storeType, address1, address2, city, state, zip, lat, lng, hours, services);
        response.statusCode(201);
        response.log().body();

        storeId = response.extract().path("id");
        System.out.println("Store Id :" + storeId);
    }
    @Title("Test002 - Get store  info by id and verify its detail")
    @Test
    public void test002() {
        ValidatableResponse response = storeSteps.getStoreInfoById(storeId);
        response.statusCode(200);
        response.extract().path("name");
        response.body("state", equalTo("MN"));
    }

    @Title("Test003 - Updating store info by id and verify updated details")
    @Test
    public void test003() {
        String uname = "Update " + StoresCRUDTest.storeName;
        String address = "Update Prime Add1";
        Map<String, Object> services = new HashMap<>();
        Map<String, Object> service = new HashMap<>();
        service.put("id", 10);
        service.put("name", "Geko Test Squad Services");
        services.put("service", service);

        ValidatableResponse response = storeSteps.updateStoreInfoById(storeId, uname, storeType, address, address2, city, state, zip, lat, lng, hours, services);
        response.statusCode(200);
        response.body("address", equalTo("Update Prime Add1"));
    }

    @Title("Test004 - Deleting store and verify it deleted")
    @Test
    public void test004() {
        storeSteps.deleteStoreById(storeId).statusCode(200);
        storeSteps.getStoreInfoById(storeId).statusCode(404);
    }
}
