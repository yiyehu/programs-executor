package tech.yiyehu.executor.common.utils;

import java.util.HashMap;

/**
 * 计时器
 *
 * @author yiyehu
 */
public class WatchTimeUtil {

    HashMap<String, Long> map = new HashMap();

    public static WatchTimeUtil instance() {
        return new WatchTimeUtil();
    }

    private WatchTimeUtil() {
    }

    public WatchTimeUtil start(String task) {
        map.put("0" + task, System.currentTimeMillis());
        return this;
    }

    public WatchTimeUtil start() {
        return start(null);
    }

    public WatchTimeUtil stop(String task) {
        map.put("1" + task, System.currentTimeMillis());
        return this;
    }

    public WatchTimeUtil stop() {
        return stop(null);
    }

    public Long getInterval(String task) {
        if (map.get("1" + task) == null || map.get("0" + task) == null) {
            return -1l;
        }
        return map.get("1" + task) - map.get("0" + task);
    }

    public Long getInterval() {
        return getInterval(null);
    }
}
