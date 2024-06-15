package top.fsfsfs.boot.modules.auth.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashMap;

/**
 * Vue路由 Meta
 *
 * @author tangyh
 * @since 2024年06月15日22:18:40
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RouterMeta extends LinkedHashMap<String, Object> implements Serializable {

    @Serial
    private static final long serialVersionUID = 5499925008927195914L;


}
