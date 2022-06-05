package ru.netology.data;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.Value;


import java.util.Locale;

import static io.restassured.RestAssured.given;


public class DataGenerator {

    private static final Faker faker = new Faker((new Locale("ru")));
    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public DataGenerator() {
    }

    static void setUpAll(RegistrationInfo user) {
        given()
                .spec(requestSpec)
                .body(user)
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String getRandomLogin(){
        return faker.name().username();
    }

    public static String getRandomPassword(){
        return faker.internet().password();
    }

    public static class Registration {

        public Registration() {
        }

        public static RegistrationInfo getUser(String status) {
            return new RegistrationInfo(getRandomLogin(),getRandomPassword(),status);
        }
        public static RegistrationInfo getRegisteredUser(String status) {
            RegistrationInfo registeredUser = getUser(status);
            setUpAll(registeredUser);
            return registeredUser;
        }
    }
    @Value

    public static class RegistrationInfo {
        String login;
        String password;

        String status;

    }
}