package ru.netology.web;

import com.codeborne.selenide.Condition;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.RegistrationInfo;
import ru.netology.repo.Repository;

import java.util.Objects;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;

class AuthTest {
    private static final Repository repository = new Repository();
    private static RegistrationInfo first = new RegistrationInfo(DataGenerator.Registration.generateInfo("ru").getLogin(),
            DataGenerator.Registration.generateInfo("ru").getPassword(), repository.returnStatus());


    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @BeforeAll
    static void setUpAll() {
        repository.add(first);
        given()
                .spec(requestSpec)
                .body(first)
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @Test
    public void shouldLoginSuccessfully() {
        $("[class='input__control'][name='login']").setValue(first.getLogin());
        $("[class='input__control'][name='password']").setValue(first.getPassword());
        $("[data-test-id='action-login']").click();
        if ((Objects.equals(first.getStatus(), "active"))) {

            $("[id='root']").shouldHave(Condition.text("Личный кабинет"));

        } else {
            $("[class='notification__content']").shouldHave((Condition.text("Пользователь заблокирован")));
        }
    }
}