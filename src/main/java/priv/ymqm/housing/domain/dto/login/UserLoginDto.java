package priv.ymqm.housing.domain.dto.login;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author chenhonnian
 * @since 2020/03/21
 */
@Data
public class UserLoginDto {

    @NotBlank(message = "账号不能为空")
    private String subject;

    @NotBlank(message = "登录密码不能为空")
    private String password;

    @NotBlank(message = "登录请求request key不能为空")
    private String requestKey;

    @NotBlank(message = "验证码不能为空")
    private String verifyCode;
}
