package priv.ymqm.housing.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountStateEnum implements CodeMessageEnum {

    /**
     * 账号状态
     */
    NORMAL(1, "正常状态"),
    DISABLE(2, "禁用");

    private Integer code;
    private String message;
}
