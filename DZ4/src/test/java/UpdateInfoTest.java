import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import org.junit.jupiter.api.BeforeEach;
import java.io.File;

public class UpdateInfoTest  extends BaseTest{
    String uploadedImageId;

    @BeforeEach
    void setUp() {
        uploadedImageId = given()
                .header("Authorization", token)
                .body(new File("src/test/resources/redcar.jpg"))
                .when()
                .post("/image")
                .jsonPath()
                .get("data.deletehash");
    }


    @Test
    void updateImageInfoTest() {

        given(requestWithAuth)
                .multiPart("title", "123")
                .expect()
                .statusCode(200)
                .body("success", equalTo(true))
                .when()
                .post("https://api.imgur.com/3/image/{imageDeleteHash}", uploadedImageId);

    }

    void tearDown() {
        given()
                .headers("Authorization", token)
                .when()
                .delete("https://api.imgur.com/3/image/{deleteHash}", uploadedImageId)
                .prettyPeek()
                .then()
                .statusCode(200);
    }


}
