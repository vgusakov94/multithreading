package by.gusakov.task3.state;

import by.gusakov.task3.entity.Truck;

public enum TruckState {
    REACHED_BASE(" reached the base."), IN_QUEUE(" in queue."), PROCESSING(" is processing."), FINISHED(" finished.");

    private String state;

    TruckState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public static void nextState(Truck truck) {
        switch (truck.getTruckState()) {
            case REACHED_BASE:
                truck.setTruckState(IN_QUEUE);
                break;
            case IN_QUEUE:
                truck.setTruckState(PROCESSING);
                break;
            case PROCESSING:
                truck.setTruckState(FINISHED);
                break;
            case FINISHED:
                break;
        }
    }
}
