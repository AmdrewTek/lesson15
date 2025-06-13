import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;


public class StatusTest {
  @Test
  public void checkTotalWithLogs() {
    given()
      .log().all()
      .get("https://selenoid.autotests.cloud/status")
      .then()
      .log().all()
      .body("total", is(5));
  }
  @Test
  public void checkUriBody() {
    given()
      .log().uri()
      .get("https://selenoid.autotests.cloud/status")
      .then()
      .log().body()
      .body("total", is(5))
      .body("browsers.chrome", hasKey("128.0"));
  }

}
