package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Description: 分页查询页面
 * @author yindb
 * @date 2019/10/22
 */
@RestController
@RequestMapping("/cms/page")
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
        if (queryPageRequest == null)
        {
            queryPageRequest = new QueryPageRequest();
        }
        if (page <= 0)
        {
            page = 1;
        }
        // 适用 mongodb
        page = page - 1;
        if (size <= 0)
        {
            size = 20;
        }
        return cmsPageService.findList(page, size, queryPageRequest);
    }

    /**
     * 添加页面
     */
    @Override
    @PostMapping("/add")
    public CmsPageResult add(@RequestBody CmsPage cmsPage) {
        return cmsPageService.add(cmsPage);
    }

    /**
     * 通过ID查询页面
     */
    @Override
    @GetMapping("/get/{id}")
    public CmsPage findById(@PathVariable("id") String id) {
        return cmsPageService.findById(id);
    }

    /**
     * 修改页面
     * @param id
     * @param cmsPage
     * @return
     */
    @Override
    @PutMapping("/edit/{id}")
    public CmsPageResult edit(@PathVariable("id") String id, @RequestBody CmsPage cmsPage) {
        return cmsPageService.update(id, cmsPage);
    }

    /**
     * 删除页面
     */
    @Override
    @DeleteMapping("/del/{id}")
    public CmsPageResult delete(@PathVariable("id")String id) {
        return cmsPageService.delete(id);
    }

    /**
     * 静态化
     * @param id
     * @return
     */
    @Override
    @GetMapping("/html/{id}")
    public void staticHtml(@PathVariable("id") String id) {
        cmsPageService.createPageHtml(id);
    }

    /**
     * 页面预览
     * @param id
     */
    @Override
    public void preview(String id) {

    }
}
