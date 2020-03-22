package priv.ymqm.housing.common.util;

import com.wf.captcha.SpecCaptcha;
import priv.ymqm.housing.domain.bo.VerifyCodeBO;

/**
 * @author chenhonnian
 * @since 2020/03/21
 */
public class VerifyCodeUtil {

    private static final int DEFAULT_CAPTCHA_WIDTH = 200;

    private static final int DEFAULT_CAPTCHA_HEIGHT = 72;

    private static final int DEFAULT_CAPTCHA_SIZE = 4;

    private VerifyCodeUtil() {
    }

    public static VerifyCodeBO generateVerifyCode() {
        SpecCaptcha specCaptcha = new SpecCaptcha();
        // 验证码显示宽度
        specCaptcha.setWidth(DEFAULT_CAPTCHA_WIDTH);
        // 验证码显示高度
        specCaptcha.setHeight(DEFAULT_CAPTCHA_HEIGHT);
        // 验证码字符数
        specCaptcha.setLen(DEFAULT_CAPTCHA_SIZE);

        VerifyCodeBO verifyCodeBO = new VerifyCodeBO();
        verifyCodeBO.setSpecCaptcha(specCaptcha);
        verifyCodeBO.setVerifyCodeText(specCaptcha.text());
        return verifyCodeBO;
    }
}
