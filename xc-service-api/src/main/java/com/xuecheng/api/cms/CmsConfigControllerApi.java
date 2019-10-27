package com.xuecheng.api.cms;

import com.xuecheng.framework.domain.cms.CmsConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Description: Cms配置管理接口
 * @author yindb
 * @date 2019/10/22
 */
@Api(value = "cms配置管理接口",description = "提供数据模型管理，查询接口")
public interface CmsConfigControllerApi
{

    @ApiOperation("通过ID查询页面")
    CmsConfig getModel(String id);

}
