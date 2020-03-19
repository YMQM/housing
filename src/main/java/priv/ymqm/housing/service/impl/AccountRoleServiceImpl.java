package priv.ymqm.housing.service.impl;

import priv.ymqm.housing.domain.po.AccountRole;
import priv.ymqm.housing.dao.AccountRoleMapper;
import priv.ymqm.housing.service.AccountRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
