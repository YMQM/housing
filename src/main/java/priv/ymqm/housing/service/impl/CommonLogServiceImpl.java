package priv.ymqm.housing.service.impl;

import priv.ymqm.housing.domain.po.CommonLog;
import priv.ymqm.housing.dao.CommonLogMapper;
import priv.ymqm.housing.service.CommonLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 日志记录表 服务实现类
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
@Service
public class CommonLogServiceImpl extends ServiceImpl<CommonLogMapper, CommonLog> implements CommonLogService {

}
