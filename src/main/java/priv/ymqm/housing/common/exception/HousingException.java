package priv.ymqm.housing.common.exception;

import lombok.Getter;
import priv.ymqm.housing.common.enums.RestApiResCodeEnum;

/**
 * 自定义业务异常
 *
 * @author chenhonnian
 * @since 2020/03/19
 */
@Getter
public class HousingException extends RuntimeException {
    private final Integer code;

    public HousingException(String message) {
        super(message);
        this.code = RestApiResCodeEnum.SYSTEM_ERROR.getCode();
    }

    public HousingException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public HousingException(RestApiResCodeEnum restApiResCodeEnum) {
        super(restApiResCodeEnum.getMessage());
        this.code = restApiResCodeEnum.getCode();
    }
}
