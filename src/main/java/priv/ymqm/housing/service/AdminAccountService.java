package priv.ymqm.housing.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import priv.ymqm.housing.domain.dto.account.CreateAdminAccountDTO;
import priv.ymqm.housing.domain.dto.account.QueryAdminAccountDTO;
import priv.ymqm.housing.domain.po.AdminAccount;
import com.baomidou.mybatisplus.extension.service.IService;
import priv.ymqm.housing.domain.vo.account.AccountDetailVO;

/**
 * <p>
 * 后台系统用户表 服务类
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
public interface AdminAccountService extends IService<AdminAccount> {

    /**
     * 创建管理员账户
     *
     * @param createAdminAccountDTO 管理员账户DTO对象
     * @return 是否创建成功
     */
    boolean createAccount(CreateAdminAccountDTO createAdminAccountDTO);

    IPage<AccountDetailVO> searchAccount(QueryAdminAccountDTO queryAdminAccountDTO);

    boolean isEmailUsed(String email);

    boolean isPhoneUsed(String phoneNumber);

    boolean canLoginWithId(Integer accountId, String password);

    boolean canLoginWithEmail(String email, String password);

    boolean canLoginWithPhone(String phoneNum, String password);

    AdminAccount getAdminAccount(String subjectName, String subjectValue);

    boolean verifyPassword(AdminAccount adminAccount, String password);
}
