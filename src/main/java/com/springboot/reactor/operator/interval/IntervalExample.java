package com.springboot.reactor.operator.interval;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

public class IntervalExample {

    public static final Logger LOG = LoggerFactory.getLogger(IntervalExample.class);

    public void exampleInterval() {
        // In this example you cannot see anything because main thread is faster than this new thread,
        // To sump up, the main ends before the ranges thread even starts.

        LOG.info("example Interval".toUpperCase());

        Flux<Integer> ranges = Flux.range(1, 12);
        Flux<Long> delay = Flux.interval(Duration.ofSeconds(1));

        ranges.zipWith(delay, (rang, del) -> rang)
            .doOnNext(i -> LOG.info(i.toString()))
            //.blockLast(); // you can see the logs with this instruction who blocks the process
            .subscribe();
    }

    public void exampleDelayElements() {

        LOG.info("example Delay Elements".toUpperCase());

        Flux<Integer> ranges = Flux.range(1, 12)
            .delayElements(Duration.ofSeconds(1))
            .doOnNext(i -> LOG.info(i.toString()));

        //ranges.blockLast(); // you can see the logs with this instruction who blocks the process
        ranges.subscribe();
    }

    public void exampleInfiniteInterval() throws InterruptedException {

        LOG.info("example Infinite Interval".toUpperCase());

        CountDownLatch latch = new CountDownLatch(1);

        Flux.interval(Duration.ofSeconds(1))
            .doOnTerminate(latch::countDown)
            .flatMap(i -> {
                if (i >= 5) {
                    return Flux.error(new InterruptedException("Hold on! Just to 5."));
                }
                return Flux.just(i);
            })
            .map(i -> "Hello " + i)
            .subscribe(LOG::info, error -> LOG.error(error.getMessage()));

        latch.await();
    }

    public void exampleRetryInfiniteInterval() throws InterruptedException {

        LOG.info("example Retry Infinite Interval".toUpperCase());

        CountDownLatch latch = new CountDownLatch(1);

        Flux.interval(Duration.ofSeconds(1))
            .doOnTerminate(latch::countDown)
            .flatMap(i -> {
                if (i >= 5) {
                    return Flux.error(new InterruptedException("Hold on! Just to 5."));
                }
                return Flux.just(i);
            })
            .map(i -> "Hello " + i)
            .retry(2)
            .subscribe(LOG::info, error -> LOG.error(error.getMessage()));

        latch.await();
    }
}