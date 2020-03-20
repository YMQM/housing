package priv.ymqm.housing.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import priv.ymqm.housing.common.annotation.ValidateJson;
import priv.ymqm.housing.common.group.Save;
import priv.ymqm.housing.common.group.Update;
import priv.ymqm.housing.domain.po.Community;
import priv.ymqm.housing.domain.vo.req.CommunityReq;
import priv.ymqm.housing.domain.vo.res.R;
import priv.ymqm.housing.service.CommunityService;

import java.util.List;

/**
 * <p>
 * 小区信息表 前端控制器
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
@RestController
@RequestMapping("/housing/community")
@Api(tags = "小区管理")
public class CommunityController {

    @Autowired
    private CommunityService communityService;

    private static final int MAX_COMMUNITY_FETCH_SIZE = 100;

    @PostMapping("save")
    @ValidateJson(group = Save.class)
    public R<String> saveCommunity(@RequestBody Community community) {
        boolean saveSuccess = communityService.saveCommunity(community);
        return saveSuccess ? R.ok("小区信息保存成功") : R.error("小区信息保存失败");
    }

    @GetMapping("delete")
    public R<String> deleteCommunity(@RequestParam Integer communityId) {
        boolean deleteSuccess = communityService.deleteCommunity(communityId);
        return deleteSuccess ? R.ok("小区删除成功") : R.error("小区删除失败");
    }

    @PostMapping("update")
    @ValidateJson(group = Update.class)
    public R<String> updateCommunity(@RequestBody Community community) {
        boolean saveSuccess = communityService.updateById(community);
        return saveSuccess ? R.ok("小区信息修改成功") : R.error("小区信息修改失败");
    }

    @GetMapping("get")
    public R<Community> getCommunity(@RequestParam Integer communityId) {
        Community community = communityService.getById(communityId);
        return community == null ? R.error("小区不存在") : R.ok(community);
    }

    @PostMapping("search")
    public R<IPage<Community>> searchCommunity(@RequestBody CommunityReq communityReq) {
        IPage<Community> communityIPage = communityService.searchCommunity(communityReq);
        return R.ok(communityIPage);
    }

    @GetMapping("findByName")
    public R<List<Community>> findCommunityByName(@RequestParam String communityName) {
        List<Community> communities = communityService.findByName(communityName, MAX_COMMUNITY_FETCH_SIZE);
        return R.ok(communities);
    }
}

