package top.fsfsfs.main.generator.enumeration;

import com.mybatisflex.annotation.EnumValue;

/**
 * 基类类型; [0-实体  1-Mapper 2-Service 3-Controller]
 *
 * @author tangyh
 * @since 2024/7/1 15:46
 */
public enum ClassTypeEnum {
    /** 基类类型 */
    ENTITY("实体", "0"),
    VO("VO", "1"),
    MAPPER("Mapper", "2"),
    SERVICE("Service", "3"),
    CONTROLLER("Controller", "4");

    private final String value;
    private final String desc;


    ClassTypeEnum(String desc, String value) {
        this.desc = desc;
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    @EnumValue
    public String getValue() {
        return value;
    }
}
