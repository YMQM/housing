package priv.ymqm.housing.domain.vo.req;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chenhonnian
 * @since 2020/03/19
 */

@Getter
@Setter
public class CommunityReq extends PageVO{
    private Integer communityId;
    private String communityName;
    private Integer creatorId;

    private Integer areaType;
    private String areaCode;
}
