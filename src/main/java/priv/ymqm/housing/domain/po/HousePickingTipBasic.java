package priv.ymqm.housing.domain.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.time.LocalDate;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 购房攻略基础信息表
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("house_picking_tip_basic")
@ApiModel(value="HousePickingTipBasic对象", description="购房攻略基础信息表")
public class HousePickingTipBasic extends Model<HousePickingTipBasic> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "攻略唯一标识")
    private String uniqueKey;

    @ApiModelProperty(value = "攻略所属城市")
    private String cityName;

    @ApiModelProperty(value = "小区id")
    private Integer communityId;

    @ApiModelProperty(value = "攻略标题")
    private String title;

    @ApiModelProperty(value = "踩盘时间")
    private LocalDate surveyTime;

    @ApiModelProperty(value = "创建者id")
    private Integer creatorId;

    @ApiModelProperty(value = "创建者姓名")
    private String creatorName;

    @ApiModelProperty(value = "创建时间戳")
    private Long createTimestamp;

    @ApiModelProperty(value = "最后一次更新操作者id")
    private Integer updateUserId;

    @ApiModelProperty(value = "最后一次更新操作时间")
    private Long updateTimestamp;

    @ApiModelProperty(value = "审核人id")
    private Integer reviewerId;

    @ApiModelProperty(value = "审核时间")
    private Long reviewTimestamp;

    @ApiModelProperty(value = "攻略状态")
    private Integer state;

    @ApiModelProperty(value = "逻辑删除")
    private Boolean isDel;


    public static final String ID = "id";

    public static final String UNIQUE_KEY = "unique_key";

    public static final String CITY_NAME = "city_name";

    public static final String COMMUNITY_ID = "community_id";

    public static final String TITLE = "title";

    public static final String SURVEY_TIME = "survey_time";

    public static final String CREATOR_ID = "creator_id";

    public static final String CREATOR_NAME = "creator_name";

    public static final String CREATE_TIMESTAMP = "create_timestamp";

    public static final String UPDATE_USER_ID = "update_user_id";

    public static final String UPDATE_TIMESTAMP = "update_timestamp";

    public static final String REVIEWER_ID = "reviewer_id";

    public static final String REVIEW_TIMESTAMP = "review_timestamp";

    public static final String STATE = "state";

    public static final String IS_DEL = "is_del";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
