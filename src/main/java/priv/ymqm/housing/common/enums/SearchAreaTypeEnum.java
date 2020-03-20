package priv.ymqm.housing.common.enums;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import priv.ymqm.housing.domain.po.Community;

/**
 * @author chenhonnian
 * @since 2020/03/19
 */
@Getter
@AllArgsConstructor
public enum SearchAreaTypeEnum implements CodeMessageEnum {
    /**
     * 行政区域级别枚举
     */
    PROVINCE_LEVEL(1, "省级", Community::getProvinceCode),
    CITY_LEVEL(2, "市级", Community::getCityCode),
    COUNTY_LEVEL(3, "县级", Community::getCountyCode);

    private Integer code;
    private String message;
    private SFunction<Community, Object> field;
}
