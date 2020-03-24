package priv.ymqm.housing.service;

import priv.ymqm.housing.domain.po.AdminAccount;
import priv.ymqm.housing.domain.po.Permission;
import priv.ymqm.housing.domain.po.Role;

import java.util.List;
import java.util.Set;

public interface CurrentRequestService {
    /**
     * 获取当前登录的账号信息
     *
     * @return 账号信息
     */
    AdminAccount currentLoginUser();

    /**
     * 获取当前登录的账户id
     *
     * @return 账号id
     */
    Integer currentLoginUserId();

    /**
     * 获取当前请求的TOKEN
     *
     * @return 请求TOKEN
     */
    String currentRequestToken();

    Set<Role> currentUserRoles();

    Set<Permission> currentUserPermissions();

    /**
     * 将当前登录用户保存在ThreadLocal中，在拦截器成功拦截token后设置
     *
     * @param adminAccount 当前登录用户
     */
    void setCurrentLoginUser(AdminAccount adminAccount);

    /**
     * 清除当前登录用户，就是移除当前ThreadLocal中保存的账户信息
     */
    void removeCurrentLoginUser();
}
