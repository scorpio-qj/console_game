package org.nico.ratel.commons.anno;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 柴奇君
 * @create 2024/12/5
 * @desc
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ScanEvent {

    /**
     * 游戏id，公共事件id为 0
     */
    int gameId() default 0;

    /**
     * 哪一端的事件
     * 1 客户端 2 服务端
     */
    int c_or_s() default 1;
}
