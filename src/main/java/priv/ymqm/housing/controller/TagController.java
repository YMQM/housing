package priv.ymqm.housing.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import priv.ymqm.housing.common.annotation.ValidateJson;
import priv.ymqm.housing.common.group.Save;
import priv.ymqm.housing.common.group.Update;
import priv.ymqm.housing.domain.po.Tag;
import priv.ymqm.housing.domain.vo.req.TagReq;
import priv.ymqm.housing.domain.vo.res.R;
import priv.ymqm.housing.service.TagService;

/**
 * <p>
 * 标签表 前端控制器
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
@RestController
@RequestMapping("/housing/tag")
@Api(tags = "标签管理")
public class TagController {

    @Autowired
    private TagService tagService;

    @PostMapping("save")
    @ValidateJson(group = Save.class)
    public R<String> saveTag(@RequestBody Tag tag) {
        boolean saveSuccess = tagService.save(tag);
        return saveSuccess ? R.ok("标签保存成功") : R.error("标签保存失败");
    }

    @PostMapping("delete")
    public R<String> deleteTag(@RequestParam(name = "tagId") Integer tagId) {
        boolean deleteSuccess = tagService.removeById(tagId);
        return deleteSuccess ? R.ok("标签删除成功") : R.error("标签删除失败");
    }

    @PostMapping("update")
    @ValidateJson(group = Update.class)
    public R<String> updateTag(@RequestBody Tag tag) {
        boolean updateSuccess = tagService.updateById(tag);
        return updateSuccess ? R.ok("标签更新成功") : R.error("标签更新失败");
    }

    @GetMapping("get")
    public R<Tag> getTagById(@RequestParam Integer tagId) {
        Tag tag = tagService.getById(tagId);
        return tag == null ? R.error("标签不存在") : R.ok(tag);
    }

    @PostMapping("search")
    public R<IPage<Tag>> findTag(@RequestBody TagReq tagReq) {
        IPage<Tag> tagIPage = tagService.findTagsByNameAndType(tagReq);
        return R.ok(tagIPage);
    }
}

