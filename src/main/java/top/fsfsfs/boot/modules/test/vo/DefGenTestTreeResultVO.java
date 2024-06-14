package top.fsfsfs.boot.modules.test.vo;

import cn.hutool.core.lang.tree.TreeNode;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import com.mybatisflex.core.keygen.KeyGenerators;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@Table(value = "fs_gen_test_tree")
public class DefGenTestTreeResultVO extends TreeNode<Long> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(description = "主键")
    @Id(keyType = KeyType.Generator, value = KeyGenerators.flexId)
    protected Long id;

    @Schema(description = "创建时间")
    protected LocalDateTime createdTime;

    @Schema(description = "创建人ID")
    protected Long createdBy;

    @Schema(description = "最后修改时间")
    protected LocalDateTime updatedTime;

    @Schema(description = "最后修改人ID")
    protected Long updatedBy;
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
    @Column(value = "type_")
    private String type;
    /**
     * 商品类型2 ;
     * <p>
     * #{ordinary:01,普通;gift:02,赠品;}
     */
    private String type2;
    /**
     * 学历;
     *
     * @Echo(api = EchoApi.DICTIONARY_ITEM_FEIGN_CLASS,  dictType = DictionaryType.Global.EDUCATION)
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
     * 父id
     */
    private Long parentId;
    /**
     * 名称
     */
    private String label;
    /**
     * 排序
     */
    private Integer weight;
    /**
     * 字符字典;
     *
     * @Echo(api = "top.tangyh.lamp.oauth.api.DictionaryApi", dictType="GLOBAL_SEX")
     */
    private String test7;
    /**
     * 整形字典;
     *
     * @Echo(api = EchoApi.DICTIONARY_ITEM_FEIGN_CLASS, dictType = DictionaryType.Global.DATA_TYPE)
     */
    private Integer test12;
    /**
     * 用户;
     *
     * @Echo(api = EchoApi.POSITION_ID_CLASS)
     */
    private Long userId;
    /**
     * 组织;
     *
     * @Echo(api = EchoApi.ORG_ID_CLASS)
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
    private Double test10;
    /**
     * xiao树
     */
    private BigDecimal test11;

}
