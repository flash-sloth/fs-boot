package top.fsfsfs.main.logsys.entity.base;

import com.mybatisflex.annotation.Column;
import java.io.Serializable;

import top.fsfsfs.basic.base.entity.SuperEntity;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

/**
 * 系统日志实体类。
 *
 * @author hukunzhen
 * @since 2024-07-02
 */
@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FsSysLogsBase extends SuperEntity<Long> implements Serializable {
    /** 表名称 */
    public static final String TABLE_NAME = "fs_sys_logs";

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 请求方法
     */
    private String logMethod;

    /**
     * 描述
     */
    private String logDescription;

    /**
     * 请求参数
     */
    private String logParams;

    /**
     * ip地址
     */
    private String logIp;

    /**
     * 浏览器
     */
    private String userAgent;

    /**
     * 日志类型，1-登陆2-访问3-操作4-异常
     */
    private String logType;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 电话
     */
    private String userPhone;

    /**
     * 请求耗时(毫秒)
     */
    private Integer executeTime;

    /**
     * 异常详细
     */
    private String exceptionDetail;

    /**
     * 租户id
     */
    @Column(isLarge = true, version = true)
    private Long tenantId;

    /**
     * 操作
     */
    private String operation;

    /**
     * 日志标签，用于分组
     */
    private String tags;

    /**
     * 所属部门ID
     */
    private Long deptId;


    /**
     * 删除标志,0：正常，非0（时间戳）：删除
     */
    private Integer delFlag;

    /**
     * 删除标志
     */
    private Long deletedAt;

    /**
     * 删除用户
     */
    private Long deletedBy;
}
