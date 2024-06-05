package top.fsfsfs.boot.common.constant;

/**
 * Echo注解中dictType的常量
 * <p>
 * 存放系统中常用的类型
 * <p>
 * 本类中的 @fs.generator auto insert 请勿删除
 *
 * @author zuihou
 * @date 2019/07/26
 */
public interface EchoDictType {
    // @fs.generator auto insert EchoDictType

    /**
     * 全局字典类型
     */
    interface Global {
        // @fs.generator auto insert Global

        /**
         * 行政级别
         * [10-国家 20-省份/直辖市 30-地市 40-区县 50-乡镇]
         */
        String AREA_LEVEL = "GLOBAL_AREA_LEVEL";
        /**
         * 民族
         * [01-汉族 02-...]
         */
        String NATION = "GLOBAL_NATION";
        /**
         * 学历
         * [01-小学 02-中学 03-高中 04-专科 05-本科 06-硕士 07-博士 08-博士后 99-其他]
         */
        String EDUCATION = "GLOBAL_EDUCATION";
        /**
         * 性别
         */
        String SEX = "GLOBAL_SEX";
        /**
         * 数据类型
         * [10-系统值 20-业务值]
         */
        String DATA_TYPE = "GLOBAL_DATA_TYPE";
    }
}
