package com.videobet.sandbox;

import java.util.Date;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class ActionSequence {
    private ScheduledFuture<?> future;
    private final AtomicInteger counter = new AtomicInteger(0);
    private final ScheduledExecutorService executor = Executors.newScheduledThreadPool(5);
    private static final int[] DELAYS = { 0, 1, 2, 4, 8, 15, 30, 60 };

    public int getNextDelay() {
        return DELAYS[Math.min(counter.getAndIncrement(), DELAYS.length - 1)];
    }

    public void setFuture(ScheduledFuture<?> future) {
        cancel();
        this.future = future;
    }

    public void clear() {
        cancel();
        counter.set(0);
        System.out.println("[" + new Date() + "]" + "clear");
    }

    private void cancel() {
        if (future != null && !future.isDone()) {
            future.cancel(false);
        }
    }

    public boolean isNextRequestAvailable() {
        return counter.get() == 0 || future == null || future.isDone();
    }

    private void nextRequest() {
        setFuture(executor.schedule(() -> {
            System.out.println("[" + new Date() + "]" + "nextRequest");
            nextRequest();
        } , getNextDelay(), TimeUnit.SECONDS));
    }

    public boolean start() {
        if (isNextRequestAvailable()) {
            nextRequest();
            return true;
        }
        return false;
    }
}
