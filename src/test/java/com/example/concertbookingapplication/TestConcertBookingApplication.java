package com.example.concertbookingapplication;

import org.springframework.boot.SpringApplication;

public class TestConcertBookingApplication {

    public static void main(String[] args) {
        SpringApplication.from(ConcertBookingApplication::main).with(TestcontainersConfiguration.class).run(args);
    }

}
