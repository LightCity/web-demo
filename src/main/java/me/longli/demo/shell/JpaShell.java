package me.longli.demo.shell;

import me.longli.demo.jpa.entity.Tb1;
import me.longli.demo.jpa.entity.sub.QualityLevelEnum;
import me.longli.demo.jpa.repository.Tb1Repository;
import me.longli.demo.mybatis.dataobject.Tb1DO;
import me.longli.demo.mybatis.mapper.Tb1Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Optional;

@ShellComponent
public class JpaShell {
    @Autowired
    private Tb1Repository tb1Repository;

    @Autowired
    private Tb1Mapper tb1Mapper;

    @ShellMethod(value = "getById", key = "fuck")
    // @ShellMethodAvailability("xxoo")
    public String getById(
            @ShellOption(value = { "--idValue" }) @Min(1L) @Max(10L) Long id) {
        Optional<Tb1> optEntity = tb1Repository.findById(id);
        return optEntity.map(Tb1::toString).orElse("null");
    }

    @ShellMethod(value = "getByIdV2", key = "fuck-mybatis")
    public Tb1DO getByIdV2(
            @ShellOption(value = { "--idValue" }) @Min(1L) @Max(10L) Integer id) {
        Tb1DO bean = tb1Mapper.getById(id);
        return bean;
    }

    @ShellMethod(value = "save")
    public long save(
            @ShellOption LocalDateTime lastUrgeTime,
            @ShellOption @Size(min = 1, max = 32) String data,
            @ShellOption @Min(1) @Max(3) int qualityLevel
    ) {
        System.out.printf("lastUrgeTime=%s, data=%s, qualityLevel=%d%n", lastUrgeTime, data, qualityLevel);
        Tb1 tb1 = new Tb1();
        tb1.setData(data);
        tb1.setLastUrgeTime(lastUrgeTime);
        tb1.setQualityLevel(QualityLevelEnum.getByValue(qualityLevel));
        Tb1 save = tb1Repository.save(tb1);
        System.out.println(save == tb1);
        return save.getId();
    }

    @ShellMethodAvailability({"fuck"})
    public Availability xxoo() {
        //return ++xxoo % 2 == 1 ? Availability.available() : Availability.unavailable("不可操作");
        return Availability.available();
    }
}
