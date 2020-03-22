package priv.ymqm.housing.domain.bo;

import com.wf.captcha.SpecCaptcha;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * @author chenhonnian
 * @since 2020/03/21
 */
@Data
public class VerifyCodeBO {
    private String requestKey;

    private String verifyCodeText;

    private SpecCaptcha specCaptcha;
}
