import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import static io.restassured.RestAssured.given;

public class FavoriteTests extends BaseTest {

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
    void imageFavoriteTest() {
        given(requestWithAuth, positiveResponseSpecification)
                .post("https://api.imgur.com/3/image/{imageHash}/favorite", uploadedImageId);
        tearDown();
    }

    @Test
    void imageFavoriteWrongHashTest() {

        given(requestWithAuth)
                .expect()
                .statusCode(404)
                .when()
                .post("https://api.imgur.com/3/image/2134/favorite");

    }

    @AfterEach
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
