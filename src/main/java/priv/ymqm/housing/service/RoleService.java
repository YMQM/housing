package priv.ymqm.housing.service;

import priv.ymqm.housing.domain.po.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
public interface RoleService extends IService<Role> {

    boolean existRoles(List<Integer> roleIds);
}
