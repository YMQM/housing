package priv.ymqm.housing.domain.dto.account;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import priv.ymqm.housing.common.group.Save;
import priv.ymqm.housing.common.group.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.List;

/**
 * @author chenhonnian
 * @since 2020/03/21
 */
@Data
public class CreateAdminAccountDTO {

    @Null(groups = Save.class)
    @NotNull(groups = Update.class)
    @ApiModelProperty(value = "主键自增", hidden = true)
    private Integer id;

    @NotBlank(message = "账户姓名不能为空")
    @ApiModelProperty(value = "姓名")
    private String userName;

    @NotBlank(message = "邮箱不能为空")
    @ApiModelProperty(value = "邮箱")
    private String email;

    @NotBlank(message = "手机号不能为空")
    @Length(min = 11, max = 11)
    @ApiModelProperty(value = "手机号")
    private String phoneNumber;

    @NotBlank(message = "密码不能为空")
    @ApiModelProperty(value = "密码")
    private String password;

    @NotBlank(message = "密码确认不能为空")
    @ApiModelProperty(value = "密码重复验证")
    private String repeatedPassword;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @NotNull
    @ApiModelProperty(value = "账号是否启用")
    private Integer state;

    @ApiModelProperty(value = "角色id列表")
    private List<Integer> roleIds;
}
