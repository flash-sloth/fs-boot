package top.fsfsfs.main.logsys.vo;

import cn.hutool.core.lang.tree.TreeNode;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.time.LocalDateTime;
import top.fsfsfs.basic.mybatisflex.listener.DefaultInsertListener;
import top.fsfsfs.basic.mybatisflex.listener.DefaultUpdateListener;
import top.fsfsfs.main.logsys.entity.base.FsSysLogsBase;

import java.io.Serial;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;
import lombok.EqualsAndHashCode;

/**
 * 系统日志 VO类（通常用作Controller出参）。
 *
 * @author hukunzhen
 * @since 2024-07-02
 */
@Accessors(chain = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Schema(description = "系统日志")
@Table(value = FsSysLogsBase.TABLE_NAME, onInsert = DefaultInsertListener.class, onUpdate = DefaultUpdateListener.class)
public class FsSysLogsVo extends TreeNode<Long> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    @Id(keyType = KeyType.Auto)
    @Schema(description = "日志ID")
    private Long id;

    /**
     * 请求方法
     */
    @Schema(description = "请求方法")
    private String logMethod;

    /**
     * 描述
     */
    @Schema(description = "描述")
    private String logDescription;

    /**
     * 请求参数
     */
    @Schema(description = "请求参数")
    private String logParams;

    /**
     * ip地址
     */
    @Schema(description = "ip地址")
    private String logIp;

    /**
     * 浏览器
     */
    @Schema(description = "浏览器")
    private String userAgent;

    /**
     * 日志类型，1-登陆2-访问3-操作4-异常
     */
    @Schema(description = "日志类型，1-登陆2-访问3-操作4-异常")
    private String logType;

    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String userName;

    /**
     * 电话
     */
    @Schema(description = "电话")
    private String userPhone;

    /**
     * 请求耗时(毫秒)
     */
    @Schema(description = "请求耗时(毫秒)")
    private Integer executeTime;

    /**
     * 异常详细
     */
    @Schema(description = "异常详细")
    private String exceptionDetail;

    /**
     * 租户id
     */
    @Column(isLarge = true, version = true)
    @Schema(description = "租户id")
    private Long tenantId;

    /**
     * 操作
     */
    @Schema(description = "操作")
    private String operation;

    /**
     * 日志标签，用于分组
     */
    @Schema(description = "日志标签，用于分组")
    private String tags;

    /**
     * 所属部门ID
     */
    @Schema(description = "所属部门ID")
    private Long deptId;

    /**
     * 创建人
     */
    @Schema(description = "创建人")
    private Long createBy;

    /**
     * 修改人
     */
    @Schema(description = "修改人")
    private Long updateBy;

    /**
     * 删除时间
     */
    @Schema(description = "删除时间")
    private LocalDateTime deleteAt;

    /**
     * 删除用户
     */
    @Schema(description = "删除用户")
    private Long deleteBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createAt;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updateAt;

    /**
     * 删除标志,0：正常，非0（时间戳）：删除
     */
    @Schema(description = "删除标志,0：正常，非0（时间戳）：删除")
    private Integer delFlag;

}
