package priv.ymqm.housing.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 后台系统用户表
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("admin_account")
@ApiModel(value="AdminAccount对象", description="后台系统用户表")
public class AdminAccount extends Model<AdminAccount> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "姓名")
    private String userName;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String phoneNumber;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "密码加盐")
    private String salt;

    @ApiModelProperty(value = "备注信息")
    private String remark;

    @ApiModelProperty(value = "账号是否启用")
    private Integer state;

    @ApiModelProperty(value = "创建时间戳")
    private Long createTimestamp;

    @ApiModelProperty(value = "最后登录时间")
    private Long lastLogin;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean isDel;


    public static final String ID = "id";

    public static final String USER_NAME = "user_name";

    public static final String EMAIL = "email";

    public static final String PHONE_NUMBER = "phone_number";

    public static final String PASSWORD = "password";

    public static final String REMARK = "remark";

    public static final String ROLE_ID = "role_id";

    public static final String IS_ACTIVE = "is_active";

    public static final String CREATE_TIMESTAMP = "create_timestamp";

    public static final String LAST_LOGIN = "last_login";

    public static final String IS_DEL = "is_del";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
