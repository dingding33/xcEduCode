package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.api.cms.CmsPageControllerApi;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.manage_cms.service.CmsPageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

/**
 * Description: 课程管理
 * @author yindb
 * @date 2019/10/22
 */
@RestController
@RequestMapping("/cms/page")
public class CmsPageController extends BaseController implements CmsPageControllerApi
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
     * 页面预览
     * @param id
     */
    @Override
    @GetMapping("/preview/{id}")
    public void preview(@PathVariable("id") String id) {

        String pageHtml = cmsPageService.createPageHtml(id);
        if (StringUtils.isNotEmpty(pageHtml)) {
            try {
                ServletOutputStream outputStream = response.getOutputStream();
                outputStream.write(pageHtml.getBytes("utf-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Description: 发布页面
     * @author yindb
     * @date 2019/10/29
     * @param id :
     * @return : com.xuecheng.framework.model.response.ResponseResult
     */
    @PostMapping("/postPage/{pageId}")
    @Override
    public ResponseResult post(@PathVariable("pageId") String id)
    {
        return cmsPageService.postPage(id);
    }
}
