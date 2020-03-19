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
 * 日志记录表
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("common_log")
@ApiModel(value="CommonLog对象", description="日志记录表")
public class CommonLog extends Model<CommonLog> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "操作者id")
    private Integer operatorId;

    @ApiModelProperty(value = "操作者姓名")
    private String operatorName;

    @ApiModelProperty(value = "操作类型")
    private Integer type;

    @ApiModelProperty(value = "操作名称")
    private String title;

    @ApiModelProperty(value = "操作内容")
    private String action;

    @ApiModelProperty(value = "操作记录时间戳")
    private Long createTime;

    @ApiModelProperty(value = "IP地址")
    private Integer ip;

    @ApiModelProperty(value = "浏览器名称")
    private String browserName;


    public static final String ID = "id";

    public static final String OPERATOR_ID = "operator_id";

    public static final String OPERATOR_NAME = "operator_name";

    public static final String TYPE = "type";

    public static final String TITLE = "title";

    public static final String ACTION = "action";

    public static final String CREATE_TIME = "create_time";

    public static final String IP = "ip";

    public static final String BROWSER_NAME = "browser_name";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
