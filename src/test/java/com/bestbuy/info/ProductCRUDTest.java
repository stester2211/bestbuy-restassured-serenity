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
public class ProductCRUDTest extends TestBase
{
    static String name="Samsung" + TestUtils.getRandomValue();
    static String type="Mobile";
    static  int price=5;
    static String upc = "567891";
    static String description = "Very good quality";
    static String manufacturer = "Energizer";
    static String model = "MN2400B4A";
    static String url = "string";
    static String image = "http://img.bbystatic.com/BestBuy_US/images/products/4390/43900_sa.jpg";
    static int productId;

    @Steps
    ProductSteps productSteps;

    @Title("This will create a new products")
    @Test
    public void test001()
    {
        ValidatableResponse response= productSteps.createProduct(name,type,price,upc,description,manufacturer,model,url,image)
                .log().all().statusCode(201);
        productId = response.extract().path("id");
    }

    @Title("Verify the products are added")
    @Test
    public void test002()
    {
        //verify if the name was added
        HashMap<String, Object> productMap = productSteps.getProductInfoByName(name);
        productId = (int) productMap.get("id");
        Assert.assertThat(productMap, hasValue(name));
    }

    @Title("This will retrieve product with id")
    @Test
    public void test003() {

        productSteps.getProductById(productId).log().all().statusCode(200);

    }


    @Title("This will update product with id")
    @Test
    public void test004() {

        name = name + "_456";
        //update information
        productSteps.updateProduct(productId, name, type).statusCode(200);

        //verify if the product was updated
        HashMap<String, Object> productMap = productSteps.getProductInfoByName(name);
        Assert.assertThat(productMap, hasValue(name));

    }

    @Title("This will delete product with id")
    @Test
    public void test005() {
        //delete the product
        productSteps.deleteProduct(productId).statusCode(200);

        //verify that the product is deleted
        productSteps.getProductById(productId).statusCode(404);
    }
}
