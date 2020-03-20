package priv.ymqm.housing.domain.vo.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenhonnian
 * @since 2020/03/19
 */
@Getter
@Setter
public class TagReq extends PageVO {
    private String tagName;
    private String tagType;
}
