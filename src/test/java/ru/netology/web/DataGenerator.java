package ru.netology.web;

import com.github.javafaker.Faker;
import lombok.experimental.UtilityClass;
import ru.netology.domain.RegistrationInfo;

import java.util.Locale;

@UtilityClass
public class DataGenerator {

    @UtilityClass
    public static class Registration {
        public static RegistrationInfo generateInfo(String locale) {
            Faker faker = new Faker(new Locale(locale));
            return new RegistrationInfo(faker.name().firstName(), Integer.toString(faker.number().numberBetween(10000000,99999999)),faker.bool().bool());
        }
    }
}