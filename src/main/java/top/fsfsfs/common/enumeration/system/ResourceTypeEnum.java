package top.fsfsfs.common.enumeration.system;

import com.mybatisflex.annotation.EnumValue;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 资源类型 ;[10-菜单  20-按钮 30-字段 40-数据]
 *
 * @author zuihou
 * @since 2021/3/12 21:20
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "资源类型-枚举")
public enum ResourceTypeEnum {
    /**
     * 菜单
     */
    SYSTEM("10", "子系统"),
    /**
     * 菜单
     */
    MENU("20", "菜单"),
    /**
     * 按钮
     */
    BUTTON("30", "按钮"),
    /**
     * 字段
     */
    FIELD("40", "字段"),

    /**
     * 数据权限
     */
    DATA("50", "数据");

    /**
     * 资源类型
     */
    @EnumValue
    private String code;

    /**
     * 资源描述
     */
    private String desc;

    @Schema(description = "编码", allowableValues = "20,30,40,50,60", example = "20")
    public String getCode() {
        return this.code;
    }

    public boolean eq(String val) {
        return this.getCode().equalsIgnoreCase(val);
    }

}
