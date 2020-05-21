package by.gusakov.task3.util.parser;

public class TruckStringParser {
    private static final String DELIMITER = ",\\s*";

    public String[] parseLine(String line) {
        String[] arrayOfData = line.split(DELIMITER);
        return arrayOfData;
    }
}
