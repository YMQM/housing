package priv.ymqm.housing.service;

import priv.ymqm.housing.domain.bo.VerifyCodeBO;
import priv.ymqm.housing.domain.po.AdminAccount;
import priv.ymqm.housing.domain.vo.UserLoginVO;

/**
 * @author chenhonnian
 * @since 2020/03/21
 */
public interface LoginControlService {
    /**
     * 设置账号的登录状态
     *
     * @param adminAccount 登录账号
     * @return 登录信息
     */
    UserLoginVO setLoginState(AdminAccount adminAccount);

    /**
     * 登出当前用户
     */
    void logOut();

    /**
     * 生成验证码并将结果存放在redis中以供验证
     *
     * @return 验证码字节流及请求key
     */
    VerifyCodeBO createVerifyCode();

    /**
     * 对验证码进行正确性验证
     *
     * @param requestKey 生成验证码时附加的请求key
     * @param verifyCode 验证码
     * @return 输入的验证码是否正确
     */
    boolean isRightVerifyCode(String requestKey, String verifyCode);

    AdminAccount getAccountByToken(String token);
}
