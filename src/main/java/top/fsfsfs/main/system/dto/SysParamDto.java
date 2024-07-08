package top.fsfsfs.main.system.dto;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import top.fsfsfs.basic.base.entity.BaseEntity;

import java.io.Serial;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;

/**
 * 系统参数 DTO（写入方法入参）。
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
public class SysParamDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 参数ID
     */
    @NotNull(message = "请填写参数ID", groups = BaseEntity.Update.class)
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
    @Size(max = 512, message = "参数路径，[key1].[key2],如：sys.logo.bigico长度不能超过{max}")
    @Schema(description = "参数路径，[key1].[key2],如：sys.logo.bigico")
    private String path;

    /**
     * 参数名称
     */
    @NotEmpty(message = "请填写参数名称")
    @Size(max = 64, message = "参数名称长度不能超过{max}")
    @Schema(description = "参数名称")
    private String name;

    /**
     * 唯一参数编码
     */
    @NotEmpty(message = "请填写唯一参数编码")
    @Size(max = 64, message = "唯一参数编码长度不能超过{max}")
    @Schema(description = "唯一参数编码")
    private String key;

    /**
     * 子系统名称
     */
    @Size(max = 255, message = "子系统名称长度不能超过{max}")
    @Schema(description = "子系统名称")
    private String appname;

    /**
     * 参数值
     */
    @NotEmpty(message = "请填写参数值")
    @Size(max = 64, message = "参数值长度不能超过{max}")
    @Schema(description = "参数值")
    private String values;

    /**
     * 扩展内容(类型为json时，取该字段)
     */
    @Size(max = 2147483647, message = "扩展内容(类型为json时，取该字段)长度不能超过{max}")
    @Schema(description = "扩展内容(类型为json时，取该字段)")
    private String content;

    /**
     * 数据类型：string,number,datetime,boolean,json
     */
    @Size(max = 16, message = "数据类型：string,number,datetime,boolean,json长度不能超过{max}")
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
    @Size(max = 255, message = "备注长度不能超过{max}")
    @Schema(description = "备注")
    private String remark;

    /**
     * 删除标志,0：正常，非0（时间戳）：删除
     */
    @NotNull(message = "请填写删除标志,0：正常，非0（时间戳）：删除")
    @Schema(description = "删除标志,0：正常，非0（时间戳）：删除")
    private Integer delFlag;

    /**
     * 租户ID
     */
    @NotNull(message = "请填写租户ID")
    @Schema(description = "租户ID")
    private Long tenantId;

    /**
     * 组织ID（组织类型：单位）
     */
    @NotNull(message = "请填写组织ID（组织类型：单位）")
    @Schema(description = "组织ID（组织类型：单位）")
    private Long depId;

    /**
     * 范围，0：系统级，1：租户级，2：单位级，3：部门级
     */
    @NotNull(message = "请填写范围，0：系统级，1：租户级，2：单位级，3：部门级")
    @Schema(description = "范围，0：系统级，1：租户级，2：单位级，3：部门级")
    private Integer scope;

}
