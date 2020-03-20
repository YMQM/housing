package priv.ymqm.housing.domain.vo.req;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author chenhonnian
 * @since 2020/03/19
 */
@Data
public class PageVO {

    @Min(1)
    @Max(500)
    @NotNull
    private Integer size;

    @Min(1)
    @NotNull
    private Integer page;
}
