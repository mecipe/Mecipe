package org.mecipe.server.common.utils;

import org.mecipe.server.common.enums.ErrorCode;
import org.mecipe.server.exception.BusinessException;

import java.util.function.Predicate;

public class Valider {

    public static void validateNullParams(Object... params) {
        for (Object param : params) {
            if (param == null) {
                throw new BusinessException(ErrorCode.NULL_ERROR);
            }
        }
    }

    public static <T> void validate(T object, ErrorCode errorCode, String message, Predicate<T> validationRule) {
        if (validationRule.test(object)) {
            throw new BusinessException(errorCode, message);
        }
    }

}
