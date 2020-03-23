package priv.ymqm.housing.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import priv.ymqm.housing.domain.vo.res.R;
import priv.ymqm.housing.service.PermissionService;

/**
 * <p>
 * 权限明细表 前端控制器
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
@RestController
@RequestMapping("/housing/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @PostMapping("initSysPermit")
    public R<String> initSysPermissionPoint() {
        permissionService.scanPermissionTag();
        return R.ok("初始化成功");
    }

}

