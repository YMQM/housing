package priv.ymqm.housing.common.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import priv.ymqm.housing.common.enums.RestApiResCodeEnum;
import priv.ymqm.housing.common.exception.HousingException;
import priv.ymqm.housing.common.exception.ParamException;
import priv.ymqm.housing.domain.vo.res.R;

/**
 * 全局请求异常拦截处理
 *
 * @author chenhonnian
 * @since 2020/03/19
 */
@ControllerAdvice
public class GlobalControllerExceptionAdvice {

    @ExceptionHandler(ParamException.class)
    @ResponseBody
    public R<Object> handleParamException(ParamException ex) {
        return R.error(RestApiResCodeEnum.PARAM_ERROR);
    }

    @ExceptionHandler(HousingException.class)
    @ResponseBody
    public R<Object> handleUserException(HousingException ex) {
        return R.error().code(ex.getCode()).exMsg(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R<Object> handlerUnknownException(Exception ex) {
        // todo log error info
        return R.error().exMsg(ex.toString());
    }
}
