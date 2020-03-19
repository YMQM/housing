package priv.ymqm.housing.service.impl;

import priv.ymqm.housing.domain.po.AdminAccount;
import priv.ymqm.housing.dao.AdminAccountMapper;
import priv.ymqm.housing.service.AdminAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 后台系统用户表 服务实现类
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
@Service
public class AdminAccountServiceImpl extends ServiceImpl<AdminAccountMapper, AdminAccount> implements AdminAccountService {

}
