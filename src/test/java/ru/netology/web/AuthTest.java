package ru.netology.web;

import com.codeborne.selenide.Condition;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.RegistrationInfo;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class AuthTest {
    private final Faker faker = new Faker((new Locale("ru")));

    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginSuccessfullyWithValidAndActive() {
        RegistrationInfo activeUser = DataGenerator.Registration.generateInfoForActive();
        $("[class='input__control'][name='login']").setValue(activeUser.getLogin());
        $("[class='input__control'][name='password']").setValue(activeUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[id='root']").shouldHave(Condition.text("Личный кабинет"));
    }

    @Test
    void shouldReportUserIsBlocked() {
        RegistrationInfo blockedUser = DataGenerator.Registration.generateInfoForBlocked();
        $("[class='input__control'][name='login']").setValue(blockedUser.getLogin());
        $("[class='input__control'][name='password']").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldHave((Condition.text("Пользователь заблокирован")));
    }

    @Test
    void shouldReportWhenInvalidLoginForActive() {
        RegistrationInfo activeUser = DataGenerator.Registration.generateInfoForActive();
        $("[class='input__control'][name='login']").setValue((faker.name().firstName()));
        $("[class='input__control'][name='password']").setValue(activeUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldReportWhenInvalidPasswordForActive() {
        RegistrationInfo activeUser = DataGenerator.Registration.generateInfoForActive();
        $("[class='input__control'][name='login']").setValue(activeUser.getLogin());
        $("[class='input__control'][name='password']").setValue(faker.internet().password());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }
}