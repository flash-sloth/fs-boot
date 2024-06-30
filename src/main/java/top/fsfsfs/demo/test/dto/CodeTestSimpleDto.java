package top.fsfsfs.demo.test.dto;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import top.fsfsfs.basic.base.entity.BaseEntity;

import java.io.Serial;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;

/**
 * 单表标准字段示例表 DTO（写入方法入参）。
 *
 * @author tangyh
 * @since 2024-06-30
 */
@Accessors(chain = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "单表标准字段示例表")
public class CodeTestSimpleDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull(message = "请填写ID", groups = BaseEntity.Update.class)
    @Schema(description = "ID")
    private Long id;

    /**
     * 名称
     */
    @NotEmpty(message = "请填写名称")
    @Size(max = 24, message = "名称长度不能超过{max}")
    @Schema(description = "名称")
    private String name;

    /**
     * 库存
     */
    @Schema(description = "库存")
    private Integer stock;

    /**
     * 商品类型;;
     * #ProductType{ordinary:普通;gift:赠品}
     */
    @Size(max = 16383, message = "商品类型;; #ProductType{ordinary:普通;gift:赠品}长度不能超过{max}")
    @Schema(description = "商品类型;; #ProductType{ordinary:普通;gift:赠品}")
    private String type;

    /**
     * 商品类型2;;
     * #{ordinary:01,普通;gift:02,赠品;}
     */
    @Size(max = 536870911, message = "商品类型2;; #{ordinary:01,普通;gift:02,赠品;}长度不能超过{max}")
    @Schema(description = "商品类型2;; #{ordinary:01,普通;gift:02,赠品;}")
    private String type2;

    /**
     * 学历;@Echo(api = EchoApi.DICTIONARY_ITEM_FEIGN_CLASS,  dictType = EchoDictType.Global.EDUCATION)
     */
    @Size(max = 255, message = "学历;@Echo(api = EchoApi.DICTIONARY_ITEM_FEIGN_CLASS,  dictType = EchoDictType.Global.EDUCATION)长度不能超过{max}")
    @Schema(description = "学历;@Echo(api = EchoApi.DICTIONARY_ITEM_FEIGN_CLASS,  dictType = EchoDictType.Global.EDUCATION)")
    private String type3;

    /**
     * 状态
     */
    @Schema(description = "状态")
    private Boolean state;

    /**
     * 测试
     */
    @Schema(description = "测试")
    private Integer test4;

    /**
     * 时间
     */
    @Schema(description = "时间")
    private LocalDate test5;

    /**
     * 日期
     */
    @Schema(description = "日期")
    private LocalDateTime test6;

    /**
     * 时间戳时间戳
     */
    @Schema(description = "时间戳时间戳")
    private LocalTime datetime1;

    /**
     * 时间戳
     */
    @Schema(description = "时间戳")
    private LocalDateTime datetime2;

    /**
     * 名称
     */
    @Size(max = 255, message = "名称长度不能超过{max}")
    @Schema(description = "名称")
    private String label;

    /**
     * 字符字典;@Echo(api = "top.tangyh.lamp.common.api.DictApi", dictType="GLOBAL_SEX")
     */
    @Size(max = 10, message = "字符字典;@Echo(api = \"top.tangyh.lamp.common.api.DictApi\", dictType=\"GLOBAL_SEX\")长度不能超过{max}")
    @Schema(description = "字符字典;@Echo(api = \"top.tangyh.lamp.common.api.DictApi\", dictType=\"GLOBAL_SEX\")")
    private String test7;

    /**
     * 整形字典;@Echo(api = EchoApi.DICTIONARY_ITEM_FEIGN_CLASS, dictType = EchoDictType.Global.TEST_ADD_DICT)[1-测试 2-新增 aad-haha]
     */
    @Schema(description = "整形字典;@Echo(api = EchoApi.DICTIONARY_ITEM_FEIGN_CLASS, dictType = EchoDictType.Global.TEST_ADD_DICT)[1-测试 2-新增 aad-haha]")
    private Integer test12;

    /**
     * 用户;@Echo(api = EchoApi.POSITION_ID_CLASS)
     */
    @Schema(description = "用户;@Echo(api = EchoApi.POSITION_ID_CLASS)")
    private Long userId;

    /**
     * 组织;@Echo(api = EchoApi.ORG_ID_CLASS)
     */
    @Schema(description = "组织;@Echo(api = EchoApi.ORG_ID_CLASS)")
    private Long orgId;

    /**
     * 小数
     */
    @Digits(integer = 16, fraction = 4, message = "小数整数位长度不能超过{integer}, 小数位长度不能超过{fraction}")
    @Schema(description = "小数")
    private BigDecimal test8;

    /**
     * 浮点2
     */
    @Schema(description = "浮点2")
    private Float test9;

    /**
     * 浮点
     */
    @Digits(integer = 24, fraction = 6, message = "浮点整数位长度不能超过{integer}, 小数位长度不能超过{fraction}")
    @Schema(description = "浮点")
    private BigDecimal test10;

    /**
     * xiao树
     */
    @Digits(integer = 2, fraction = 0, message = "xiao树整数位长度不能超过{integer}, 小数位长度不能超过{fraction}")
    @Schema(description = "xiao树")
    private BigDecimal test11;

}
