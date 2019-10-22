package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 分页查询页面
 * @author yindb
 * @date 2019/10/22
 */
@RestController
public class CmsPageController implements CmsPageControllerApi
{
    private CmsPageService cmsPageService;

    @Autowired
    public CmsPageController(CmsPageService cmsPageService)
    {
        this.cmsPageService = cmsPageService;
    }

    /**
     * Description: 页面列表分页查询
     * @author yindb
     * @date 2019/10/22
     * @param page :页码
     * @param size : 分页条数
     * @param queryPageRequest :条件
     * @return : com.xuecheng.framework.model.response.QueryResponseResult
     */
    @Override
    @GetMapping("/list/{page}/{size}")
    public QueryResponseResult findList(@PathVariable("page") int page, @PathVariable("size") int size, QueryPageRequest queryPageRequest)
    {
        return cmsPageService.findList(page, size, queryPageRequest);
    }
}
