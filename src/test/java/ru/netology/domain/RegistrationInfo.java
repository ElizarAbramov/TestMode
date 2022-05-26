package ru.netology.domain;

import lombok.Data;

@Data
public class RegistrationInfo {
    private final String login;
    private final String password;

    private final boolean active;
}