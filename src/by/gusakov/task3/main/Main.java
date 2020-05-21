package by.gusakov.task3.main;

import by.gusakov.task3.entity.LogisticBase;
import by.gusakov.task3.entity.Truck;
import by.gusakov.task3.exception.ProjectDataException;
import by.gusakov.task3.util.builder.TruckBuilder;
import by.gusakov.task3.util.parser.TruckStringParser;
import by.gusakov.task3.util.reader.TruckDataReader;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class Main {

    private static final String DATA_PATH = "/data/data.txt";
    private static final int NUMBER_OF_TERMINALS = 2;

    public static void main(String[] args) throws ProjectDataException, URISyntaxException, InterruptedException {
        LogisticBase logisticBase = LogisticBase.getInstance();
        logisticBase.setNumberOfTerminals(NUMBER_OF_TERMINALS);

        TruckDataReader truckDataReader = new TruckDataReader();
        List<String> trucksStrings = truckDataReader.readFromDataFile(DATA_PATH);
        TruckStringParser truckStringParser = new TruckStringParser();
        List<Truck> trucks = new ArrayList<>();

        for (String string : trucksStrings) {
            String[] truckDataArray = truckStringParser.parseLine(string);
            trucks.add(TruckBuilder.buildTruck(truckDataArray));
        }

        for (Truck truck : trucks) {
            new Thread(truck).start();
            TimeUnit.MILLISECONDS.sleep(100);
        }

    }
}
