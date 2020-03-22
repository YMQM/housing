package priv.ymqm.housing.service;

import priv.ymqm.housing.domain.po.AccountRole;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户角色分配关联表 服务类
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
public interface AccountRoleService extends IService<AccountRole> {

    /**
     * 设置账户所属角色
     *
     * @param accountId 账户id
     * @param roleIds   角色id列表
     * @return 是否设置成功
     */
    boolean setAccountRoles(Integer accountId, List<Integer> roleIds);


}
