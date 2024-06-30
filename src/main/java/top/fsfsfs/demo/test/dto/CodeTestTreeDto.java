package top.fsfsfs.demo.test.dto;

import com.mybatisflex.annotation.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import top.fsfsfs.basic.base.entity.BaseEntity;

import java.io.Serial;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.NoArgsConstructor;

/**
 * 树结构标准字段示例表 DTO（写入方法入参）。
 *
 * @author tangyh
 * @since 2024-06-30
 */
@Accessors(chain = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "树结构标准字段示例表")
public class CodeTestTreeDto implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull(message = "请填写ID", groups = BaseEntity.Update.class)
    @Schema(description = "ID")
    private Long id;

    /**
     * 父id
     */
    @Schema(description = "父id")
    private Long parentId;

    /**
     * 排序
     */
    @Schema(description = "排序")
    private Integer weight;

    /**
     * 名称
     */
    @Size(max = 255, message = "名称长度不能超过{max}")
    @Schema(description = "名称")
    private String name;

}
