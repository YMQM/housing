package priv.ymqm.housing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import priv.ymqm.housing.domain.po.Permission;
import priv.ymqm.housing.domain.po.RolePermission;
import priv.ymqm.housing.dao.RolePermissionMapper;
import priv.ymqm.housing.service.PermissionService;
import priv.ymqm.housing.service.RolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色权限分配关联表 服务实现类
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    @Autowired
    private PermissionService permissionService;

    @Override
    public Set<Integer> listPermitIdsByRoleId(Integer roleId) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RolePermission::getRoleId, roleId)
                .select(RolePermission::getPermissionId);
        List<RolePermission> rolePermissions = this.list(queryWrapper);
        Set<Integer> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId).collect(Collectors.toSet());
        return permissionIds;
    }

    @Override
    public Set<Permission> listPermitsByRoleId(Integer roleId) {
        Set<Integer> permissionIds = this.listPermitIdsByRoleId(roleId);
        if (permissionIds == null || permissionIds.isEmpty()) {
            return Collections.emptySet();
        }
        List<Permission> permissions = permissionService.listByIds(permissionIds);
        return new HashSet<>(permissions);
    }

    @Override
    public Set<Integer> listPermitIdsByRoleIds(List<Integer> roleIds) {
        LambdaQueryWrapper<RolePermission> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(RolePermission::getRoleId, roleIds)
                .select(RolePermission::getPermissionId);
        List<RolePermission> rolePermissions = this.list(queryWrapper);
        Set<Integer> permissionIds = rolePermissions.stream()
                .map(RolePermission::getPermissionId).collect(Collectors.toSet());
        return permissionIds;
    }

    @Override
    public Set<Permission> listPermitsByRoleIds(List<Integer> roleIds) {
        Set<Integer> permissionIds = this.listPermitIdsByRoleIds(roleIds);
        if (permissionIds == null || permissionIds.isEmpty()) {
            return Collections.emptySet();
        }
        List<Permission> permissions = permissionService.listByIds(permissionIds);
        return new HashSet<>(permissions);
    }
}
