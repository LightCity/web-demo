package me.longli.demo.jpa.entity;

import me.longli.demo.jpa.entity.converter.QualityLevelConverter;
import me.longli.demo.jpa.entity.sub.QualityLevelEnum;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb1", schema = "test")
public class Tb1 {
    @Id
    @Column(name = "id", nullable = false, unique = true)
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name = "last_urge_time", nullable = false)
    private LocalDateTime lastUrgeTime;

    @Column(name = "data", length = 32)
    private String data;

    @Convert(converter = QualityLevelConverter.class)
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