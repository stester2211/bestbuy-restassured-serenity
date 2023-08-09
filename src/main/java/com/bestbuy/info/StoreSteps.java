package com.bestbuy.info;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.constants.Path;
import com.bestbuy.models.StorePojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class StoreSteps
{
    @Step
    public ValidatableResponse createStore(String name, String type, String address, String address2, String city,
                                           String state, String zip){
        StorePojo storePojo = new StorePojo();

        storePojo.setName(name);
        storePojo.setType(type);
        storePojo.setAddress(address);
        storePojo.setAddress2(address2);
        storePojo.setCity(city);
        storePojo.setState(state);
        storePojo.setZip(zip);

        return SerenityRest.given()
                .basePath(Path.STORES)
                .contentType(ContentType.JSON)
                .body(storePojo)
                .when()
                .post()
                .then();
    }

    @Step
    public HashMap<String, Object> getStoreInfoByName(String name)
    {
        String s1 = "data.findAll{it.name == '";
        String s2 = "'}.get(0)";
        return SerenityRest.given()
                .basePath(Path.STORES)
                .queryParam("name", name)
                .when()
                .get()
                .then().log().all().statusCode(200)
                .extract()
                .path(s1 + name + s2);
    }
    @Step("Getting the store information with storeId: {0}")
    public ValidatableResponse getStoreById(int id) {
        return SerenityRest.given().log().all()
                .basePath(Path.STORES)
                .pathParam("id", id)
                .when()
                .get(EndPoints.GET_SINGLE_STORE_BY_ID)
                .then();
    }

    @Step
    public ValidatableResponse updateStore(int id,String name,String type){
        StorePojo storePojo = new StorePojo();

        storePojo.setName(name);
        storePojo.setType(type);


        return SerenityRest.given()
                .basePath(Path.STORES)
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(storePojo)
                .when()
                .patch(EndPoints.UPDATE_STORE_BY_ID)
                .then().log().all();
    }
    @Step
    public ValidatableResponse deleteStore(int id)
    {
        return SerenityRest.given().log().all()
                .basePath(Path.STORES)
                .pathParam("id", id)
                .when()
                .delete(EndPoints.DELETE_STORE_BY_ID)
                .then();
    }

}
