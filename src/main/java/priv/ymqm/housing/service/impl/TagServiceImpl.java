package priv.ymqm.housing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import priv.ymqm.housing.domain.po.Tag;
import priv.ymqm.housing.dao.TagMapper;
import priv.ymqm.housing.domain.vo.req.TagReq;
import priv.ymqm.housing.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 标签表 服务实现类
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
@Service
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Override
    public IPage<Tag> findTagsByNameAndType(TagReq tagReq) {
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(tagReq.getTagName())) {
            queryWrapper.like(Tag::getTitle, tagReq.getTagName());
        }
        if (StringUtils.isNotBlank(tagReq.getTagType())) {
            queryWrapper.eq(Tag::getType, tagReq.getTagType());
        }
        Page<Tag> pageParam = new Page<>(tagReq.getPage(), tagReq.getSize());
        return baseMapper.selectPage(pageParam, queryWrapper);
    }

    @Override
    public List<String> listTagTypes(int maxFetchSize) {
        Page<Tag> pageParam = new Page<>(1, maxFetchSize);
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Tag::getType);
        IPage<Tag> pageData = baseMapper.selectPage(pageParam, queryWrapper);
        List<Tag> tagList = pageData.getRecords();
        List<String> tagTypeList = tagList.stream().map(Tag::getType).collect(Collectors.toList());
        return tagTypeList;
    }
}
