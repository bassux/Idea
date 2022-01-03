import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public abstract class BaseTest {

    static Properties properties = new Properties();
    static String token;
    static String username;
    static ResponseSpecification positiveResponseSpecification;
    static RequestSpecification requestWithAuth;
    static RequestSpecification requestWithAuthAndImage;
    static RequestSpecification requestWithAuthAndURLImage;

    @BeforeAll
    static void beforAll() {

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.filters(new AllureRestAssured());
        RestAssured.baseURI = "https://api.imgur.com/3";
        getProperties();
        token = properties.getProperty("token");
        username = properties.getProperty("username");


        positiveResponseSpecification = new ResponseSpecBuilder()
                .expectBody("status", equalTo(200))
                .expectBody("success", is(true))
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .build();

        requestWithAuth = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .build();

        requestWithAuthAndImage = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .addMultiPart("image", new File("src/test/resources/redcar.jpg"))
                .build();
        requestWithAuthAndURLImage = new RequestSpecBuilder()
                .addHeader("Authorization", token)
                .addMultiPart("image", new String ("https://media.istockphoto.com/photos/pint-of-guinness-picture-id458593013"))
                .build();


    }


    private static void getProperties() {

        try (InputStream output = new FileInputStream("src/test/resources/application.properties")){
            properties.load(output);
        }
        catch (IOException e){
         e.printStackTrace();
        }
    }

}
