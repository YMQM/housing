package priv.ymqm.housing.service.impl;

import priv.ymqm.housing.domain.po.Permission;
import priv.ymqm.housing.dao.PermissionMapper;
import priv.ymqm.housing.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限明细表 服务实现类
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
