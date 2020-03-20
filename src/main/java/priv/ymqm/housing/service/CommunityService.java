package priv.ymqm.housing.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import priv.ymqm.housing.domain.po.Community;
import com.baomidou.mybatisplus.extension.service.IService;
import priv.ymqm.housing.domain.vo.req.CommunityReq;

import java.util.*;

/**
 * <p>
 * 小区信息表 服务类
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
public interface CommunityService extends IService<Community> {
    /**
     * 根据条件查询小区列表
     *
     * @param communityReq 查询条件
     * @return 小区分页列表
     */
    IPage<Community> searchCommunity(CommunityReq communityReq);

    /**
     * 根据名称查询小区
     *
     * @param name         小区名称
     * @param maxFetchSize 最大获取条数
     * @return 小区列表
     */
    List<Community> findByName(String name, int maxFetchSize);


    /**
     * 保存小区信息
     *
     * @param community 小区信息VO
     * @return 是否保存成功
     */
    boolean saveCommunity(Community community);

    /**
     * 删除小区信息，如果存在小区对应的攻略信息，则不能删除
     *
     * @param communityId 小区ID
     * @return 是否删除成功
     */
    boolean deleteCommunity(Integer communityId);
}
