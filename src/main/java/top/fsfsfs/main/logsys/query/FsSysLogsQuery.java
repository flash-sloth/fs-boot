package top.fsfsfs.main.logsys.query;

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
 * 系统日志 Query类（查询方法入参）。
 *
 * @author hukunzhen
 * @since 2024-07-02
 */
@Accessors(chain = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "系统日志")
public class FsSysLogsQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
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
    @Schema(description = "删除标志")
    private Long deletedAt;

    /**
     * 删除用户
     */
    @Schema(description = "删除用户")
    private Long deletedBy;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    @Schema(description = "修改时间")
    private LocalDateTime updateTime;

    /**
     * 删除标志,0：正常，非0（时间戳）：删除
     */
    @Schema(description = "删除标志,0：正常，非0（时间戳）：删除")
    private Integer delFlag;

}
