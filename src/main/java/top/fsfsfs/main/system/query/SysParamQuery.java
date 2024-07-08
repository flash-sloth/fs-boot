package top.fsfsfs.main.system.query;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

import java.io.Serial;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;

/**
 * 系统参数 Query类（查询方法入参）。
 *
 * @author hukunzhen
 * @since 2024-07-08 21:45:18
 */
@Accessors(chain = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "系统参数")
public class SysParamQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 参数ID
     */
    @Schema(description = "参数ID")
    private Long id;

    /**
     * 参数父ID
     */
    @Schema(description = "参数父ID")
    private Long parantId;

    /**
     * 参数路径，[key1].[key2],如：sys.logo.bigico
     */
    @Schema(description = "参数路径，[key1].[key2],如：sys.logo.bigico")
    private String path;

    /**
     * 参数名称
     */
    @Schema(description = "参数名称")
    private String name;

    /**
     * 唯一参数编码
     */
    @Schema(description = "唯一参数编码")
    private String key;

    /**
     * 子系统名称
     */
    @Schema(description = "子系统名称")
    private String appname;

    /**
     * 参数值
     */
    @Schema(description = "参数值")
    private String values;

    /**
     * 扩展内容(类型为json时，取该字段)
     */
    @Schema(description = "扩展内容(类型为json时，取该字段)")
    private String content;

    /**
     * 数据类型：string,number,datetime,boolean,json
     */
    @Schema(description = "数据类型：string,number,datetime,boolean,json")
    private String type;

    /**
     * 是否启用,0启用，1禁用
     */
    @Schema(description = "是否启用,0启用，1禁用")
    private Boolean enabled;

    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;

    /**
     * 创建时间
     */
    @Schema(description = "创建标志")
    private Long createdAt;

    /**
     * 创建用户
     */
    @Schema(description = "创建用户")
    private Long createdBy;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updatedAt;

    /**
     * 更新用户
     */
    @Schema(description = "更新用户")
    private Long updatedBy;

    /**
     * 删除时间
     */
    @Schema(description = "删除时间")
    private LocalDateTime deletedAt;

    /**
     * 删除用户
     */
    @Schema(description = "删除用户")
    private Long deletedBy;

    /**
     * 删除标志,0：正常，非0（时间戳）：删除
     */
    @Schema(description = "删除标志,0：正常，非0（时间戳）：删除")
    private Integer delFlag;

    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private Long tenantId;

    /**
     * 组织ID（组织类型：单位）
     */
    @Schema(description = "组织ID（组织类型：单位）")
    private Long depId;

    /**
     * 范围，0：系统级，1：租户级，2：单位级，3：部门级
     */
    @Schema(description = "范围，0：系统级，1：租户级，2：单位级，3：部门级")
    private Integer scope;

}
