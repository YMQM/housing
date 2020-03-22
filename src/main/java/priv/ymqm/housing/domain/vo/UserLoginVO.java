package priv.ymqm.housing.domain.vo;

import lombok.Data;

/**
 * @author chenhonnian
 * @since 2020/03/21
 */
@Data
public class UserLoginVO {
    private Integer userId;
    private String userName;
    private String avatarUrl;
    private String token;
}
