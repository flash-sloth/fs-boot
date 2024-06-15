package top.fsfsfs.boot.modules.auth.vo;

import cn.hutool.core.lang.tree.Tree;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 用户拥有的权限资源
 *
 * @author tangyh
 * @since 2024年06月15日22:18:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@Schema(description = "用户的可用资源")
public class VisibleResourceVO implements Serializable {
    @Schema(description = "是否启用URI/按钮权限")
    private Boolean enabled;
    @Schema(description = "资源编码是否区分大小写")
    private Boolean caseSensitive;
    @Schema(description = "拥有的资源编码")
    private List<String> resourceList;
    @Schema(description = "拥有的菜单路由")
    private List<Tree<Long>> routerList;
    @Schema(description = "拥有的角色编码")
    private List<String> roleList;
}
