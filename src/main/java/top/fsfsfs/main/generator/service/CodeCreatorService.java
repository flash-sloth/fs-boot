package top.fsfsfs.main.generator.service;

import cn.hutool.core.lang.tree.Tree;
import top.fsfsfs.basic.mvcflex.request.DownloadVO;
import top.fsfsfs.basic.mvcflex.service.SuperService;
import top.fsfsfs.main.generator.dto.CodeGenDto;
import top.fsfsfs.main.generator.dto.CodePreviewDto;
import top.fsfsfs.main.generator.dto.TableImportDto;
import top.fsfsfs.main.generator.entity.CodeCreator;
import top.fsfsfs.main.generator.vo.CodeCreatorVo;

import java.util.List;

/**
 * 代码生成 服务层。
 *
 * @author tangyh
 * @since 2024-06-21
 */
public interface CodeCreatorService extends SuperService<CodeCreator> {
    /**
     * 查询指定数据源的表
     *
     * @param dsId 数据源id
     * @return 表结构
     */
    List<CodeCreatorVo> listTableMetadata(Long dsId);

    /**
     * 导入表结构
     * @param importDto 导入参数
     * @return 是否成功
     */
    Boolean importTable(TableImportDto importDto);

    /**
     * 预览表结构
     * @param previewDto 预览参数
     * @return 代码预览树
     */
    List<Tree<Long>> preview(CodePreviewDto previewDto);

    /**
     * 生成代码
     * @param genDto 生成参数
     */
    void generator(CodeGenDto genDto);


    /**
     * 下载代码
     * @param ids  表id
     * @param codeIds 代码id
     * @return 字节
     */
    DownloadVO download(List<Long> ids, List<Long> codeIds);

    /**
     * 删除表和从表数据
     * @param ids id
     * @return 是否成功
     */
    Boolean removeAllByIds(List<Long> ids);
}
