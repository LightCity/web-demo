package me.longli.demo.jpa.entity.sub;

public enum QualityLevelEnum {

    LOW(1, "低"),
    MEDIUM(2, "中"),
    HIGH(3, "高")
    ;

    private final Integer value;

    private final String name;

    QualityLevelEnum(Integer value, String name) {
        this.value = value;
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static QualityLevelEnum getByValue(Integer value) {
        switch (value) {
            case 1: return LOW;
            case 2: return MEDIUM;
            case 3: return HIGH;
            default: return null;
        }
    }
}
