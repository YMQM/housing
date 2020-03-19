package priv.ymqm.housing.common.util;

import priv.ymqm.housing.common.enums.CodeMessageEnum;

/**
 * @author chenhonnian
 * @since 2020/03/19
 */
public class EnumUtil {
    private EnumUtil() {
    }

    public static <T extends CodeMessageEnum> T getEnumByCode(Class<T> enumType, Integer code) {
        T[] enumConstants = enumType.getEnumConstants();
        for (T enumConst : enumConstants) {
            if (code != null && code.equals(enumConst.getCode())) {
                return enumConst;
            }
        }
        return null;
    }

    public static <T extends CodeMessageEnum> T getEnumByMessage(Class<T> enumType, String message) {
        T[] enumConstants = enumType.getEnumConstants();
        for (T enumConst : enumConstants) {
            if (message != null && message.equals(enumConst.getMessage())) {
                return enumConst;
            }
        }
        return null;
    }
}
