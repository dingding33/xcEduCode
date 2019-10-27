package com.xuecheng.framework.domain.cms.request;

import com.xuecheng.framework.model.request.RequestData;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Description: 查询条件类型
 * @author yindb
 * @date 2019/10/22
 */
@Data
public class QueryPageRequest extends RequestData
{
    /** 站点ID */
    @ApiModelProperty("站点ID")
    private String siteId;
    /** 页面ID */
    @ApiModelProperty("页面ID")
    private String pageId;
    /** 页面名称 */
    @ApiModelProperty("页面名称")
    private String pageName;
    /** 别名 */
    @ApiModelProperty("别名")
    private String pageAlias;
    /** 模板ID */
    @ApiModelProperty("模板ID")
    private String templateId;
    /** 页面类型 */
    @ApiModelProperty("页面类型")
    private String pageType;
}
