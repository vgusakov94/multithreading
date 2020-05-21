package by.gusakov.task3.entity;

import by.gusakov.task3.queue.TruckPriorityQueue;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class Terminal {

    private static Terminal terminal;
    private TruckPriorityQueue truckPriorityQueue = TruckPriorityQueue.getInstance();
    private static AtomicBoolean isCreate = new AtomicBoolean(false);

    private static ReentrantLock lock = new ReentrantLock();
    private Semaphore terminalSemaphore;

    public static Terminal getInstance() {
        if (!isCreate.get()) {
            try {
                lock.lock();
                if (terminal == null) {
                    terminal = new Terminal();
                    isCreate.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return terminal;
    }

    public void process() {
        Truck truck = truckPriorityQueue.pollTruck();
        truck.setLoaded(!truck.isLoaded());
    }

    public Semaphore getTerminalSemaphore() {
        return terminalSemaphore;
    }

    public void setTerminalSemaphore(int numberOfTerminals) {
        this.terminalSemaphore = new Semaphore(numberOfTerminals, true);
    }
}
