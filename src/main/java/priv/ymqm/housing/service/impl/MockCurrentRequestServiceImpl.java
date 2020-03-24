package priv.ymqm.housing.service.impl;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import priv.ymqm.housing.common.enums.AccountStateEnum;
import priv.ymqm.housing.domain.po.AdminAccount;
import priv.ymqm.housing.domain.po.Permission;
import priv.ymqm.housing.domain.po.Role;
import priv.ymqm.housing.service.CurrentRequestService;

import java.util.Set;

/**
 * @author chenhonnian
 * @since 2020/03/22
 */
@Profile("dev")
@Service
public class MockCurrentRequestServiceImpl implements CurrentRequestService {
    @Override
    public AdminAccount currentLoginUser() {
        AdminAccount mockAccount = new AdminAccount();
        long now = System.nanoTime();
        mockAccount.setId(100861120)
                .setUserName("ymqm")
                .setEmail("ymqm@qq.com")
                .setPhoneNumber("15508723779")
                .setState(AccountStateEnum.NORMAL.getCode())
                .setRemark("测试MOCK")
                .setIsDel(false)
                .setCreateTimestamp(now)
                .setLastLogin(now);
        return mockAccount;
    }

    @Override
    public Integer currentLoginUserId() {
        return 100861120;
    }

    @Override
    public String currentRequestToken() {
        return "TEST_TOKEN_FOR_DEV";
    }

    @Override
    public Set<Role> currentUserRoles() {
        return null;
    }

    @Override
    public Set<Permission> currentUserPermissions() {
        return null;
    }

    @Override
    public void setCurrentLoginUser(AdminAccount adminAccount) {

    }

    @Override
    public void removeCurrentLoginUser() {

    }
}
