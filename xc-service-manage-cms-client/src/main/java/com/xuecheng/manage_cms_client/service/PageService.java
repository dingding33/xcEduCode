package com.xuecheng.manage_cms_client.service;

import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.CmsSite;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.dao.CmsSiteRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import sun.nio.ch.IOUtil;

import java.io.*;
import java.util.Optional;

/**
 * Description: 页面管理操作
 * @author yindb
 * @date 2019/10/29
 */
@Service
public class PageService
{
    private CmsPageRepository cmsPageRepository;

    private CmsSiteRepository cmsSiteRepository;

    private GridFsTemplate gridFsTemplate;

    private GridFSBucket gridFSBucket;

    public PageService(CmsPageRepository cmsPageRepository, CmsSiteRepository cmsSiteRepository, GridFsTemplate
            gridFsTemplate, GridFSBucket gridFSBucket)
    {
        this.cmsPageRepository = cmsPageRepository;
        this.cmsSiteRepository = cmsSiteRepository;
        this.gridFsTemplate = gridFsTemplate;
        this.gridFSBucket = gridFSBucket;
    }


    /**
     * Description: 将页面HTML保存到页面物理路径
     * @author yindb
     * @date 2019/10/29
     * @param pageId :
     * @return : void
     */
    public void savePageToServerPath(String pageId)
    {
        // 查询页面信息
        Optional<CmsPage> optionalCmsPage = cmsPageRepository.findById(pageId);

        if (!optionalCmsPage.isPresent())
        {
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
        }
        // 去除页面物理路径
        CmsPage cmsPage = optionalCmsPage.get();
        // 页面所属站点
        // TODOY :--此处 CmsSite 没有PhysicalPath -- 2019-10-29 11:39
        CmsSite cmsSite = this.getCmsSiteById(cmsPage.getSiteId());

        // 页面物理路径
        String pagePath = cmsPage.getPagePhysicalPath() + cmsPage.getPageName();

        // 查找页面静态文件
        String htmlFileId = cmsPage.getHtmlFileId();

        InputStream inputStream = this.getFileById(htmlFileId);
        if (inputStream == null)
        {
            ExceptionCast.cast(CmsCode.CMS_GENERATEHTML_HTMLISNULL);
        }
        FileOutputStream fileOutputStream = null;
        try
        {
            fileOutputStream = new FileOutputStream(new File(pagePath));
            // 将文件保存到服务器路径
            IOUtils.copy(inputStream, fileOutputStream);
        } catch (Exception e)
        {
            e.printStackTrace();
        }finally
        {
            try
            {
                inputStream.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            try
            {
                fileOutputStream.close();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }

    /**
     * Description: 根据文件ID获取流对象
     * @author yindb
     * @date 2019/10/29
     * @param htmlFileId :
     * @return : java.io.InputStream
     */
    private InputStream getFileById(String htmlFileId)
    {
        try
        {
            GridFSFile gridFSFile = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(htmlFileId)));
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(gridFSFile.getObjectId());
            GridFsResource gridFsResource = new GridFsResource(gridFSFile, gridFSDownloadStream);
            return gridFsResource.getInputStream();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }


    public CmsSite getCmsSiteById(String siteId)
    {
        Optional<CmsSite> optional = cmsSiteRepository.findById(siteId);
        if (optional.isPresent())
        {
            return optional.get();
        }
        return null;
    }


}
