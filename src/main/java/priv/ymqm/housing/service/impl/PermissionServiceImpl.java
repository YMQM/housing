package priv.ymqm.housing.service.impl;

import io.swagger.annotations.Api;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponentsBuilder;
import priv.ymqm.housing.common.annotation.HasPermission;
import priv.ymqm.housing.common.exception.HousingException;
import priv.ymqm.housing.common.util.ReflectUtil;
import priv.ymqm.housing.domain.po.Permission;
import priv.ymqm.housing.dao.PermissionMapper;
import priv.ymqm.housing.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * <p>
 * 权限明细表 服务实现类
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService,
        ApplicationContextAware {

    private ApplicationContext context;

    private static final Class<? extends Annotation> SCAN_ANNOTATION = RequestMapping.class;

    @Autowired
    private PermissionService selfPermissionService;


    @Override
    public void scanPermissionTag() {
        Map<String, Object> controllerBeanMap = context.getBeansWithAnnotation(SCAN_ANNOTATION);
        for (Map.Entry<String, Object> entry : controllerBeanMap.entrySet()) {
            String controllerBeanName = entry.getKey();
            Object controllerBean = entry.getValue();
            Class<?> controllerCls = ClassUtils.getUserClass(controllerBean);
            RequestMapping requestMapping = context.findAnnotationOnBean(controllerBeanName, RequestMapping.class);
            if (requestMapping == null) {
                continue;
            }
            String[] controllerPaths = requestMapping.path();
            if (controllerPaths.length < 1) {
                continue;
            }
            Method[] methods = ReflectUtil.findMethodsWithAnnotation(controllerCls, HasPermission.class);
            if (methods.length == 0) {
                continue;
            }
            String controllerPath = controllerPaths[0];
            String controllerName = controllerCls.getSimpleName();
            String controllerApiTagName = null;
            Api apiAnnotation = controllerCls.getAnnotation(Api.class);
            if (apiAnnotation != null && apiAnnotation.tags().length > 0) {
                controllerApiTagName = apiAnnotation.tags()[0];
            }
            String controllerPermitName = controllerName;
            if (controllerApiTagName != null) {
                controllerPermitName = controllerApiTagName;
            }

            Permission controllerPermission = new Permission();
            controllerPermission.setParentId(-1);
            controllerPermission.setName(controllerPermitName);
            controllerPermission.setUniqueKey(controllerCls.getName());
            controllerPermission.setUrl(controllerPath);
            controllerPermission.setIsLeaf(false);
            controllerPermission.setDetail("Controller");
            try {
                selfPermissionService.savePermission(controllerPermission);
            } catch (DataIntegrityViolationException ex) {
                continue;
            }
            scanRequestMethods(methods, controllerName, controllerPath, controllerPermission.getId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean savePermission(Permission permission) {
        Integer pid = permission.getParentId();
        Permission parentPermission = this.getById(pid);
        if (pid == -1) {
            parentPermission = Permission.rootPermission();
        }
        if (parentPermission == null) {
            throw new HousingException("父级权限节点不存在");
        }
        if (parentPermission.getIsLeaf()) {
            throw new HousingException("父级权限节点为叶子节点，不能添加子节点");
        }
        permission.setIsNeedCheck(false);
        permission.setTreePath("tem");
        permission.setCreateTime(System.currentTimeMillis());
        boolean saveSuccess = this.save(permission);
        if (!saveSuccess) {
            return false;
        }
        Integer insertId = permission.getId();
        String selfTreePath = parentPermission.getTreePath() + "," + insertId;
        permission.setTreePath(selfTreePath);
        return this.updateById(permission);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)  {
        this.context = applicationContext;
    }

    private void scanRequestMethods(Method[] requestMethods, String controllerSimpleName,
                                    String controllerPath, Integer controllerPermitId) {
        for (Method requestMethod : requestMethods) {
            HasPermission hasPermissionAnnotation = requestMethod.getAnnotation(HasPermission.class);
            if (hasPermissionAnnotation == null) {
                continue;
            }
            String methodRequestPath = getControllerMethodRequestPath(requestMethod);
            if (methodRequestPath == null) {
                continue;
            }

            String requestMethodName = requestMethod.getName();
            String uniquePermitKey = controllerSimpleName + "." + requestMethodName;
            String permitName = hasPermissionAnnotation.name();
            if (permitName == null) {
                permitName = uniquePermitKey;
            }
            String fullRequestPath = UriComponentsBuilder.fromPath(controllerPath)
                    .pathSegment(methodRequestPath).toUriString();

            Permission permissionDO = new Permission();
            permissionDO.setIsLeaf(true);
            permissionDO.setName(permitName);
            permissionDO.setUrl(fullRequestPath);
            permissionDO.setUniqueKey(uniquePermitKey);
            permissionDO.setParentId(controllerPermitId);
            permissionDO.setDetail("System init");
            permissionDO.setCreateTime(System.currentTimeMillis());
            try {
                selfPermissionService.savePermission(permissionDO);
            } catch (DataIntegrityViolationException ignored) {

            }
        }
    }

    private String getControllerMethodRequestPath(Method requestMethod) {
        PostMapping postMapping = requestMethod.getAnnotation(PostMapping.class);
        GetMapping getMapping = requestMethod.getAnnotation(GetMapping.class);
        RequestMapping methodRequestMapping = requestMethod.getAnnotation(RequestMapping.class);
        if (postMapping != null) {
            if (postMapping.value().length > 0) {
                return postMapping.value()[0];
            }
            if (postMapping.path().length > 0) {
                return postMapping.path()[0];
            }
        }
        if (getMapping != null) {
            if (getMapping.value().length > 0) {
                return getMapping.value()[0];
            }
            if (getMapping.path().length > 0) {
                return getMapping.value()[0];
            }
        }
        if (methodRequestMapping != null) {
            if (methodRequestMapping.value().length > 0) {
                return methodRequestMapping.value()[0];
            }
            if (methodRequestMapping.path().length > 0) {
                return methodRequestMapping.path()[0];
            }
        }
        return null;
    }

}
