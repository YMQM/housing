package priv.ymqm.housing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import priv.ymqm.housing.domain.po.AccountRole;
import priv.ymqm.housing.dao.AccountRoleMapper;
import priv.ymqm.housing.domain.po.Role;
import priv.ymqm.housing.service.AccountRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import priv.ymqm.housing.service.RoleService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户角色分配关联表 服务实现类
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
@Service
public class AccountRoleServiceImpl extends ServiceImpl<AccountRoleMapper, AccountRole> implements AccountRoleService {

    @Autowired
    private RoleService roleService;

    @Override
    public boolean setAccountRoles(Integer accountId, List<Integer> roleIds) {
        return false;
    }

    @Override
    public List<Integer> listRoleIdsByUserId(Integer userId) {
        LambdaQueryWrapper<AccountRole> queryWrapper = new LambdaQueryWrapper<AccountRole>()
                .eq(AccountRole::getAccountId, userId).select(AccountRole::getRoleId);
        List<AccountRole> accountRoles = this.list(queryWrapper);
        List<Integer> roleIds = accountRoles.stream().map(AccountRole::getRoleId).collect(Collectors.toList());
        return roleIds;
    }

    @Override
    public List<Role> listRolesByUserId(Integer userId) {
        List<Integer> roleIds = this.listRoleIdsByUserId(userId);
        return roleService.listByIds(roleIds);
    }
}
