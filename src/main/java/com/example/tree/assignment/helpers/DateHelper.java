package com.example.tree.assignment.helpers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateHelper {
	private static final String PATTERN_DATE = "dd.MM.yyyy";

    public static LocalDate getLocalDateForString(final String string) {
        return LocalDate.parse(string,
                DateTimeFormatter.ofPattern(PATTERN_DATE));
    }
}
