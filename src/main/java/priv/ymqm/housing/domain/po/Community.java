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
import priv.ymqm.housing.common.group.Save;
import priv.ymqm.housing.common.group.Update;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

/**
 * <p>
 * 小区信息表
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("community")
@ApiModel(value="Community对象", description="小区信息表")
public class Community extends Model<Community> {

    private static final long serialVersionUID=1L;

    @Null(groups = Save.class)
    @NotNull(groups = Update.class)
    @ApiModelProperty(value = "主键自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "小区唯一标识", hidden = true)
    private String uniqueKey;

    @NotBlank(message = "小区名称不能为空")
    @ApiModelProperty(value = "小区名称")
    private String title;

    @NotBlank
    @ApiModelProperty(value = "省份code")
    private String provinceCode;

    @NotBlank
    @ApiModelProperty(value = "省份（一级行政区）名称")
    private String provinceName;

    @NotBlank
    @ApiModelProperty(value = "市级行政区code")
    private String cityCode;

    @NotBlank
    @ApiModelProperty(value = "市级行政区名称")
    private String cityName;

    @NotBlank
    @ApiModelProperty(value = "县级行政区code")
    private String countyCode;

    @NotBlank
    @ApiModelProperty(value = "县级行政区名称")
    private String countyName;

    @NotBlank(message = "小区详细地址不能为空")
    @ApiModelProperty(value = "小区详细地址")
    private String detailAddress;

    @Null
    @ApiModelProperty(value = "创建人id", hidden = true)
    private Integer creatorId;


    public static final String ID = "id";

    public static final String UNIQUE_KEY = "unique_key";

    public static final String TITLE = "title";

    public static final String PROVINCE_CODE = "province_code";

    public static final String PROVINCE_NAME = "province_name";

    public static final String CITY_CODE = "city_code";

    public static final String CITY_NAME = "city_name";

    public static final String COUNTY_CODE = "county_code";

    public static final String COUNTY_NAME = "county_name";

    public static final String DETAIL_ADDRESS = "detail_address";

    public static final String CREATOR_ID = "creator_id";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
