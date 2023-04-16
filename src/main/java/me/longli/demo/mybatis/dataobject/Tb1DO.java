package me.longli.demo.mybatis.dataobject;

import me.longli.demo.jpa.entity.sub.QualityLevelEnum;

import java.time.LocalDateTime;

public class Tb1DO {

    private Long id;

    private LocalDateTime lastUrgeTime;

    private String data;

    private QualityLevelEnum qualityLevel;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getLastUrgeTime() {
        return lastUrgeTime;
    }

    public void setLastUrgeTime(LocalDateTime lastUrgeTime) {
        this.lastUrgeTime = lastUrgeTime;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public QualityLevelEnum getQualityLevel() {
        return qualityLevel;
    }

    public void setQualityLevel(QualityLevelEnum qualityLevel) {
        this.qualityLevel = qualityLevel;
    }

    @Override
    public String toString() {
        return "Tb1{" +
                "id=" + id +
                ", lastUrgeTime=" + lastUrgeTime +
                ", data='" + data + '\'' +
                ", qualityLevel=" + qualityLevel +
                '}';
    }
}