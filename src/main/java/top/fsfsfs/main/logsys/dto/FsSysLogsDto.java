package top.fsfsfs.main.logsys.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

import top.fsfsfs.basic.base.entity.BaseEntity;

import java.io.Serial;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;

/**
 * 系统日志 DTO（写入方法入参）。
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
public class FsSysLogsDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 日志ID
     */
    @NotNull(message = "请填写日志ID", groups = BaseEntity.Update.class)
    @Schema(description = "日志ID")
    private Long id;

    /**
     * 请求方法
     */
    @Size(max = 255, message = "请求方法长度不能超过{max}")
    @Schema(description = "请求方法")
    private String logMethod;

    /**
     * 描述
     */
    @Size(max = 255, message = "描述长度不能超过{max}")
    @Schema(description = "描述")
    private String logDescription;

    /**
     * 请求参数
     */
    @Size(max = 536870911, message = "请求参数长度不能超过{max}")
    @Schema(description = "请求参数")
    private String logParams;

    /**
     * ip地址
     */
    @Size(max = 255, message = "ip地址长度不能超过{max}")
    @Schema(description = "ip地址")
    private String logIp;

    /**
     * 浏览器
     */
    @Size(max = 255, message = "浏览器长度不能超过{max}")
    @Schema(description = "浏览器")
    private String userAgent;

    /**
     * 日志类型，1-登陆2-访问3-操作4-异常
     */
    @Size(max = 255, message = "日志类型，1-登陆2-访问3-操作4-异常长度不能超过{max}")
    @Schema(description = "日志类型，1-登陆2-访问3-操作4-异常")
    private String logType;

    /**
     * 用户名
     */
    @Size(max = 50, message = "用户名长度不能超过{max}")
    @Schema(description = "用户名")
    private String userName;

    /**
     * 电话
     */
    @Size(max = 50, message = "电话长度不能超过{max}")
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
    @Size(max = 536870911, message = "异常详细长度不能超过{max}")
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
    @Size(max = 50, message = "操作长度不能超过{max}")
    @Schema(description = "操作")
    private String operation;

    /**
     * 日志标签，用于分组
     */
    @Size(max = 255, message = "日志标签，用于分组长度不能超过{max}")
    @Schema(description = "日志标签，用于分组")
    private String tags;

    /**
     * 所属部门ID
     */
    @Schema(description = "所属部门ID")
    private Long deptId;

    /**
     * 删除标志,0：正常，非0（时间戳）：删除
     */
    @NotNull(message = "请填写删除标志,0：正常，非0（时间戳）：删除")
    @Schema(description = "删除标志,0：正常，非0（时间戳）：删除")
    private Integer delFlag;

}
