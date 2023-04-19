package com.springboot.reactor;

import com.springboot.reactor.backpressure.BackPressureExample;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

    public static void main(final String[] args) {
        SpringApplication.run(SpringBootReactorApplication.class, args);
    }

    @Override
    public void run(final String... args) {
        // TODO castings methods
        BackPressureExample backPressureExample = new BackPressureExample();
        backPressureExample.backPressureWithLimitRateExample();
    }
}
