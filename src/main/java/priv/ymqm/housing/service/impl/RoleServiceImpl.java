package priv.ymqm.housing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import priv.ymqm.housing.domain.po.Role;
import priv.ymqm.housing.dao.RoleMapper;
import priv.ymqm.housing.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public boolean existRoles(List<Integer> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return false;
        }
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<Role>().in(Role::getId, roleIds);
        int count = this.count(queryWrapper);
        return count == roleIds.size();
    }
}
