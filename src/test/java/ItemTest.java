import com.google.gson.JsonObject;
import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;

public class ItemTest {

    @Test
    public void test_get_all_status_code(){
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/items");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(String.valueOf(statusCode), "200");
    }

    @Test
    public void test_get_item_not_exist_status_code(){
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/items/1");
        String body = response.getBody().asString();
        Assert.assertEquals(body.contains("ERROR"), true);
    }

    @Test
    public void test_post_item_empty_body_error(){
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.post("/items");
        String body = response.getBody().asString();
        Assert.assertEquals(body.contains("ERROR"), true);
    }

    @Test
    public void test_post_item(){
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        JsonObject item = new JsonObject();
        item.addProperty("title", "Item de test - No Ofertar");
        item.addProperty("description", "Item:  Ray-Ban WAYFARER Gloss Black RB2140 901  Model: RB2140.");
        httpRequest.body(item.toString());
        Response response = httpRequest.post("/items");
        String bodyResponse = response.getBody().asString();
        Assert.assertEquals(bodyResponse.contains("SUCCESS"), true);
    }


}

