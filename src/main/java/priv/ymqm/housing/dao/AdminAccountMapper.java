package priv.ymqm.housing.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import priv.ymqm.housing.domain.dto.account.QueryAdminAccountDTO;
import priv.ymqm.housing.domain.po.AdminAccount;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import priv.ymqm.housing.domain.vo.account.AccountDetailVO;

/**
 * <p>
 * 后台系统用户表 Mapper 接口
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
public interface AdminAccountMapper extends BaseMapper<AdminAccount> {

    IPage<AccountDetailVO> searchPage(Page<AccountDetailVO> page, @Param("queryDto") QueryAdminAccountDTO queryAccountDTO);

}
