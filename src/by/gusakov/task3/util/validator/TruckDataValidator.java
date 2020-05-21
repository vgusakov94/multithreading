package by.gusakov.task3.util.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TruckDataValidator {
    private static final Pattern LINE_PATTERN = Pattern.compile("^(\\d+. (true|false), (true|false))$");

    public static boolean validate(String line) {
        Matcher matcher = LINE_PATTERN.matcher(line);
        return matcher.matches();
    }
}

