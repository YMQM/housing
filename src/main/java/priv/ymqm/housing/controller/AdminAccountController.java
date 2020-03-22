package priv.ymqm.housing.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import priv.ymqm.housing.common.annotation.ValidateJson;
import priv.ymqm.housing.common.group.Save;
import priv.ymqm.housing.domain.dto.account.CreateAdminAccountDTO;
import priv.ymqm.housing.domain.dto.account.QueryAdminAccountDTO;
import priv.ymqm.housing.domain.vo.account.AccountDetailVO;
import priv.ymqm.housing.domain.vo.res.R;
import priv.ymqm.housing.service.AdminAccountService;
import priv.ymqm.housing.service.RoleService;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 后台系统用户表 前端控制器
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
@RestController
@RequestMapping("/housing/adminAccount")
public class AdminAccountController {
    @Autowired
    private AdminAccountService adminAccountService;

    @Autowired
    private RoleService roleService;

    @PostMapping("create")
    @ValidateJson(group = Save.class)
    public R<String> createAccount(@RequestBody CreateAdminAccountDTO accountDTO) {
        String password = accountDTO.getPassword();
        String repeatedPassword = accountDTO.getRepeatedPassword();
        if (!Objects.equals(password, repeatedPassword)) {
            return R.error("两次输入的密码不一致");
        }
        boolean emailExist = adminAccountService.isEmailUsed(accountDTO.getEmail());
        if (emailExist) {
            return R.error("邮箱已被注册");
        }
        boolean phoneNumExist = adminAccountService.isPhoneUsed(accountDTO.getPhoneNumber());
        if (phoneNumExist) {
            return R.error("手机号已被注册");
        }
        List<Integer> roleIds = accountDTO.getRoleIds();
        boolean allRolesExist = roleService.existRoles(roleIds);
        if (CollectionUtils.isNotEmpty(roleIds) && !allRolesExist) {
            return R.error("角色不存在");
        }
        boolean createSuccess = adminAccountService.createAccount(accountDTO);
        return createSuccess ? R.ok("账号创建成功") : R.error("账号创建失败");
    }

    @PostMapping("delete")
    public R<String> deleteAccount(@RequestParam Integer accountId) {
        boolean deleteSuccess = adminAccountService.removeById(accountId);
        return deleteSuccess ? R.ok("账号删除成功") : R.error("账号删除失败");
    }

    @PostMapping("search")
    public R<IPage<AccountDetailVO>> searchAccount(@RequestBody QueryAdminAccountDTO queryAdminAccountDTO) {
        IPage<AccountDetailVO> accountDetailVOIPage = adminAccountService.searchAccount(queryAdminAccountDTO);
        return R.ok(accountDetailVOIPage);
    }

}

