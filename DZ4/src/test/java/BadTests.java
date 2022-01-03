import org.junit.jupiter.api.Test;
import java.io.File;
import static io.restassured.RestAssured.given;

public class BadTests extends BaseTest {

    @Test
    void uploadBadFileTest() {
        given(requestWithAuth)
                .multiPart("image", new File("src/test/resources/justfile.txt"))
                .expect()
                .statusCode(400)
                .when()
                .post("https://api.imgur.com/3/image");
    }

    @Test
    void uploadNoFileTest() {

        given(requestWithAuth)
                .multiPart("image", "")
                .expect()
                .statusCode(400)
                .when()
                .post("https://api.imgur.com/3/image");
    }

    @Test
    void uploadBadUrlTest() {
        given(requestWithAuth)
                .multiPart("image", new String ("https://medidffa.istockphoto.com/photos/pint-of-guinness-picture-id458593013"))
                .expect()
                .statusCode(400)
                .when()
                .post("https://api.imgur.com/3/image");
    }




}
