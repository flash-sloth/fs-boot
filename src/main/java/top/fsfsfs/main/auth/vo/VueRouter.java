//package top.fsfsfs.main.auth.vo;
//
//import cn.hutool.core.lang.tree.TreeNode;
//import cn.hutool.core.map.MapUtil;
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonInclude;
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import top.fsfsfs.basic.interfaces.echo.EchoVO;
//
//import java.io.Serial;
//import java.io.Serializable;
//import java.util.Map;
//
///**
// * 构建 Vue路由
// *
// * @author tangyh
// * @since 2024年06月15日22:18:40
// */
//@Data
//@EqualsAndHashCode(callSuper = false)
//@JsonInclude(JsonInclude.Include.NON_NULL)
//public class VueRouter extends TreeNode<Long> implements Serializable, EchoVO {
//
//    @Serial
//    private static final long serialVersionUID = -3327478146308500708L;
//    private Map<String, Object> echoMap = MapUtil.newHashMap();
//    @Schema(description = "路径")
//    private String path;
//    @Schema(description = "菜单名称")
//    private String name;
//    @Schema(description = "组件")
//    private String component;
//    @Schema(description = "重定向")
//    private String redirect;
//    @Schema(description = "元数据")
//    private RouterMeta meta;
//    @Schema(description = "类型")
////    @Echo(api = EchoApi.DICTIONARY_ITEM_FEIGN_CLASS, dictType = EchoDictType.System.RESOURCE_TYPE)
//    private String resourceType;
//    @Schema(description = "打开方式")
////    @Echo(api = EchoApi.DICTIONARY_ITEM_FEIGN_CLASS, dictType = EchoDictType.System.RESOURCE_OPEN_WITH)
//    private String openWith;
//
//    @JsonIgnore
//    private Boolean hidden;
//    @JsonIgnore
//    private String metaJson;
//    @JsonIgnore
//    private String icon;
//}
