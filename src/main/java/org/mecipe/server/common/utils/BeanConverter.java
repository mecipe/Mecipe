package org.mecipe.server.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

@Slf4j
public class BeanConverter {

    public static void copyProperties(Object source, Object target) {
        try {
            BeanUtils.copyProperties(source, target);
        } catch (Exception e) {
            throw new RuntimeException("BeanConverter error: " + e.getMessage(), e);
        }
    }

    public static <T> T toBean(Object source, Class<T> clazz) {
        try {
            T target = clazz.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("Bean conversion error", e);
        }
    }

}
