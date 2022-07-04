package me.longli.demo.event;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class MyEvent extends ApplicationEvent {
    private String eventName;
    private LocalDateTime eventTime;

    public MyEvent(Object source, String eventName, LocalDateTime eventTime) {
        super(source);
        this.eventName = eventName;
        this.eventTime = eventTime;
    }
}
