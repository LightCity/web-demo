package me.longli.demo.shell;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.longli.demo.aoptest.service.ServiceB;
import me.longli.demo.service.HelloService;
import org.jobrunr.jobs.JobId;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@ShellComponent
public class JobShell {

    @Autowired
    private JobScheduler jobScheduler;

    @Autowired
    private HelloService helloService;

    @Autowired
    private ApplicationContext applicationContext;

    @ShellMethod(value = "showBean", key = "showBean")
    public String showBean(@ShellOption(value = { "--beanName" }) String beanName) {
        JobId id = jobScheduler.enqueue(() -> {
            // TODO 这个任务能够序列化到数据库吗？
            // TODO 似乎只有最后一句代码被序列化了
            Object bean = applicationContext.getBean(beanName);
            ObjectMapper mapper = new ObjectMapper();
            System.out.println(mapper.writeValueAsString(bean));
        });
        return id.asUUID().toString();
    }

    @ShellMethod(value = "showTime", key = "showTime")
    public String showTime() {
        JobId id = jobScheduler.enqueue(() -> {
            LocalDateTime now = LocalDateTime.now();
            System.out.println(now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        });
        return id.asUUID().toString();
    }

    @ShellMethod(value = "sayHello", key = "sayHello")
    public String sayHello(
            @ShellOption(value = { "--name" }) String name) {
        JobId id = jobScheduler.enqueue(() -> {
            ServiceB serviceB = (ServiceB) applicationContext.getBean("serviceBImpl");
            serviceB.sayB();
            helloService.sayHello(name);
        });
        return id.toString();
    }
}
