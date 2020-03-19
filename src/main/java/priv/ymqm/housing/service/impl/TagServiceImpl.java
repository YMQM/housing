package priv.ymqm.housing.service.impl;

import priv.ymqm.housing.domain.po.Tag;
import priv.ymqm.housing.dao.TagMapper;
import priv.ymqm.housing.service.TagService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

}
