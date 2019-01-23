package com.fcgl.common.converter;

/**
 * 对象转换接口
 *
 * @author furg@senthink.com
 * @date 2017/11/16
 */
public interface Converter<S, T> {

    /**
     * 正向转换
     * @param s 源对象
     * @return 目标对象
     */
    T forward(S s);

    /**
     *
     * @param t
     * @return
     */
    S backward(T t);
}
