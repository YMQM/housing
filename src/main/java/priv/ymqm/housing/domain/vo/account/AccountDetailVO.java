package priv.ymqm.housing.domain.vo.account;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import priv.ymqm.housing.domain.vo.RoleListVO;

import java.util.List;

/**
 * @author chenhonnian
 * @since 2020/03/21
 */
@Data
public class AccountDetailVO {
    @ApiModelProperty(value = "主键自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String phoneNumber;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @ApiModelProperty(value = "账号状态")
    private Integer state;

    @ApiModelProperty(value = "创建时间戳")
    private Long createTimestamp;

    @ApiModelProperty(value = "最后登录时间")
    private Long lastLogin;

    @ApiModelProperty(value = "账户拥有角色集合")
    private List<RoleListVO> roles;

}
