import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.*;

@DisplayName("API тесты")
public class ReqresTest extends TestBase {
  private static final String API_KEY = "reqres-free-v1";

  @Test
  @DisplayName("Лист пользователей")
  void userListTest() {
    given()
      .header("x-api-key", API_KEY)
      .log().all()

      .get("/users?page=2")
      .then()
      .log().all()
      .body("page", is(2))
      .body("total", is(12));
  }

  @Test
  @DisplayName("Успешное создание пользователя")
  void successfulUserCreateTest() {
    String userData = "{\n" +
      "    \"name\": \"morpheus\",\n" +
      "    \"job\": \"leader\"\n" + "}";
    given()
      .header("x-api-key", API_KEY)
      .body(userData)
      .contentType(JSON)
      .log().uri()

      .when()
      .post("/user")

      .then()
      .log().all()
      .statusCode(201)
      .body("id", notNullValue())
      .body("createdAt", notNullValue());
  }

  @Test
  @DisplayName("Неуспешное создание пользователя")
  void unsuccessfuUserCreateTest() {
    String userData = "{2}";
    given()
      .header("x-api-key", API_KEY)
      .body(userData)
      .contentType(JSON)
      .log().uri()

      .when()
      .post("/user")

      .then()
      .log().body()
      .statusCode(400)
      .body(containsString("Bad Request"));
  }

  @Test
  @DisplayName("Успешная регистрация пользователя")
  void successfulUserRegisterTest() {
    String userData = "{\n" +
      "    \"email\": \"eve.holt@reqres.in\",\n" +
      "    \"password\": \"pistol\"\n" + "}";
    given()
      .header("x-api-key", API_KEY)
      .body(userData)
      .contentType(JSON)
      .log().uri()

      .when()
      .post("/register")

      .then()
      .log().all()
      .statusCode(200)
      .body("id", is(4))
      .body("token", is("QpwL5tke4Pnpja7X4"));
  }

  @Test
  @DisplayName("Неуспешная регистрация пользователя")
  void unsuccessfulUserRegisterTest() {
    String userData = "{\"email\": \"sydney@fife\"}";
    given()
      .header("x-api-key", API_KEY)
      .body(userData)
      .contentType(JSON)
      .log().uri()

      .when()
      .post("/register")

      .then()
      .log().all()
      .statusCode(400)
      .body("error", is("Missing password"));
  }

  @Test
  @DisplayName("Удаление")
  void successfulDeleteTest() {
    given()
      .header("x-api-key", API_KEY)
      .log().all()

      .when()
      .delete("/users/2")

      .then()
      .log().all()
      .statusCode(204);
  }
}

