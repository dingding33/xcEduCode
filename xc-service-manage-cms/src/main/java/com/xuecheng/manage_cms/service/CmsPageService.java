package com.xuecheng.manage_cms.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsTemplate;
import com.xuecheng.framework.domain.cms.request.QueryPageRequest;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.domain.cms.response.CmsPageResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.manage_cms.dao.CmsPageRepository;
import com.xuecheng.manage_cms.dao.CmsTemplateRepository;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * Description:
 * @author yindb
 * @date 2019/10/22
 */
@Service
public class CmsPageService
{
    private CmsPageRepository cmsPageRepository;

    private RestTemplate restTemplate;

    private CmsTemplateRepository cmsTemplateRepository;

    private GridFsTemplate gridFsTemplate;

    private GridFSBucket gridFSBucket;

    @Autowired
    public CmsPageService(CmsPageRepository cmsPageRepository, RestTemplate restTemplate, CmsTemplateRepository cmsTemplateRepository, GridFsTemplate gridFsTemplate)
    {
        this.cmsPageRepository = cmsPageRepository;
        this.restTemplate = restTemplate;
        this.cmsTemplateRepository = cmsTemplateRepository;
        this.gridFsTemplate = gridFsTemplate;
    }

    public QueryResponseResult findList(int page, int size, QueryPageRequest queryPageRequest)
    {
        // 分页
        Pageable pageable = PageRequest.of(page, size);

        // 条件匹配
        // 页面名称模糊查询
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("pageAlias", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("pageName", ExampleMatcher.GenericPropertyMatchers.contains());

        CmsPage cmsPage = new CmsPage();
        if (StringUtils.isNotEmpty(queryPageRequest.getSiteId())) {
            cmsPage.setSiteId(queryPageRequest.getSiteId());
        }
        if (StringUtils.isNotEmpty(queryPageRequest.getPageAlias())) {
            cmsPage.setPageAliase(queryPageRequest.getPageAlias());
        }
        if (StringUtils.isNotEmpty(queryPageRequest.getPageName())) {
            cmsPage.setPageName(queryPageRequest.getPageName());
        }
        if (StringUtils.isNotEmpty(queryPageRequest.getPageType())) {
            cmsPage.setPageType(queryPageRequest.getPageType());
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
            cmsPage.setPageId(null);
            cmsPageRepository.save(cmsPage);
            return new CmsPageResult(CommonCode.SUCCESS, cmsPage);
        }else {
            ExceptionCast.cast(CmsCode.CMS_ADDPAGE_EXISTSNAME);
        }
        return new CmsPageResult(CommonCode.FAIL, null);
    }


    /**
     * 根据ID查询页面
     * @param id
     * @return
     */
    public CmsPage findById(String id) {
        Optional<CmsPage> byId = cmsPageRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        return null;
    }

    /**
     * 修改页面
     * @param id
     * @param cmsPage
     * @return
     */
    public CmsPageResult update(String id, CmsPage cmsPage) {
        CmsPage byId = this.findById(id);
        if (byId != null) {
            byId.setTemplateId(cmsPage.getTemplateId());
            byId.setSiteId(cmsPage.getSiteId());
            byId.setPageAliase(cmsPage.getPageAliase());
            byId.setPageName(cmsPage.getPageName());
            byId.setPageWebPath(cmsPage.getPageWebPath());
            byId.setPagePhysicalPath(cmsPage.getPagePhysicalPath());

            CmsPage save = cmsPageRepository.save(byId);
            if (save != null) {
                return new CmsPageResult(CommonCode.SUCCESS, save);
            }
        }
        return new CmsPageResult(CommonCode.SUCCESS,null);
    }

    /**
     * 根据ID删除页面
     * @param id
     * @return
     */
	public CmsPageResult delete(String id) {
        CmsPage cmsPage = this.findById(id);
        if(cmsPage != null) {
            cmsPageRepository.deleteById(id);
            return new CmsPageResult(CommonCode.SUCCESS, null);
        }
		return new CmsPageResult(CommonCode.SUCCESS, null);
	}

    /**
     * 获取页面模型数据
     * @param id
     * @return
     */
	public Map getModelByPageId(String id) {
        CmsPage cmsPage = this.findById(id);
        // 页面是否存在
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        // 取出dataUrl
        String dataUrl = cmsPage.getDataUrl();
        if (StringUtils.isEmpty(dataUrl)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAURLISNULL);
        }
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(dataUrl, Map.class);
        return forEntity.getBody();

    }


    /**
     * 获取页面模板
     * @param id
     * @return
     */
    public String getTemplateByPageId(String id) {
        CmsPage cmsPage = this.findById(id);
        if (cmsPage == null) {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        String templateId = cmsPage.getTemplateId();
        if (StringUtils.isBlank(templateId)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        // 查找模板
        Optional<CmsTemplate> optional = cmsTemplateRepository.findById(templateId);
        if (optional.isPresent()) {
            CmsTemplate cmsTemplate = optional.get();
            String templateFileId = cmsTemplate.getTemplateFileId();
            // 取出模板文件
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(templateFileId)));
            // 打开下载流对象
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            // 创建 GridFsResource
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
            try {
                // 读取文件对象为string返回
                return IOUtils.toString(gridFsResource.getInputStream(), "utf-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    /**
     * 静态化
     * @param id
     * @return
     */
    public String createPageHtml(String id) {

        // 获取页面模型数据
        Map model = this.getModelByPageId(id);
        if (model == null) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_DATAISNULL);
        }
        String template = this.getTemplateByPageId(id);
        if (StringUtils.isBlank(template)) {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_TEMPLATEISNULL);
        }
        String html = this.generateHtml(template, model);


        return null;
    }

    /**
     * 页面静态化
     * @param template
     * @param model
     * @return
     */
    private String generateHtml(String template, Map model) {

        try {
            // 生成配置类
            Configuration configuration = new Configuration(Configuration.getVersion());
            // 模板加载器
            StringTemplateLoader templateLoader = new StringTemplateLoader();
            templateLoader.putTemplate("template",template);
            // 配置模板加载器
            configuration.setTemplateLoader(templateLoader);
            // 获取模板
            Template template1 = configuration.getTemplate("template");
            return FreeMarkerTemplateUtils.processTemplateIntoString(template1, model);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }


}
