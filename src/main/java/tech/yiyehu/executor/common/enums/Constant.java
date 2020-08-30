package tech.yiyehu.executor.common.enums;

/**
 * 常量
 */
public class Constant {
    /** 超级管理员ID */
    public static final int SUPER_ADMIN = 1;

    /** 用户ID字段 */
    public static final String USER_ID = "user_id";

    /** ID字段 */
    public static final String ID = "id";
    /**
     * 当前页码
     */
    public static final String PAGE = "page";
    /**
     * 每页显示记录数
     */
    public static final String LIMIT = "limit";
    /**
     * 排序字段
     */
    public static final String ORDER_FIELD = "sidx";
    /**
     * 排序方式
     */
    public static final String ORDER = "order";
    /**
     *  升序
     */
    public static final String ASC = "asc";

    /**
     * 菜单类型
     */
    public enum MenuType {
        /**
         * 目录
         */
        CATALOG(0),
        /**
         * 菜单
         */
        MENU(1),
        /**
         * 按钮
         */
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }
    /**
     * 云服务商
     */
    public enum StorageType {
        /**
         * 七牛云
         */
        QINIU(1),
        /**
         * 阿里云
         */
        ALIYUN(2),
        /**
         * 腾讯云
         */
        QCLOUD(3),

        /**
         * 本地存储
         */
        LOCAL(4);

        private int value;

        StorageType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * user type
     */
    public enum UserType {
        /**
         * 系统用户
         */
        SYS(0),
        /**
         * 普通用户
         */
        USER(1);

        private int value;

        UserType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * exe type
     */
    public enum ExeType {
        /**
         * 执行Python程序
         */
        PYTHON(1,"python","--");

        private int value;
        private String exeCmd;
        private String prefix;

        ExeType(int value,String exeCmd,String prefix) {
            this.value = value;
            this.exeCmd = exeCmd;
            this.prefix = prefix;
        }

        public static ExeType getWithValue(int value) {
            for(ExeType type:ExeType.values()){
                if(type.value == value){
                    return type;
                }
            }
            return null;
        }

        public int getValue() {
            return value;
        }

        public String getExeCmd() {
            return exeCmd;
        }

        public String getPrefix() {
            return prefix;
        }
    }
    /**
     * exe type
     */
    public enum ExeCommonParam {
        /**
         * 执行程序的常用参数名
         */
        FILE_PATH(0,"file_path");

        private int value;
        private String exeParam;

        ExeCommonParam(int value,String exeCmd) {
            this.value = value;
            this.exeParam = exeCmd;
        }

        public static ExeType getWithValue(int value) {
            for(ExeType type:ExeType.values()){
                if(type.value == value){
                    return type;
                }
            }
            return null;
        }

        public int getValue() {
            return value;
        }

        public String getExeParam() {
            return exeParam;
        }
    }

    /**
     * 执行的参数类型
     */
    public enum ExeParamType {
        /**
         * 只有文件
         */
        FILE(1,"file"),
        /**
         * 只有参数
         */
        PARAMS(2,"params"),
        /**
         * 混合
         */
        MIX(3,"mix");

        private int value;
        private String type;

        ExeParamType(int value,String exeCmd) {
            this.value = value;
            this.type = exeCmd;
        }

        public static ExeType getWithValue(int value) {
            for(ExeType type:ExeType.values()){
                if(type.value == value){
                    return type;
                }
            }
            return null;
        }

        public int getValue() {
            return value;
        }

        public String getType() {
            return type;
        }
    }

    /**
     * exe type
     */
    public enum SymbolEnum {
        /**
         * String 分隔符
         */
        SPLIT_STRING(0,",");

        private int value;
        private String symbol;

        SymbolEnum(int value,String exeCmd) {
            this.value = value;
            this.symbol = exeCmd;
        }

        public static ExeType getWithValue(int value) {
            for(ExeType type:ExeType.values()){
                if(type.value == value){
                    return type;
                }
            }
            return null;
        }

        public int getValue() {
            return value;
        }

        public String getSymbol() {
            return symbol;
        }
    }

}
