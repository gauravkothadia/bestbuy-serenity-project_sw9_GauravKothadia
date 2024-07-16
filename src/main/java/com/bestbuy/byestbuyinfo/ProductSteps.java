package com.bestbuy.byestbuyinfo;

import com.bestbuy.constants.EndPoints;
import com.bestbuy.model.ProductPojo;
import io.restassured.response.ValidatableResponse;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.rest.SerenityRest;

public class ProductSteps {

    @Step("Creating product with name: {0}, type: {1}, price: {2}, upc: {3}, shipping: {4}, description: {5}, manufacturer: {6}, model: {7}, Url: {8}, Image: {9}")
    public ValidatableResponse createProduct(String name, String type, double price,
                                             String upc, double shipping, String description,
                                             String manufacturer, String model, String url, String image) {
        ProductPojo productPojo = ProductPojo.getProductPojo(name, type, price, upc, shipping,
                description, manufacturer, model, url, image);
        return SerenityRest.given()
                .header("Content-Type", "application/json")
                .when()
                .body(productPojo)
                .post()
                .then();
    }

    @Step("Getting the product information with product id : {0}")
    public ValidatableResponse getProductById(int productId) {
        return SerenityRest.given()
                .pathParam("id", productId)
                .when()
                .get(EndPoints.GET_SINGLE_PRODUCT_BY_ID)
                .then();
    }

    @Step("Updating product information with productId: {0}, name: {1}, type: {2}, price: {3}, shipping: {4}, upc: {5}, description: {6}, manufacturer: {7}, model: {8}, url: {9}, image: {10}")
    public ValidatableResponse updateProductInfoById(int productId, String name, String type, double price,  String upc, double shipping,String description, String manufacturer, String model, String url, String image) {

        ProductPojo productPojo = ProductPojo.getProductPojo(name, type, price, upc, shipping, description, manufacturer, model, url, image);

        return SerenityRest.given()
                .pathParam("id", productId)
                .header("Content-Type", "application/json")
                .when()
                .body(productPojo)
                .patch(EndPoints.UPDATE_PRODUCT_BY_ID)
                .then();
    }

    @Step("Deleting product information with productID: {0}")
    public ValidatableResponse deleteProduct(int productId) {

        return SerenityRest.given()
                .pathParam("id", productId)
                .when()
                .delete(EndPoints.DELETE_PRODUCT_BY_ID)
                .then();
    }

}
