package com.nubia.spring.utils;
import org.apache.log4j.Logger;
public class Log {
    private static Logger log;
    private Log() {
    }
    /**
     * 未处理线程安全
     * 
     * @param log
     * @return
     */
    public static Logger createInstance(Class clazz) {
        if (log == null) {
            if (log == null) {
                log = Logger.getLogger(clazz);
            }
        }
        return log;
    }
    /**
     * 处理线程问题
     * 
     * @param log
     * @return
     */
    public static Logger createSynInstance(Class clazz) {
        if (log == null) {
            synchronized (Log.class) {
                if (log == null) {
                    log = Logger.getLogger(clazz);
                }
            }
        }
        return log;
    }
}
