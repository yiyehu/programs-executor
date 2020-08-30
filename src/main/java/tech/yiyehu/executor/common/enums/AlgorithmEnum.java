package tech.yiyehu.executor.common.enums;

public enum AlgorithmEnum {

    /**
     * 哈欠检测
     */
    YAWNING_DETECTION(1),

    /**
     * 疲劳检测
     */
    DROWSY_DETECTION(2),

    /**
     * 车牌检测
     */
    PLATE_DETECTION(3),

    /**
     * 背景建模
     */
    BACKGROUND_MODELING(4),

    /**
     * 危险逼近
     */
    DANGER_DETECTION(5);

    private int id;

    AlgorithmEnum(int value) {
        this.id = value;
    }

    public int getId() {
        return id;
    }

    public AlgorithmEnum getEnum(int id) {
        for (AlgorithmEnum element : AlgorithmEnum.values()) {
            if (id == element.id) {
                return element;
            }
        }
        return null;
    }
}
