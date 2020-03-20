package priv.ymqm.housing.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import priv.ymqm.housing.domain.po.Tag;
import com.baomidou.mybatisplus.extension.service.IService;
import priv.ymqm.housing.domain.vo.req.TagReq;

import java.util.*;
/**
 * <p>
 * 标签表 服务类
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
public interface TagService extends IService<Tag> {
    /**
     * 根据名称和类型搜索标签
     * @param tagReq 标签VO
     * @return 标签分页数据
     */
    IPage<Tag> findTagsByNameAndType(TagReq tagReq);

    /**
     * 获取现有的标签类型
     * @param maxFetchSize 最大获取数量
     * @return 标签类型List
     */
    List<String> listTagTypes(int maxFetchSize);

}
