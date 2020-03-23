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
 * 权限明细表
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("permission")
@ApiModel(value="Permission对象", description="权限明细表")
public class Permission extends Model<Permission> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "权限唯一标识")
    private String uniqueKey;

    @ApiModelProperty(value = "权限描述")
    private String detail;

    @ApiModelProperty(value = "匹配的请求地址")
    private String url;

    @ApiModelProperty(value = "是否需要和用户持有权限做匹配")
    private Boolean isNeedCheck;

    @ApiModelProperty(value = "创建时间戳")
    private Long createTime;

    @ApiModelProperty(value = "父级id")
    private Integer parentId;

    @ApiModelProperty(value = "是否为叶子权限节点")
    private Boolean isLeaf;

    @ApiModelProperty(value = "树结构路径")
    private String treePath;

    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String UNIQUE_KEY = "unique_key";

    public static final String DESC = "describe";

    public static final String URL = "url";

    public static final String IS_NEED_CHECK = "is_need_check";

    public static final String CREATE_TIME = "create_time";

    public static final Permission rootPermission() {
        Permission rootPermission = new Permission();
        rootPermission.setId(-1);
        rootPermission.setName("root");
        rootPermission.setIsLeaf(false);
        rootPermission.setParentId(-1);
        rootPermission.setTreePath("-1");
        rootPermission.setDetail("root");
        return rootPermission;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
