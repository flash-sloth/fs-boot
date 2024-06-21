/*
 * Copyright (c) 2024. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package top.fsfsfs.boot.modules.generator.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import top.fsfsfs.basic.constant.Constants;

import java.util.function.UnaryOperator;

/**
 * 代码生成默认配置
 * @author tangyh
 * @since 2024年06月21日11:09:07
 */
@Data
@RefreshScope
@ConfigurationProperties(prefix = CodeCreatorProperties.PREFIX)
public class CodeCreatorProperties {
    public static final String PREFIX = Constants.PROJECT_PREFIX + ".generator";
    private PackageInfo packageInfo = new PackageInfo();
    private Entity entity = new Entity();


}
