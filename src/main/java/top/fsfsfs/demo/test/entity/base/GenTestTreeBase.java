package top.fsfsfs.demo.test.entity.base;

import com.mybatisflex.annotation.Column;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import top.fsfsfs.basic.base.entity.TreeEntity;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

/**
 * 测试树结构 实体类。
 *
 * @author tangyh
 * @since 2024-06-25
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class GenTestTreeBase extends TreeEntity<Long> implements Serializable {
    /** 表名称 */
    public static final String TABLE_NAME = "fs_gen_test_tree";

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;

    /**
     * 库存
     */
    private Integer stock;

    /**
     * 商品类型;
     * #ProductType{ordinary:普通;gift:赠品}
     */
    @Column("type_")
    private String type;

    /**
     * 商品类型2;#{ordinary:01,普通;gift:02,赠品;}
     */
    private String type2;

    /**
     * 学历;@Echo(api = EchoApi.DICTIONARY_ITEM_FEIGN_CLASS,  dictType = EchoDictType.Global.EDUCATION)
     */
    private String type3;

    /**
     * 状态
     */
    private Boolean state;

    /**
     * 测试
     */
    private Integer test4;

    /**
     * 时间
     */
    private LocalDate test5;

    /**
     * 日期
     */
    private LocalDateTime test6;

    /**
     * 名称
     */
    private String label;

    /**
     * 字符字典;@Echo(api = "top.tangyh.lamp.common.api.DictApi", dictType="GLOBAL_SEX")
     */
    private String test7;

    /**
     * 整形字典;@Echo(api = EchoApi.DICTIONARY_ITEM_FEIGN_CLASS, dictType = EchoDictType.Global.TEST_ADD_DICT)[1-测试 2-新增 aad-haha]
     */
    private Integer test12;

    /**
     * 用户;@Echo(api = EchoApi.POSITION_ID_CLASS)[1-aa 2-ddd]
     */
    private Long userId;

    /**
     * 组织;@Echo(api = EchoApi.ORG_ID_CLASS)
     */
    private Long orgId;

    /**
     * 小数
     */
    private BigDecimal test8;

    /**
     * 浮点2
     */
    private Float test9;

    /**
     * 浮点
     */
    private BigDecimal test10;

    /**
     * xiao树
     */
    private BigDecimal test11;

    /**
     * 删除标志
     */
    private Long deletedAt;

    private Long deletedBy;

}
