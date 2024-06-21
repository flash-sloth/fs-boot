package top.fsfsfs.boot.modules.generator.properties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 代码生成器 包信息
 *
 * @author tangyh
 * @since 2021-08-01 16:04
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PackageInfo {
    /**
     * 代码生成目录。
     */
    private String sourceDir;
    /**
     * 根包。
     */
    private String basePackage;
    /** 模块包名 */
    private String module;

    /** 模块包 描述 */
    private String moduleDescription;
    /** 子系统Id */
    private Long subSystemId;

    private String author = System.getProperty("user.name");
}
