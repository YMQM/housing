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
 * 攻略标签关联表
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("house_picking_tip_tag")
@ApiModel(value="HousePickingTipTag对象", description="攻略标签关联表")
public class HousePickingTipTag extends Model<HousePickingTipTag> {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "攻略id")
    private Integer houseTipId;

    @ApiModelProperty(value = "标签id")
    private Integer tagId;


    public static final String ID = "id";

    public static final String HOUSE_TIP_ID = "house_tip_id";

    public static final String TAG_ID = "tag_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
