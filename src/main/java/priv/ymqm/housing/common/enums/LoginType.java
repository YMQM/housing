package priv.ymqm.housing.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import priv.ymqm.housing.domain.po.AdminAccount;

@Getter
@AllArgsConstructor
public enum LoginType implements CodeMessageEnum {

    /**
     * 用户登录方式
     */
    USER_ID_LOGIN(1, "ID登录", AdminAccount.ID),
    USER_EMAIL_LOGIN(2, "邮箱登录", AdminAccount.EMAIL),
    USER_PHONE_LOGIN(3, "手机号登录", AdminAccount.PHONE_NUMBER);

    private Integer code;
    private String message;

    private String subjectName;
}
