package priv.ymqm.housing.common.exception;

import priv.ymqm.housing.common.enums.RestApiResCodeEnum;

/**
 * API传入参数异常
 *
 * @author chenhonnian
 * @since 2020/03/19
 */
public class ParamException extends HousingException {

    public ParamException(Integer code, String message) {
        super(code, message);
    }

    public ParamException(String message) {
        this(RestApiResCodeEnum.PARAM_ERROR.getCode(), message);
    }
}
