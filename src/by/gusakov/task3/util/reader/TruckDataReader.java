package by.gusakov.task3.util.reader;

import by.gusakov.task3.exception.ProjectDataException;
import by.gusakov.task3.util.validator.TruckDataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TruckDataReader {
    private static final Logger logger = LogManager.getLogger(TruckDataReader.class);

    public List<String> readFromDataFile (String pathToDataFile) throws ProjectDataException, URISyntaxException {
        if (pathToDataFile == null){
            throw new ProjectDataException("Null path to file in read method.");
        }
        List<String> truckList;
        Path path = Paths.get(getClass().getResource(pathToDataFile).toURI());
        try (Stream<String> lineStream = Files.newBufferedReader(path).lines()){
            truckList = lineStream.collect(Collectors.toList());
            if (truckList.isEmpty()){
                logger.warn("List is empty! Please, check your data!");
            }

        }catch (IOException e){
            throw new ProjectDataException("Error in reading file: ", e);
        }
        List<String> validTruckList = new ArrayList<>();
        for (String s : truckList){
            if (TruckDataValidator.validate(s)){
                validTruckList.add(s);
            }else {
                logger.error("Line \"" + s + "\" is not valid. The application does not use this data.");
            }
        }
        return validTruckList;
    }
}
