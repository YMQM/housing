package priv.ymqm.housing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.TimeoutUtils;
import org.springframework.transaction.annotation.Transactional;
import priv.ymqm.housing.common.enums.AccountStateEnum;
import priv.ymqm.housing.common.exception.HousingException;
import priv.ymqm.housing.common.util.EncryptionUtil;
import priv.ymqm.housing.domain.dto.account.CreateAdminAccountDTO;
import priv.ymqm.housing.domain.dto.account.QueryAdminAccountDTO;
import priv.ymqm.housing.domain.po.AdminAccount;
import priv.ymqm.housing.dao.AdminAccountMapper;
import priv.ymqm.housing.domain.vo.account.AccountDetailVO;
import priv.ymqm.housing.service.AccountRoleService;
import priv.ymqm.housing.service.AdminAccountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;

/**
 * <p>
 * 后台系统用户表 服务实现类
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
@Service
@Slf4j
public class AdminAccountServiceImpl extends ServiceImpl<AdminAccountMapper, AdminAccount> implements AdminAccountService {

    @Autowired
    private AccountRoleService accountRoleService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createAccount(CreateAdminAccountDTO createAdminAccountDTO) {
        if (!createAdminAccountDTO.getPassword().equals(createAdminAccountDTO.getRepeatedPassword())) {
            throw new HousingException("输入密码不一致");
        }

        // 设置创建时间
        AdminAccount adminAccount = new AdminAccount();
        BeanUtils.copyProperties(createAdminAccountDTO, adminAccount);
        adminAccount.setCreateTimestamp(System.currentTimeMillis())
                .setIsDel(false);

        // 加盐对密码进行哈希
        String salt = EncryptionUtil.generateRandomSalt();
        String hashPass = EncryptionUtil.sha256Hash(createAdminAccountDTO.getPassword(), salt);
        adminAccount.setPassword(hashPass);
        adminAccount.setSalt(salt);
        if (!this.save(adminAccount)) {
            return false;
        }

        // 保存账户拥有角色关系
        List<Integer> roleIds = createAdminAccountDTO.getRoleIds();
        if (CollectionUtils.isNotEmpty(roleIds)) {
            accountRoleService.setAccountRoles(adminAccount.getId(), roleIds);
        }
        return true;
    }

    @Override
    public IPage<AccountDetailVO> searchAccount(QueryAdminAccountDTO queryAdminAccountDTO) {
        Page<AccountDetailVO> pageParam = new Page<>(queryAdminAccountDTO.getPage(), queryAdminAccountDTO.getSize());
        IPage<AccountDetailVO> adminAccountIPage = baseMapper.searchPage(pageParam, queryAdminAccountDTO);
        return adminAccountIPage;
    }

    @Override
    public boolean isEmailUsed(String email) {
        if (email == null) {
            throw new HousingException("账户邮箱不能为null");
        }
        String trimEmail = email.trim();
        if (StringUtils.isBlank(trimEmail)) {
            throw new HousingException("账户邮箱不能为空");
        }
        LambdaQueryWrapper<AdminAccount> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdminAccount::getEmail, trimEmail);
        return this.count(queryWrapper) > 0;
    }

    @Override
    public boolean isPhoneUsed(String phoneNumber) {
        if (phoneNumber == null) {
            throw new HousingException("账户手机号不能为null");
        }
        String trimPhoneNum = phoneNumber.trim();
        if (StringUtils.isBlank(trimPhoneNum)) {
            throw new HousingException("账户手机号不能为空");
        }
        LambdaQueryWrapper<AdminAccount> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(AdminAccount::getPhoneNumber, trimPhoneNum);
        return this.count(queryWrapper) > 0;
    }

    @Override
    public boolean canLoginWithId(Integer accountId, String password) {
        AdminAccount adminAccount = this.getById(accountId);
        if (adminAccount == null) {
            return false;
        }
        return verifyPassword(adminAccount, password);
    }

    @Override
    public boolean canLoginWithEmail(String email, String password) {
        List<AdminAccount> adminAccounts = this.list(
                new LambdaQueryWrapper<AdminAccount>().eq(AdminAccount::getEmail, email));
        if (CollectionUtils.isEmpty(adminAccounts)) {
            return false;
        }
        if (adminAccounts.size() > 1) {
            log.warn("账号邮箱重复，邮箱：{}", email);
        }
        AdminAccount adminAccount = adminAccounts.get(0);
        return verifyPassword(adminAccount, password);
    }

    @Override
    public boolean canLoginWithPhone(String phoneNum, String password) {
        List<AdminAccount> adminAccounts = this.list(
                new LambdaQueryWrapper<AdminAccount>().eq(AdminAccount::getEmail, phoneNum));
        if (CollectionUtils.isEmpty(adminAccounts)) {
            return false;
        }
        if (adminAccounts.size() > 1) {
            log.warn("账号手机号重复，手机号：{}", phoneNum);
        }
        AdminAccount adminAccount = adminAccounts.get(0);
        return verifyPassword(adminAccount, password);
    }

    @Override
    public AdminAccount getAdminAccount(String subjectName, String subjectValue) {
        List<AdminAccount> adminAccounts = this.list(new QueryWrapper<AdminAccount>().eq(subjectName, subjectValue));
        if (CollectionUtils.isEmpty(adminAccounts)) {
            return null;
        }
        if (adminAccounts.size() > 1) {
            log.warn("账号主体重复, 主体类型: {}, 主体：{}", subjectName, subjectValue);
        }
        AdminAccount adminAccount = adminAccounts.get(0);
        return adminAccount;
    }

    @Override
    public boolean verifyPassword(AdminAccount adminAccount, String password) {
        String salt = adminAccount.getSalt();
        if (StringUtils.isBlank(salt)) {
            log.warn("账号{}没有设置密码哈希盐值", adminAccount.getId());
        }
        String passwordHash = EncryptionUtil.sha256Hash(password, salt);
        boolean rightPassword = Objects.equals(passwordHash, adminAccount.getPassword());
        return rightPassword;
    }

}
