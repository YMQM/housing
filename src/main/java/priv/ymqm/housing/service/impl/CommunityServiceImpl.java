package priv.ymqm.housing.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import priv.ymqm.housing.common.enums.SearchAreaTypeEnum;
import priv.ymqm.housing.common.util.EnumUtil;
import priv.ymqm.housing.domain.po.Community;
import priv.ymqm.housing.dao.CommunityMapper;
import priv.ymqm.housing.domain.vo.req.CommunityReq;
import priv.ymqm.housing.service.CommunityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 小区信息表 服务实现类
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
@Service
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, Community> implements CommunityService {

    @Override
    public IPage<Community> searchCommunity(CommunityReq communityReq) {
        LambdaQueryWrapper<Community> communityQueryWrapper = buildSearchQuery(communityReq);
        Page<Community> pageParam = new Page<>(communityReq.getPage(), communityReq.getSize());
        return baseMapper.selectPage(pageParam, communityQueryWrapper);
    }

    @Override
    public List<Community> findByName(String name, int maxFetchSize) {
        if (StringUtils.isBlank(name)) {
            return Collections.emptyList();
        }
        LambdaQueryWrapper<Community> communityQueryWrapper = new LambdaQueryWrapper<>();
//        communityQueryWrapper.select(Community::getId);
//        communityQueryWrapper.select(Community::getTitle);
        communityQueryWrapper.like(Community::getTitle, name);
        Page<Community> firstPage = new Page<>(1, maxFetchSize);
        IPage<Community> pageData = baseMapper.selectPage(firstPage, communityQueryWrapper);
        return pageData.getRecords();
    }

    @Override
    public boolean saveCommunity(Community community) {
        return false;
    }

    @Override
    public boolean deleteCommunity(Integer communityId) {
        return false;
    }

    private LambdaQueryWrapper<Community> buildSearchQuery(CommunityReq communityReq) {
        LambdaQueryWrapper<Community> queryWrapper = new LambdaQueryWrapper<>();
        // 根据小区id进行精确搜索，可以直接返回
        if (communityReq.getCommunityId() != null) {
            queryWrapper.eq(Community::getId, communityReq.getCommunityId());
            return queryWrapper;
        }
        String communityName = communityReq.getCommunityName();
        if (StringUtils.isNotBlank(communityName)) {
            queryWrapper.like(Community::getTitle, communityName);
        }
        if (communityReq.getCreatorId() != null) {
            queryWrapper.eq(Community::getCreatorId, communityReq.getCreatorId());
        }
        // 根据所属地区搜索，可能指定省级、市级、或县级三个级别的地区；
        SearchAreaTypeEnum searchAreaType = EnumUtil.getEnumByCode(SearchAreaTypeEnum.class,
                communityReq.getAreaType());
        if (searchAreaType != null) {
            queryWrapper.eq(searchAreaType.getField(), communityReq.getAreaCode());
        }
        return queryWrapper;
    }
}
