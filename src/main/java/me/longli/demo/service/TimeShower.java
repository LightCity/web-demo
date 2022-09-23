package me.longli.demo.service;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class TimeShower {

    public void showTime() {
        System.out.println(OffsetDateTime.of(LocalDateTime.now(), ZoneOffset.of("+8")));
    }
}
