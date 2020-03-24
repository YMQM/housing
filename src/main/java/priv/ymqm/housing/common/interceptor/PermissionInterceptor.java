package priv.ymqm.housing.common.interceptor;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Charsets;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.UrlPathHelper;
import priv.ymqm.housing.domain.po.Permission;
import priv.ymqm.housing.domain.vo.res.R;
import priv.ymqm.housing.service.CurrentRequestService;
import priv.ymqm.housing.service.PermissionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Set;

/**
 * @author chenhonnian
 * @since 2020/03/23
 */
@Slf4j
@Component
public class PermissionInterceptor implements HandlerInterceptor {

    @Autowired
    private CurrentRequestService currentRequestService;

    @Autowired
    private PermissionService permissionService;

    private static final UrlPathHelper urlPathHelper = new UrlPathHelper();

    private static Cache<R<?>, String> resCache;

    static {
        resCache = CacheBuilder.newBuilder().maximumSize(100).build();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestUri = request.getRequestURI();
        String lookupPathForRequest = urlPathHelper.getLookupPathForRequest(request);
        String contextName = request.getContextPath();
        if (contextName != null) {
            requestUri = requestUri.replaceFirst(contextName, "");
        }
        if (!Objects.equals(requestUri, lookupPathForRequest)) {
            log.warn("权限拦截uri异常，请求uri：{} 匹配uri：{}", requestUri, lookupPathForRequest);
            requestUri = lookupPathForRequest;
        }
        Permission resourcePermission = permissionService.getPermissionByUri(requestUri);
        if (resourcePermission == null) {
            return true;
        }
        if (Boolean.FALSE.equals(resourcePermission.getIsNeedCheck())) {
            return true;
        }
        Set<Permission> userPermits = currentRequestService.currentUserPermissions();
        if (userPermits == null || userPermits.isEmpty()) {
            writeResponse(response, R.error("权限不足"));
            return false;
        }
        boolean hasPermission = userPermits.contains(resourcePermission);
        if (!hasPermission) {
            writeResponse(response, R.error("权限不足"));
        }
        return hasPermission;
    }


    private void writeResponse(HttpServletResponse response, R<?> res) {
        String payLoad = resCache.getIfPresent(res);
        if (payLoad == null) {
            payLoad = JSON.toJSONString(res);
            resCache.put(res, payLoad);
        }
        try {
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.getWriter().print(payLoad);
        } catch (IOException e) {
            log.error("写入HttpServletResponse返回结果失败", e);
        }
    }
}
