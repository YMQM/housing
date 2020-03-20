package priv.ymqm.housing.common.advice;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        return R.error()
                .code(ex.getCode())
                .userMsg(ex.getMessage())
                .exMsg(ex.getExMsg());
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R<Object> handlerUnknownException(Exception ex) {
        log.error("服务器内部异常", ex);
        return R.error().exMsg(ex.toString());
    }
}
