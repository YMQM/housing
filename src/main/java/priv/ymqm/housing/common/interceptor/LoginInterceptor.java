package priv.ymqm.housing.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import priv.ymqm.housing.domain.po.AdminAccount;
import priv.ymqm.housing.domain.vo.res.R;
import priv.ymqm.housing.service.CurrentRequestService;
import priv.ymqm.housing.service.LoginControlService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.google.common.cache.CacheBuilder;

/**
 * @author chenhonnian
 * @since 2020/03/22
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginControlService loginControlService;

    @Autowired
    private CurrentRequestService currentRequestService;

    private static final String REQUEST_HEADER_TOKEN_NAME = "Authorization";

    private static final R<?> NO_TOKEN_RES = R.error("请求Header中需要添加Token");

    private static final R<?> TOKEN_EXPIRE_RES = R.error("账号Token已过期");

    private static Cache<R<?>, String> resCache;

    static {
        resCache = CacheBuilder.newBuilder().maximumSize(100).build();
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(REQUEST_HEADER_TOKEN_NAME);
        if (token == null) {
            writeResponse(response, NO_TOKEN_RES);
            return false;
        }
        token = token.trim();
        AdminAccount adminAccount = loginControlService.getAccountByToken(token);
        if (adminAccount == null) {
            writeResponse(response, TOKEN_EXPIRE_RES);
            return false;
        }
        currentRequestService.setCurrentLoginUser(adminAccount);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        currentRequestService.removeCurrentLoginUser();
    }

    private void writeResponse(HttpServletResponse response, R<?> res) {
        String payLoad = resCache.getIfPresent(res);
        if (payLoad == null) {
            payLoad = JSON.toJSONString(res);
            resCache.put(res, payLoad);
        }
        try {
            response.getWriter().print(payLoad);
        } catch (IOException e) {
            log.error("写入HttpServletResponse返回结果失败", e);
        }
    }
}
