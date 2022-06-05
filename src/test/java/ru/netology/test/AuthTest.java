package ru.netology.test;

import com.codeborne.selenide.Condition;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static ru.netology.data.DataGenerator.Registration.getRegisteredUser;
import static ru.netology.data.DataGenerator.Registration.getUser;
import static ru.netology.data.DataGenerator.getRandomLogin;
import static ru.netology.data.DataGenerator.getRandomPassword;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

class AuthTest {
    @BeforeEach
    public void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldLoginSuccessfullyWithRegisteredAndActive() {
        var registeredUser = getRegisteredUser("active");
        $("[class='input__control'][name='login']").setValue(registeredUser.getLogin());
        $("[class='input__control'][name='password']").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[id='root']").shouldHave(Condition.text("Личный кабинет"));
    }

    @Test
    void shouldGetErrorNotRegistered() {
        var notRegisteredUser = getUser("active");
        $("[class='input__control'][name='login']").setValue(notRegisteredUser.getLogin());
        $("[class='input__control'][name='password']").setValue(notRegisteredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }


    @Test
    void shouldReportUserIsBlocked() {
        var blockedUser = getRegisteredUser("blocked");
        $("[class='input__control'][name='login']").setValue(blockedUser.getLogin());
        $("[class='input__control'][name='password']").setValue(blockedUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[class='notification__content']").shouldHave((Condition.text("Пользователь заблокирован")));
    }

    @Test
    void shouldReportWhenInvalidLoginForActive() {
        var registeredUser = getRegisteredUser("active");
        var wrongLogin = getRandomLogin();
        $("[class='input__control'][name='login']").setValue(wrongLogin);
        $("[class='input__control'][name='password']").setValue(registeredUser.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }

    @Test
    void shouldReportWhenInvalidPasswordForActive() {
        var registeredUser = getRegisteredUser("active");
        var wrongPassword = getRandomPassword();
        $("[class='input__control'][name='login']").setValue(registeredUser.getLogin());
        $("[class='input__control'][name='password']").setValue(wrongPassword);
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification']").shouldHave(Condition.text("Неверно указан логин или пароль"));
    }
}