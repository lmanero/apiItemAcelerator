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
    public void test_get_status_code(){
        RestAssured.baseURI = "http://localhost:8080";
        RequestSpecification httpRequest = RestAssured.given();
        Response response = httpRequest.get("/items/1");
        int statusCode = response.getStatusCode();
        Assert.assertEquals(String.valueOf(statusCode), "200");
    }

}

