import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class LoginTests {
  private static final String API_KEY = "reqres-free-v1";

  @Test
  public void checkUriBody() {
    String authData = "{\"email\":\"eve.holt@reqres.in\", \"password\": \"cityslicka\"}";


    given()
      .header("x-api-key", API_KEY)
      .body(authData)
      .contentType(JSON)
      .log().all()

      .when()
      .log().all()
      .post("https://reqres.in/api/login")

      .then()
      .log().all()
      //.statusCode(200)
      .body("token", is("QpwL5tke4Pnpja7X4"));
  }
}
