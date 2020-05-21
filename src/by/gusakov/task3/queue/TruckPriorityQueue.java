package by.gusakov.task3.queue;

import by.gusakov.task3.entity.Truck;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class TruckPriorityQueue {

    private static TruckPriorityQueue queue;
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean isCreate = new AtomicBoolean(false);

    private final PriorityQueue<Truck> priorityQueue = new PriorityQueue<>(Comparator.comparing(Truck::isPerishableCargo).reversed());

    public static TruckPriorityQueue getInstance() {
        if (!isCreate.get()) {
            try {
                lock.lock();
                if (queue == null) {
                    queue = new TruckPriorityQueue();
                    isCreate.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return queue;
    }

    public void addTruck(Truck truck) {
        lock.lock();
        priorityQueue.add(truck);
        lock.unlock();
    }

    public boolean isEmpty() {
        lock.lock();
        boolean empty = priorityQueue.isEmpty();
        lock.unlock();
        return empty;
    }


    public Truck pollTruck() {
        lock.lock();
        Truck truck = priorityQueue.poll();
        lock.unlock();
        return truck;
    }

    public Truck peekTruck() {
        lock.lock();
        Truck truck = priorityQueue.peek();
        lock.unlock();
        return truck;
    }
}
