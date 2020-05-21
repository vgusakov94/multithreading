package by.gusakov.task3.entity;

import by.gusakov.task3.queue.TruckPriorityQueue;
import by.gusakov.task3.state.TruckState;

import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Truck implements Runnable {
    private static final Logger logger = LogManager.getLogger(Truck.class);

    private long id;
    private boolean perishableCargo;
    private boolean loaded;
    private TruckState truckState;

    public Truck(long id, boolean loaded, boolean perishableCargo) {
        this.id = id;
        this.loaded = loaded;
        this.perishableCargo = perishableCargo;
        truckState = TruckState.REACHED_BASE;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isPerishableCargo() {
        return perishableCargo;
    }

    public void setPerishableCargo(boolean perishableCargo) {
        this.perishableCargo = perishableCargo;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }

    public TruckState getTruckState() {
        return truckState;
    }

    public void setTruckState(TruckState truckState) {
        this.truckState = truckState;
    }

    public void nextState() {
        TruckState.nextState(this);
        logTruckStatus();
    }

    public void logTruckStatus() {
        logger.info(this + truckState.getState());
    }


    @Override
    public void run() {
        try {
            if (truckState.equals(TruckState.FINISHED)) {
                logTruckStatus();
            } else {
                logTruckStatus();
                TruckPriorityQueue truckPriorityQueue = TruckPriorityQueue.getInstance();
                truckPriorityQueue.addTruck(this);
                TimeUnit.SECONDS.sleep(1);
                nextState();

                LogisticBase logisticBase = LogisticBase.getInstance();
                Terminal terminal = logisticBase.getTerminal();

                while (!this.equals(truckPriorityQueue.peekTruck())) {
                    TimeUnit.MILLISECONDS.sleep(100);
                }

                terminal.getTerminalSemaphore().acquire();
                TimeUnit.MILLISECONDS.sleep(500);
                terminal.process();
                nextState();
                TimeUnit.SECONDS.sleep(1);
                nextState();
                terminal.getTerminalSemaphore().release();
            }
        } catch (InterruptedException e) {
            logger.error("Truck thread was interrupted: ", e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Truck truck = (Truck) o;

        if (id != truck.id) {
            return false;
        }
        return perishableCargo == truck.perishableCargo;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (perishableCargo ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Truck {");
        sb.append("id=").append(id);
        sb.append("; perishable-").append(perishableCargo);
        sb.append("; loaded-").append(loaded);
        sb.append('}');
        return sb.toString();
    }
}
