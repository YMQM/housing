package priv.ymqm.housing.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RestApiResCodeEnum implements CodeMessageEnum {

    /**
     * API请求结果code
     */
    SUCCESS(0, "请求成功"),

    PARAM_ERROR(2000, "参数错误"),

    UN_LOGIN(4000, "未登录"),

    NO_PERMISSION(4100, "权限不足"),

    SYSTEM_ERROR(5000, "系统内部错误");

    private Integer code;
    private String message;
}
