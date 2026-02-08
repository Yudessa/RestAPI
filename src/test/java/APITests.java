import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class APITests {
    public final int unxeistingPetId = 2342325;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "https://petstore.swagger.io/v2/";
        given()
                .when()
                .delete(baseURI + "pet/{id}", 2342325);
    }

    @Test
    public void petNotFoundTestBDD() {
        given().when()
                .get(baseURI + "pet/{id}", unxeistingPetId)
                .then()
                .statusCode(404)
                .statusLine("HTTP/1.1 404 Not Found")
                .body("message", equalTo("Pet not found"),
                        "type", equalTo("error"));
    }

    @Test
    public void newPetTest() {
        Integer id = 12;
        String name = "Vasya";
        String status = "sold";

        Map<String, String> request = new HashMap<>();
        request.put("id", id.toString());
        request.put("name", name);
        request.put("status", status);

        given().contentType("application/json")
                .body(request)
                .when()
                .post(baseURI + "pet/")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(id),
                        "name", equalTo(name),
                        "status", equalTo(status));
    }
    @Test
    @DisplayName("Обновление информации о питомце")
    public void updatePetInfo(){
        Integer id = 12;
        String name = "Kot";
        String status = "available";

        Map<String, String> request = new HashMap<>();
        request.put("id", id.toString());
        request.put("name", name);
        request.put("status", status);

        given().contentType("application/json")
                .body(request)
                .when()
                .put(baseURI + "pet")
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(id),
                        "name", equalTo(name),
                        "status", equalTo(status));
    }

}