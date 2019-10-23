package com.xuecheng.framework.domain.cms;

import java.util.Map;

import lombok.Data;
import lombok.ToString;

/**
 * Created by admin on 2018/2/6.
 */
@Data
@ToString
public class CmsConfigModel {
    private String key;
    private String name;
    private String url;
    @SuppressWarnings("rawtypes")
    private Map mapValue;
    private String value;

}
