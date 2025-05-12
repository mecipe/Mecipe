package org.mecipe.server.common.utils;

import org.springframework.beans.BeanUtils;

public class BeanConverter {

    public static void copyProperties(Object source, Object target) {
        try {
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            throw new RuntimeException("BeanConverter error: " + e.getMessage(), e);
        }
    }

    /**
     * Bean转换，目标对象必须有无参构造器
     */
    public static <T> T toBean(Object source, Class<T> clazz) {
        try {
            T target = clazz.getDeclaredConstructor().newInstance();
            copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Bean conversion error", e);
        }
    }

}
