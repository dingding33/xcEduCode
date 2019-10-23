package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

/**
 * Description:
 * @author yindb
 * @date 2019/10/22
 */
@Service
public class CmsPageService
{
    private CmsPageRepository cmsPageRepository;

    @Autowired
    public CmsPageService(CmsPageRepository cmsPageRepository)
    {
        this.cmsPageRepository = cmsPageRepository;
    }

    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest)
    {
        // 分页
        Pageable pageable = PageRequest.of(page, size);

        // 条件匹配
        // 页面名称模糊查询
        ExampleMatcher exampleMatcher = ExampleMatcher.matching().withMatcher("pageAliase", ExampleMatcher.GenericPropertyMatchers.contains());

        CmsPage cmsPage = new CmsPage();
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAliase())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAliase());
        }

        Example<CmsPage> example = Example.of(cmsPage, exampleMatcher);

        Page<CmsPage> cmsPages = cmsPageRepository.findAll(example, pageable);
        QueryResult<CmsPage> result = new QueryResult<>();
        result.setList(cmsPages.getContent());
        result.setTotal(cmsPages.getTotalElements());
        return new QueryResponseResult(CommonCode.SUCCESS, result);
    }


    /**
     * 添加页面
     * @param cmsPage
     * @return
     */
    public CmsPageResult add(CmsPage cmsPage) {

        // 校验页面是否存在
        CmsPage page = cmsPageRepository.findByPageNameAndSiteIdAndPageWebPath(cmsPage.getPageName(), cmsPage.getSiteId(), cmsPage.getPageWebPath());

        if (page == null) {
            cmsPage.setSiteId(null);
            cmsPageRepository.save(cmsPage);
            return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        }
        return new CmsPageResult(CommonCode.FAIL, null);


    }
}
