package top.fsfsfs.boot.modules.generator.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 代码生成器 配置 VO
 *
 * @author tangyh
 * @since 2021-08-01 16:04
 */
@Data
@Accessors(chain = true)
public class CodeCreatorVO {
    private String vo;
    private String entity;
    private String xml;
    private String mapper;
    private String service;
    private String controller;
    private String menu;

    private String list;
    private String tree;
    private String form;

}
