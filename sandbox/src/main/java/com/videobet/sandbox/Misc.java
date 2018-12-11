package com.videobet.sandbox;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.Timer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Misc {
    static Logger logger = LoggerFactory.getLogger(new Object() {
    }.getClass().getEnclosingClass().getCanonicalName());

    static MetricRegistry registry = new MetricRegistry();
    static Timer arrayListTimer = registry.timer("ArrayList");
    static Timer linkedListTimer = registry.timer("LinkedList");


    public static void main(String[] args) throws InterruptedException {
        final Slf4jReporter reporter = Slf4jReporter.forRegistry(registry)
                .outputTo(logger)
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.NANOSECONDS)
                .build();
        reporter.start(5, TimeUnit.SECONDS);

        logger.error("one two three: {} {} {}", new Object[]{"a", "b", "c"});

//        Misc misc = new Misc();
//        misc.testArrayList();
//        TimeUnit.SECONDS.sleep(5);
//        misc.testLinkedList();
//        TimeUnit.SECONDS.sleep(5);

        logger.info("END");
    }

    private void testArrayList() {
//        List<Integer> list = new ArrayList<>();
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < 10_000_000; i++) {
            Timer.Context context = arrayListTimer.time();
//            list.add(i);
            context.stop();
        }
    }

    private void testLinkedList() {
        List<Integer> list = new LinkedList<>();
        for (int i = 0; i < 10_000_000; i++) {
            Timer.Context context = linkedListTimer.time();
//            list.add(i);
            context.stop();
        }
    }

}
