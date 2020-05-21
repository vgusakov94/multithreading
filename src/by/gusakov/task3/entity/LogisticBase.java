package by.gusakov.task3.entity;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

public class LogisticBase {

    private static LogisticBase base;
    private static ReentrantLock lock = new ReentrantLock();
    private static AtomicBoolean isCreate = new AtomicBoolean(false);
    private static Terminal terminal = Terminal.getInstance();

    private LogisticBase() {
    }

    public static LogisticBase getInstance() {
        if (!isCreate.get()) {
            try {
                lock.lock();
                if (base == null) {
                    base = new LogisticBase();
                    isCreate.set(true);
                }
            } finally {
                lock.unlock();
            }
        }
        return base;
    }

    public void setNumberOfTerminals(int numberOfTerminals) {
        try {
            lock.lock();
            terminal.setTerminalSemaphore(numberOfTerminals);
        } finally {
            lock.unlock();
        }
    }

    public Terminal getTerminal() {
        return terminal;
    }
}
