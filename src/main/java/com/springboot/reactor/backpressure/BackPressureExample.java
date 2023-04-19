package com.springboot.reactor.backpressure;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

public class BackPressureExample {

    public static final Logger LOG = LoggerFactory.getLogger(BackPressureExample.class);

    public void backPressureExample() {

        LOG.info("backPressure Example:".toUpperCase());

        Flux.range(1, 10)
            .log()
            .subscribe(new Subscriber<>() {

                private Subscription subscription;
                private final Integer limit = 5;
                private Integer consumed = 0;

                @Override
                public void onSubscribe(final Subscription subscription) {
                    this.subscription = subscription;
                    subscription.request(limit);
                }

                @Override
                public void onNext(final Integer integer) {
                    LOG.info(integer.toString());
                    consumed++;
                    if (consumed.equals(limit)) {
                        consumed = 0;
                        subscription.request(limit);
                    }
                }

                @Override
                public void onError(final Throwable throwable) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onComplete() {
                    // TODO Auto-generated method stub
                }
            });
    }

    public void backPressureWithLimitRateExample() {

        LOG.info("backPressure With LimitRate Example:".toUpperCase());

        Flux.range(1, 10)
            .log()
            .limitRate(5)
            .subscribe();
    }
}
