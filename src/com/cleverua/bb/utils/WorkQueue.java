package com.cleverua.bb.utils;

import java.util.Vector;

public class WorkQueue {
        
    private final PoolWorker[] threads;
    private final Vector queue;

    public WorkQueue(int threadsQty, int initialQueueCapacity) {
        queue = new Vector(initialQueueCapacity);
        threads = new PoolWorker[threadsQty];

        for (int i = 0; i < threadsQty; i++) {
            threads[i] = new PoolWorker();
            threads[i].start();
        }
    }

    public void execute(Runnable r) {
        synchronized (queue) {
            queue.addElement(r);
            queue.notify();
        }
    }

    private class PoolWorker extends Thread {
        public void run() {
            Runnable r;

            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException ignored) {}
                    }

                    r = (Runnable) queue.elementAt(0);
                    queue.removeElementAt(0);
                }

                // If we don't catch RuntimeException, the pool could leak threads
                try {
                    r.run();
                } catch (RuntimeException e) {
                    // You might want to log something here
                }
            }
        }
    }
}
