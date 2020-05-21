package by.gusakov.task3.util.builder;

import by.gusakov.task3.entity.Truck;

public class TruckBuilder {

    public static Truck buildTruck(String[] data) {
        return new Truck(Long.parseLong(data[0]), Boolean.parseBoolean(data[1]), Boolean.parseBoolean(data[2]));
    }

}
