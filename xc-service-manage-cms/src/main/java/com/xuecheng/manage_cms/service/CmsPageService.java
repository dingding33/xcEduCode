package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        Pageable pageable = PageRequest.of(page, size);
        Page<CmsPage> cmsPages = cmsPageRepository.findAll(pageable);
        QueryResult<CmsPage> result = new QueryResult<>();
        result.setList(cmsPages.getContent());
        result.setTotal(cmsPages.getTotalElements());
        return new QueryResponseResult(CommonCode.SUCCESS, result);
    }
}
