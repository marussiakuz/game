package com.game.service;

import com.game.exception.InvalidPlayerDataException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class PlayersDataValidator {
    private static final int MAX_LENGTH_OF_NAME = 12;
    private static final int MAX_LENGTH_OF_TITLE = 30;
    private static final int MAX_EXPERIENCE = 10_000_000;
    private static final int MIN_YEAR = 2000;
    private static final int MAX_YEAR = 3000;

    private PlayersDataValidator() {
    }

    public static void validateName(String name) {
        if (name.trim().length() == 0 || name.length() > MAX_LENGTH_OF_NAME)
            throw new InvalidPlayerDataException(String.format("players name must not be empty and no longer than %s " +
                    "characters", MAX_LENGTH_OF_NAME));
    }

    public static void validateTitle(String title) {
        if (title.trim().length() == 0 || title.length() > MAX_LENGTH_OF_TITLE)
            throw new InvalidPlayerDataException(String.format("players title must not be empty and no longer than %s " +
                    "characters", MAX_LENGTH_OF_TITLE));
    }

    public static void validateBirthday(long birthday) {
        LocalDate localDate = new Date(birthday).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        if (birthday < 0 || year < MIN_YEAR || year > MAX_YEAR)
            throw new InvalidPlayerDataException("incorrect birthday");
    }

    public static void validateExperience(int experience) {
        if (experience < 0 || experience > MAX_EXPERIENCE)
            throw new InvalidPlayerDataException(String.format("experience must not be null and must be from 0 and %s",
                    MAX_EXPERIENCE));
    }

    public static void validateId(long id) {
        if (id <= 0)
            throw new InvalidPlayerDataException(String.format("invalid id: %s", id));
    }
}
