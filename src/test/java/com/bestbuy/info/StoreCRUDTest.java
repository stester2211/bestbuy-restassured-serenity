package com.bestbuy.info;

import com.bestbuy.testbase.TestBase;
import com.bestbuy.utils.TestUtils;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class StoreCRUDTest extends TestBase
{
    static int storesID;
    static String name = "Sk Grocer" + TestUtils.getRandomValue();
    static String type = "Grocery";
    static String address = "Wyandotte";
    static String address2 = "Windsor";
    static String city = "Windsor";
    static String state = "Ontario";
    static String zip = "112233";
    static int lat = 123456;
    static int lng = 7894561;
    static String hours = "24";

    @Steps
    StoreSteps storeSteps;

    @Title("This will create a new Store")
    @Test
    public void test001() {

        ValidatableResponse response =  storeSteps.createStore(name,type,address,address2,city,state,zip)
                .log().all().statusCode(201);
        storesID = response.extract().path("id");
    }

    @Title("Verify if the store was added")
    @Test
    public void test002() {

        HashMap<String, Object> storeMap = storeSteps.getStoreInfoByName(name);
        storesID = (int) storeMap.get("id");  //for grab  - get id
        Assert.assertThat(storeMap, hasValue(name));  // verify


    }
    @Title("This will retrieve store with id")
    @Test
    public void test003() {

        storeSteps.getStoreById(storesID).log().all().statusCode(200);

    }

    @Title("Update the Store information and verify the Store information")
    @Test
    public void test004() {

        name = name + "_123";
        storeSteps.updateStore(storesID,name,type).statusCode(200);
        HashMap<String, Object> storeMap = storeSteps.getStoreInfoByName(name);
        Assert.assertThat(storeMap, hasValue(name));


    }
    @Title("Delete the Store and verify if the Store is deleted!")
    @Test
    public void test005() {
        storeSteps.deleteStore(storesID).statusCode(200);
        storeSteps.getStoreById(storesID).statusCode(404);
    }
}
