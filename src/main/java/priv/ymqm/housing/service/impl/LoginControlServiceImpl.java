package priv.ymqm.housing.service.impl;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import priv.ymqm.housing.common.exception.HousingException;
import priv.ymqm.housing.common.util.EncryptionUtil;
import priv.ymqm.housing.common.util.VerifyCodeUtil;
import priv.ymqm.housing.domain.bo.VerifyCodeBO;
import priv.ymqm.housing.domain.po.AdminAccount;
import priv.ymqm.housing.domain.vo.UserLoginVO;
import priv.ymqm.housing.service.CurrentRequestService;
import priv.ymqm.housing.service.LoginControlService;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author chenhonnian
 * @since 2020/03/21
 */
@Service
@Slf4j
public class LoginControlServiceImpl implements LoginControlService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private CurrentRequestService currentRequestService;

    /**
     * 登录过期时间，先设成一天吧
     */
    private static final long LOGIN_EXPIRE_SECONDS = 24 * 60 * 60;

    /**
     * 验证码过期时间, 先设成2分钟
     */
    private static final long LOGIN_VERIFY_CODE_EXPIRE_SECONDS = 2 * 60;

    /**
     * 用户登录TOKEN存放
     */
    private static final String LOGIN_REDIS_KEY = "housing:LoginControlService:userToken";

    /**
     * 用户多设备登录TOKEN记录
     */
    private static final String LOGIN_TOKEN_REVERSE_RECORD_KEY = "housing:LoginControlService:tokenRecord";

    /**
     * 用户登录验证码存放
     */
    private static final String LOGIN_VERIFY_CODE_REDIS_KEY = "housing:LoginControlService:verifyCode";

    private static final Random RANDOM = new Random();

    @Override
    public UserLoginVO setLoginState(AdminAccount adminAccount) {
        String token = EncryptionUtil.createUserToken();
        String tokenKey = redisKey(LOGIN_REDIS_KEY, token);
        String accountJsonPayLoad = JSON.toJSONString(adminAccount);
        Boolean saveSuccess = redisTemplate.opsForValue().setIfAbsent(tokenKey, accountJsonPayLoad,
                LOGIN_EXPIRE_SECONDS, TimeUnit.SECONDS);
        if (Boolean.FALSE.equals(saveSuccess)) {
            log.warn("账号登录生成的TOKEN重复，账号id：{}，TOKEN:{}", adminAccount.getId(), token);
            throw new HousingException("TOKEN重复，请尝试重新登录");
        }
        // 多设备登录相关
        String tokenGrantRecordKey = redisKey(LOGIN_TOKEN_REVERSE_RECORD_KEY, adminAccount.getId());
        redisTemplate.opsForList().rightPush(tokenGrantRecordKey, token);

        UserLoginVO userLoginVO = new UserLoginVO();
        userLoginVO.setUserId(adminAccount.getId());
        userLoginVO.setUserName(adminAccount.getUserName());
        userLoginVO.setToken(token);
        return userLoginVO;
    }

    @Override
    public void logOut() {
        String requestToken = currentRequestService.currentRequestToken();
        if (StringUtils.isBlank(requestToken)) {
            throw new HousingException("当前账号已退出");
        }
        String tokenKey = redisKey(LOGIN_REDIS_KEY, requestToken);
        redisTemplate.delete(tokenKey);
    }

    @Override
    public VerifyCodeBO createVerifyCode() {
        VerifyCodeBO verifyCodeBO = VerifyCodeUtil.generateVerifyCode();
        long nanoTime = System.nanoTime();
        // 生成验证码对应的请求key，登录时需要带上请求key和验证码进行校验
        String requestKey = nanoTime + "-" + RANDOM.nextLong();
        String redisKey = LOGIN_VERIFY_CODE_REDIS_KEY + ":" + requestKey;
        Boolean saveSuccess = redisTemplate.opsForValue().setIfAbsent(redisKey, verifyCodeBO.getVerifyCodeText(),
                LOGIN_VERIFY_CODE_EXPIRE_SECONDS, TimeUnit.SECONDS);
        if (Boolean.FALSE.equals(saveSuccess)) {
            log.error("验证码request key重复了, key: {}", redisKey);
            throw new HousingException("获取验证码失败，请重新获取");
        }
        verifyCodeBO.setRequestKey(requestKey);
        return verifyCodeBO;
    }

    @Override
    public boolean isRightVerifyCode(String requestKey, String verifyCode) {
        String redisKey = redisKey(LOGIN_VERIFY_CODE_REDIS_KEY, requestKey);
        String savedVerifyCode = redisTemplate.opsForValue().get(redisKey);
        if (savedVerifyCode == null) {
            throw new HousingException("验证码过期，请重新获取验证码");
        }
        boolean isPassVerify = StringUtils.containsIgnoreCase(savedVerifyCode, verifyCode);
        if (isPassVerify) {
            redisTemplate.delete(redisKey);
        }
        return isPassVerify;
    }

    @Override
    public AdminAccount getAccountByToken(String token) {
        if (token == null) {
            return null;
        }
        String accountRedisKey = redisKey(LOGIN_REDIS_KEY, token);
        String accountPayLoad = redisTemplate.opsForValue().get(accountRedisKey);
        AdminAccount adminAccount = JSON.parseObject(accountPayLoad, AdminAccount.class);
        return adminAccount;
    }

    private String redisKey(String base, Object... args) {
        final String SPLICE = ":";
        StringBuilder stringBuilder = new StringBuilder(base);
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            stringBuilder.append(SPLICE);
            if (arg == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(arg.toString());
            }
        }
        return stringBuilder.toString();
    }
}
