package ru.netology.web;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.netology.domain.RegistrationInfo;

import static com.codeborne.selenide.Selenide.$;
import static io.restassured.RestAssured.given;

class AuthTest {

   private String str;
    int num;

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @BeforeAll
    static void setUpAll() {

        given()
                .spec(requestSpec)
                .body(new RegistrationInfo("vasya","password",true))
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    @Test
    public void shouldLoginSuccessfully() {
        $("[class='input__control'][name='login']").setValue(DataGenerator.Registration.generateInfo("ru").getLogin());
        $("[class='input__control'][name='password']").setValue(DataGenerator.Registration.generateInfo("ru").getPassword());
        $("[data-test-id='action-login']").click();

    }

}