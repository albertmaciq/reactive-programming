package com.springboot.reactor.operator.range;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class RangeExample {

    public static final Logger LOG = LoggerFactory.getLogger(RangeExample.class);

    public void exampleRange() {

        LOG.info("example Range:".toUpperCase());

        Flux<Integer> ranges = Flux.range(0, 4);

        Flux.just(1, 2, 3, 4)
            .map(i -> i * 2)
            .zipWith(ranges,
                    (one, two) -> String.format("First Flux: %d, Second Flux: %d", one, two))
            .subscribe(LOG::info);
    }
}
