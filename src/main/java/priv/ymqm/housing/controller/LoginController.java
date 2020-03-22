package priv.ymqm.housing.controller;

import com.wf.captcha.SpecCaptcha;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import priv.ymqm.housing.common.enums.AccountStateEnum;
import priv.ymqm.housing.common.enums.LoginType;
import priv.ymqm.housing.common.exception.HousingException;
import priv.ymqm.housing.domain.bo.VerifyCodeBO;
import priv.ymqm.housing.domain.dto.login.UserLoginDto;
import priv.ymqm.housing.domain.po.AdminAccount;
import priv.ymqm.housing.domain.vo.UserLoginVO;
import priv.ymqm.housing.domain.vo.res.R;
import priv.ymqm.housing.service.AdminAccountService;
import priv.ymqm.housing.service.LoginControlService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author chenhonnian
 * @since 2020/03/21
 */
@Slf4j
@RestController
@RequestMapping("/loginController")
public class LoginController {

    @Autowired
    private AdminAccountService adminAccountService;

    @Autowired
    private LoginControlService loginControlService;

    @PostMapping("login")
    public R<UserLoginVO> loginIn(@RequestBody UserLoginDto userLoginDto) {
        boolean isRightVerifyCode = loginControlService.isRightVerifyCode(userLoginDto.getRequestKey(),
                userLoginDto.getVerifyCode());
        if (!isRightVerifyCode) {
            return R.error("验证码错误，请重新填写验证码");
        }

        LoginType loginType = judgeLoginType(userLoginDto.getSubject());
        AdminAccount account = adminAccountService.getAdminAccount(loginType.getSubjectName(),
                userLoginDto.getSubject());
        if (account == null) {
            return R.error("账号或密码错误");
        }

        if (account.getState() > AccountStateEnum.NORMAL.getCode()) {
            return R.error("账号状态异常，无法登录");
        }

        boolean isRightPassword = adminAccountService.verifyPassword(account, userLoginDto.getPassword());
        if (!isRightPassword) {
            return R.error("账号或密码错误");
        }

        UserLoginVO userLoginVO = loginControlService.setLoginState(account);
        return R.ok(userLoginVO);
    }

    @PostMapping("logout")
    public R<String> logOut() {
        loginControlService.logOut();
        return R.ok("成功退出登录");
    }

    @GetMapping("getVerifyCode")
    public void getVerifyCode(HttpServletResponse httpServletResponse) {
        VerifyCodeBO verifyCodeBO = loginControlService.createVerifyCode();
        httpServletResponse.addHeader("verifyCodeRequestKey", verifyCodeBO.getRequestKey());
        httpServletResponse.setContentType("image/png");
        OutputStream responseStream;
        try {
            responseStream = httpServletResponse.getOutputStream();
            SpecCaptcha specCaptcha = verifyCodeBO.getSpecCaptcha();
            specCaptcha.out(responseStream);
        } catch (IOException e) {
            log.error("获取验证码失败", e);
            throw new HousingException("获取验证码失败");
        }
    }

    private LoginType judgeLoginType(String subject) {
        if (subject == null) {
            throw new HousingException("登录账号不能为null");
        }
        if (StringUtils.isNumeric(subject) && subject.length() == 11) {
            return LoginType.USER_PHONE_LOGIN;
        }
        if (StringUtils.isNumeric(subject)) {
            return LoginType.USER_ID_LOGIN;
        }
        String emailRegex = "[a-z][A-Z][0-9]*@\\.\\s+";
        if (subject.matches(emailRegex)) {
            return LoginType.USER_EMAIL_LOGIN;
        }
        return LoginType.USER_EMAIL_LOGIN;
    }
}
