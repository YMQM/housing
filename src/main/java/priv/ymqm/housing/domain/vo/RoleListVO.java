package priv.ymqm.housing.domain.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author chenhonnian
 * @since 2020/03/21
 */
@Data
public class RoleListVO {
    @ApiModelProperty(value = "主键自增")
    private Integer id;

    @ApiModelProperty(value = "唯一键")
    private String uniqueKey;

    @ApiModelProperty(value = "角色名称")
    private String roleName;
}
