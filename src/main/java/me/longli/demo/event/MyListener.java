package me.longli.demo.event;

import lombok.NonNull;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class MyListener implements ApplicationListener<MyEvent> {

    @EventListener({MyEvent.class})
    @Override
    public void onApplicationEvent(@NonNull MyEvent event) {
        System.out.println("MyListener#onApplicationEvent receive event: " + event);
    }

    @EventListener({MyEvent.class})
    public void fuck(@NonNull MyEvent event) {
        System.out.println("MyListener#fuck receive event: " + event);
    }
}
