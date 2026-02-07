import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
}