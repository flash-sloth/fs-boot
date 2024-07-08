package top.fsfsfs.main.system.entity.base;

import com.mybatisflex.annotation.Column;
import java.io.Serializable;
import java.time.LocalDateTime;
import top.fsfsfs.basic.base.entity.SuperEntity;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

/**
 * 系统参数实体类。
 *
 * @author hukunzhen
 * @since 2024-07-08 21:45:18
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class SysParamBase extends SuperEntity<Long> implements Serializable {
    /** 表名称 */
    public static final String TABLE_NAME = "fs_sys_param";

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 参数父ID
     */
    private Long parantId;

    /**
     * 参数路径，[key1].[key2],如：sys.logo.bigico
     */
    private String path;

    /**
     * 参数名称
     */
    private String name;

    /**
     * 唯一参数编码
     */
    private String key;

    /**
     * 子系统名称
     */
    private String appname;

    /**
     * 参数值
     */
    private String values;

    /**
     * 扩展内容(类型为json时，取该字段)
     */
    private String content;

    /**
     * 数据类型：string,number,datetime,boolean,json
     */
    private String type;

    /**
     * 是否启用,0启用，1禁用
     */
    private Boolean enabled;

    /**
     * 备注
     */
    private String remark;

    /**
     * 删除标志
     */
    private Long deletedAt;

    /**
     * 删除用户
     */
    private Long deletedBy;

    /**
     * 删除标志,0：正常，非0（时间戳）：删除
     */
    private Integer delFlag;

    /**
     * 租户ID
     */
    @Column(isLarge = true, version = true)
    private Long tenantId;

    /**
     * 组织ID（组织类型：单位）
     */
    private Long depId;

    /**
     * 范围，0：系统级，1：租户级，2：单位级，3：部门级
     */
    private Integer scope;

}
