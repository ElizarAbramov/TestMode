package ru.netology.repo;

import com.github.javafaker.Faker;
import ru.netology.domain.RegistrationInfo;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    private final List<RegistrationInfo> items = new ArrayList<>();


    public void add(RegistrationInfo item) {

        items.add(item);
    }

    public String returnStatus() {

        Faker faker = new Faker();
        String active = null;
        String blocked = null;
        if (faker.bool().bool()) {
            return "active";

        } else {
            return "blocked";
        }
    }
}