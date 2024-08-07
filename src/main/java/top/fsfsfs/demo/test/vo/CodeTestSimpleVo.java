package top.fsfsfs.demo.test.vo;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import top.fsfsfs.basic.mybatisflex.listener.DefaultInsertListener;
import top.fsfsfs.basic.mybatisflex.listener.DefaultUpdateListener;
import top.fsfsfs.demo.test.entity.base.CodeTestSimpleBase;

import java.io.Serial;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;

/**
 * 单表标准字段示例表 VO类（通常用作Controller出参）。
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
@Table(value = CodeTestSimpleBase.TABLE_NAME)
public class CodeTestSimpleVo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @Id
    @Schema(description = "ID")
    private Long id;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createdAt;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private Long createdBy;

    /**
     * 最后修改时间
     */
    @Schema(description = "最后修改时间")
    private LocalDateTime updatedAt;

    /**
     * 最后修改人
     */
    @Schema(description = "最后修改人")
    private Long updatedBy;

    /**
     * 删除标识
     */
    @Schema(description = "删除标识")
    private Long deletedAt;

    /**
     * 删除人
     */
    @Schema(description = "删除人")
    private Long deletedBy;

    /**
     * 名称
     */
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
    @Column("type_")
    @Schema(description = "商品类型;; #ProductType{ordinary:普通;gift:赠品}")
    private String type;

    /**
     * 商品类型2;;
     * #{ordinary:01,普通;gift:02,赠品;}
     */
    @Schema(description = "商品类型2;; #{ordinary:01,普通;gift:02,赠品;}")
    private String type2;

    /**
     * 学历;@Echo(api = EchoApi.DICTIONARY_ITEM_FEIGN_CLASS,  dictType = EchoDictType.Global.EDUCATION)
     */
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
    @Schema(description = "名称")
    private String label;

    /**
     * 字符字典;@Echo(api = "top.tangyh.lamp.common.api.DictApi", dictType="GLOBAL_SEX")
     */
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
    @Schema(description = "浮点")
    private BigDecimal test10;

    /**
     * xiao树
     */
    @Schema(description = "xiao树")
    private BigDecimal test11;

}
