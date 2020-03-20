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
 * 标签表
 * </p>
 *
 * @author chenhonnian
 * @since 2020-03-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tag")
@ApiModel(value = "Tag对象", description = "标签表")
public class Tag extends Model<Tag> {

    private static final long serialVersionUID = 1L;

    @NotNull(groups = Update.class, message = "主键id不能为空")
    @Null(groups = Save.class)
    @ApiModelProperty(value = "主键自增")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @NotBlank(message = "标签名称不能为空")
    @ApiModelProperty(value = "标签名称")
    private String title;

    @ApiModelProperty(value = "所属项目")
    private String project;

    @NotBlank(message = "标签分类不能为空")
    @ApiModelProperty(value = "标签分类")
    private String type;


    public static final String ID = "id";

    public static final String TITLE = "title";

    public static final String PROJECT = "project";

    public static final String TYPE = "type";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
