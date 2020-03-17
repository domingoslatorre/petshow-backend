package hyve.petshow;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ExampleResourceTest {

    @Test
    public void testDemoAPI() {
        given()
          .when().get("/api")
          .then()
             .statusCode(200);
    }

}