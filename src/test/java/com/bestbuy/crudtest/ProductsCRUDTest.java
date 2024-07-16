package com.bestbuy.crudtest;

import com.bestbuy.byestbuyinfo.ProductSteps;
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

import static org.hamcrest.Matchers.equalTo;

@RunWith(SerenityRunner.class)
public class ProductsCRUDTest extends TestBase {
    static ValidatableResponse response;

    static String name = "Duracell - AAA Batteries (8-Pack)" + TestUtils.getRandomValue();
    static String type = "HardGood" + TestUtils.getRandomValue();
    static double price = 5.49;
    static String upc = TestUtils.getRandomValue();
    static double shipping = 4.99;
    static String description = "Compatible with select electronic devices; AAA size; DURALOCK Power Preserve technology; 4-pack";
    static String manufacturer = "Duracell";
    static String model = "MN2400B4Z" + TestUtils.getRandomValue();
    static String url = "http://www.bestbuy.com/site/duracell-aaa-batteries-4-pack/43900.p?id=1051384074145&skuId=43900&cmp=RMXCC" + TestUtils.getRandomValue();
    static String image = "http://img.bbystatic.com/BestBuy_US/images/products/4390/43900_sa.jpg" + TestUtils.getRandomValue();
    static int productId;

    @Steps
    ProductSteps productSteps;

    @BeforeClass
    public static void ini() {
        RestAssured.basePath = Path.PRODUCTS;
    }

    @Title("Create a product")
    @Test
    public void test01() {
        ValidatableResponse response = productSteps.createProduct(name, type, price, upc, shipping, description, manufacturer, model, url, image);
        response.statusCode(201);

        productId = response.extract().path("id");
        System.out.println(productId);
    }

    @Title("Getting the product info by id and verify details")
    @Test
    public void test02() {
        ValidatableResponse response1 = productSteps.getProductById(productId);
        response1.statusCode(200);
        System.out.println("Name is : "+response1.extract().path("name"));
        response1.body("price",equalTo(5.49F));
    }

    @Title("Updating the product details using it id")
    @Test
    public void test03() {
        String uname = ProductsCRUDTest.name+ "Updated";
        String utype = ProductsCRUDTest.type+ "Updated";
        double uprice = 1600.00;
        ValidatableResponse response1 = productSteps.updateProductInfoById(productId,uname,utype,uprice,upc,shipping,description,manufacturer,model,url,image);
        response1.statusCode(200);
        response1.body("name", equalTo(uname));
    }

    @Title("Test004 - Deleting product and verify it deleted")
    @Test
    public void test04(){
        productSteps.deleteProduct(productId).statusCode(200);
        productSteps.getProductById(productId).statusCode(404);

    }
}