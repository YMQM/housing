package priv.ymqm.housing.service;

import priv.ymqm.housing.domain.po.Permission;
import priv.ymqm.housing.domain.po.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 角色权限分配关联表 服务类
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
public interface RolePermissionService extends IService<RolePermission> {

    Set<Integer> listPermitIdsByRoleId(Integer roleId);

    Set<Permission> listPermitsByRoleId(Integer roleId);

    Set<Integer> listPermitIdsByRoleIds(List<Integer> roleIds);

    Set<Permission> listPermitsByRoleIds(List<Integer> roleIds);


}
