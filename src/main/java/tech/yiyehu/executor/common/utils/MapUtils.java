package tech.yiyehu.executor.common.utils;

import java.util.HashMap;


/**
 * Map工具类,return this 方便代码编写
 */
public class MapUtils extends HashMap<String, Object> {

    @Override
    public MapUtils put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
