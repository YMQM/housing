package priv.ymqm.housing.domain.dto.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import priv.ymqm.housing.domain.vo.req.PageVO;

import javax.validation.constraints.Min;

/**
 * @author chenhonnian
 * @since 2020/03/21
 */
@Data
public class QueryAdminAccountDTO extends PageVO {

    @ApiModelProperty(value = "姓名")
    private String name;

    @Min(value = 0)
    @ApiModelProperty(value = "工号")
    private Integer id;

    @Min(value = 0)
    @ApiModelProperty(value = "角色id")
    private Integer roleId;

    @Min(value = 0)
    @ApiModelProperty(value = "账户状态")
    private Integer state;
}
