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
    private  String exMsg;

    public HousingException(String userMsg) {
        super(userMsg);
        this.code = RestApiResCodeEnum.SYSTEM_ERROR.getCode();
    }

    public HousingException(String userMsg, String exMsg) {
        super(userMsg);
        this.code = RestApiResCodeEnum.SYSTEM_ERROR.getCode();
        this.exMsg = exMsg;
    }

    public HousingException(Integer code, String userMsg) {
        super(userMsg);
        this.code = code;
    }

    public HousingException(Integer code, String userMsg, String exMsg) {
        super(userMsg);
        this.code = code;
        this.exMsg = exMsg;
    }

    public HousingException(RestApiResCodeEnum restApiResCodeEnum) {
        super(restApiResCodeEnum.getMessage());
        this.code = restApiResCodeEnum.getCode();
    }

    public HousingException(RestApiResCodeEnum restApiResCodeEnum, String exMsg) {
        super(restApiResCodeEnum.getMessage());
        this.code = restApiResCodeEnum.getCode();
        this.exMsg = exMsg;
    }
}
