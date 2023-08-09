package com.bestbuy.info;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.constants.Path;
import com.bestbuy.models.ProductPojo;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;

public class ProductSteps
{
    @Step
    public ValidatableResponse createProduct(String name, String type, int price, String upc, String description, String manufacturer,
                                             String model, String url, String image)
    {
        ProductPojo productPojo = new ProductPojo();

        productPojo.setName(name);
        productPojo.setType(type);
        productPojo.setPrice(price);
        productPojo.setUpc(upc);
        productPojo.setDescription(description);
        productPojo.setManufacturer(manufacturer);
        productPojo.setModel(model);
        productPojo.setUrl(url);
        productPojo.setImage(image);

        return SerenityRest.given()
                .basePath(Path.PRODUCTS)
                .contentType(ContentType.JSON)
                .body(productPojo)
                .when()
                .post()
                .then();

    }

    @Step("Getting the Product information with name : {0}")
    public HashMap<String, Object> getProductInfoByName(String name)
    {
        String s1 = "data.findAll{it.name == '";
        String s2 = "'}.get(0)";
        return SerenityRest.given()
                .basePath(Path.PRODUCTS)
                .queryParam("name", name)
                .when()
                .get()
                .then().log().all().statusCode(200)
                .extract()
                .path(s1 + name + s2);
    }


    @Step("Getting the product information with productId: {0}")
    public ValidatableResponse getProductById(int id) {
        return SerenityRest.given().log().all()
                .basePath(Path.PRODUCTS)
                .pathParam("id", id)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then();
    }


    @Step("Updating product information with id: {0}, name {1}, type {2}")
    public ValidatableResponse updateProduct(int id, String name, String type) {

        ProductPojo productPojo = new ProductPojo();

        productPojo.setName(name);
        productPojo.setType(type);

        return SerenityRest.given()
                .basePath(Path.PRODUCTS)
                .contentType(ContentType.JSON)
                .pathParam("id", id)
                .body(productPojo)
                .when()
                .patch(EndPoints.UPDATE_PRODUCT_BY_ID)
                .then().log().all();

    }

    @Step("Deleting product information with id: {0}")
    public ValidatableResponse deleteProduct(int id) {
        return SerenityRest.given().log().all()
                .basePath(Path.PRODUCTS)
                .pathParam("id", id)
                .when()
                .delete(EndPoints.DELETE_PRODUCT_BY_ID)
                .then();
    }
}
