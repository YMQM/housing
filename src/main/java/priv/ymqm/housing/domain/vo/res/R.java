package priv.ymqm.housing.domain.vo.res;

import priv.ymqm.housing.common.enums.RestApiResCodeEnum;

import java.lang.reflect.Field;
import java.util.Collections;

/**
 * 通用HTTP API请求返回结果封装
 *
 * @author chenhonnian
 * @since 2020/03/19
 */

public class R<T> {
    private Integer code;

    private String userMsg;

    private String exMsg;

    private T data;

    private R() {
    }

    public static <T> R<T> ok(T data) {
        R<T> res = ok();
        return res.data(data);
    }

    public static <T> R<T> ok(String userMsg) {
        R<T> res = ok();
        res.userMsg(userMsg);
        return res;
    }

    public static <T> R<T> ok() {
        R<T> res = new R<>();
        res.code(RestApiResCodeEnum.SUCCESS.getCode());
        res.userMsg(RestApiResCodeEnum.SUCCESS.getMessage());
        modifyDataField(res, Collections.emptyMap());
        return res;
    }

    public static <T> R<T> error() {
        R<T> res = new R<>();
        res.code(RestApiResCodeEnum.SYSTEM_ERROR.getCode());
        res.userMsg(RestApiResCodeEnum.SYSTEM_ERROR.getMessage());
        modifyDataField(res, Collections.emptyMap());
        return res;
    }

    public static <T> R<T> error(String userMsg) {
        R<T> res = error();
        res.userMsg(userMsg);
        return res;
    }

    public static <T> R<T> error(RestApiResCodeEnum restApiResCodeEnum) {
        R<T> res = new R<>();
        res.code(restApiResCodeEnum.getCode());
        res.userMsg(restApiResCodeEnum.getMessage());
        return res;
    }

    public R<T> code(Integer code) {
        this.code = code;
        return this;
    }

    public R<T> userMsg(String userMsg) {
        this.userMsg = userMsg;
        return this;
    }

    public R<T> exMsg(String exMsg) {
        this.exMsg = exMsg;
        return this;
    }

    public R<T> data(T data) {
        if (data == null) {
            modifyDataField(this, Collections.emptyMap());
        } else {
            this.data = data;
        }
        return this;
    }

    private static void modifyDataField(R resInstance, Object data) {
        Class cls = R.class;
        Field dataField;
        try {
            dataField = cls.getDeclaredField("data");
        } catch (NoSuchFieldException neverHappen) {
            return;
        }
        dataField.setAccessible(true);
        try {
            dataField.set(resInstance, data);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(String userMsg) {
        this.userMsg = userMsg;
    }

    public String getExMsg() {
        return exMsg;
    }

    public void setExMsg(String exMsg) {
        this.exMsg = exMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
