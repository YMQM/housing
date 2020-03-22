package priv.ymqm.housing.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import priv.ymqm.housing.domain.po.AdminAccount;
import priv.ymqm.housing.service.CurrentRequestService;
import priv.ymqm.housing.service.LoginControlService;

import javax.servlet.http.HttpServletRequest;

/**
 * @author chenhonnian
 * @since 2020/03/22
 */
@Slf4j
@Service
public class CurrentRequestServiceImpl implements CurrentRequestService {

    @Autowired
    private LoginControlService loginControlService;

    private static final String REQUEST_HEADER_TOKEN_NAME = "Authorization";

    private static ThreadLocal<AdminAccount> currentLoginUserThreadLocal = new ThreadLocal<>();

    @Override
    public AdminAccount currentLoginUser() {
        AdminAccount adminAccount = currentLoginUserThreadLocal.get();
        if (adminAccount != null) {
            return adminAccount;
        }
        HttpServletRequest request = getCurrentRequest();
        String token = request.getHeader(REQUEST_HEADER_TOKEN_NAME);
        if (token == null) {
            return null;
        }
        token = token.trim();
        adminAccount = loginControlService.getAccountByToken(token);
        return adminAccount;
    }

    @Override
    public Integer currentLoginUserId() {
        AdminAccount adminAccount = currentLoginUser();
        return adminAccount.getId();
    }

    @Override
    public String currentRequestToken() {
        HttpServletRequest request = getCurrentRequest();
        String token = request.getHeader(REQUEST_HEADER_TOKEN_NAME);
        return token;
    }

    @Override
    public void setCurrentLoginUser(AdminAccount adminAccount) {
        currentLoginUserThreadLocal.set(adminAccount);
    }

    @Override
    public void removeCurrentLoginUser() {
        currentLoginUserThreadLocal.remove();
    }

    private HttpServletRequest getCurrentRequest() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        return requestAttributes.getRequest();
    }
}
