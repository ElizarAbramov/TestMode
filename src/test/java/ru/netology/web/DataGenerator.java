package ru.netology.web;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;
import ru.netology.domain.RegistrationInfo;


import java.util.Locale;

import static io.restassured.RestAssured.given;

@UtilityClass
public class DataGenerator {

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    static void setUpAll(RegistrationInfo user) {
        given()
                .spec(requestSpec)
                .body(user)
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }


    @UtilityClass
    public static class Registration {
        public static RegistrationInfo generateInfoForActive() {
            Faker faker = new Faker(new Locale("ru"));
            RegistrationInfo active = new RegistrationInfo(faker.name().firstName(), Integer.toString(faker.number().numberBetween(10000000, 99999999)), "active");
            setUpAll(active);
            return active;
        }

        public static RegistrationInfo generateInfoForBlocked() {
            Faker faker = new Faker(new Locale("ru"));
            RegistrationInfo blocked = new RegistrationInfo(faker.name().firstName(), Integer.toString(faker.number().numberBetween(10000000, 99999999)), "blocked");
            setUpAll(blocked);
            return blocked;
        }
    }
}