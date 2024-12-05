package org.nico.ratel.server.utils;

import org.nico.ratel.server.ServerConfig;

/**
 * @author 柴奇君
 * @create 2024/12/5
 * @desc
 */
public class IdUtils {

    public static final long _2024_12_1_0_0_0_0 = 1732982400000L;
    /**
     * 计数器
     */
    private static int id = 0;


    private static long nowSec = getNowSec();

    /**
     * 锁对象
     */

    private final static Object obj = new Object();

    /**
     * 获取一个唯一ID ID 是一个64位long 16位服务器id + 48位时间秒数 + 16位自增ID
     * <p>
     * <p>
     * 规则：1、服务器id不变 ，2、时间随着当前时间变更，3、自增id如果从1到65000后，ID复位，此时需要时间增1防止重复
     * 4、自增后的时间小于了当前时间，那么就更新当前时间
     *
     * @return
     */
    public static long getId() {
        int nowId;
        long now = getNowSec();
        synchronized (obj) {
            id += 1;
            if (id > 65000) {
                id = 0;
                nowSec += 1L;
            }
            nowId = id;
            if (now > nowSec) {
                nowSec = now;
            } else {
                now = nowSec;
            }
        }
        return ((ServerConfig.serverId & 0x7_FFFFL) << 45)
                | ((now & 0x1FFF_FFFF) << 16) | (nowId & 0xFFFF);
    }


    private static long getNowSec() {
        return (System.currentTimeMillis() - _2024_12_1_0_0_0_0) / 1000L;
    }

    public static void main(String[] args) {

    }
}
