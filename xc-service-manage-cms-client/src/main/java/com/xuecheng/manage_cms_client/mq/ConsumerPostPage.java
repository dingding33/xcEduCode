package com.xuecheng.manage_cms_client.mq;

import com.alibaba.fastjson.JSON;
import com.xuecheng.framework.domain.cms.CmsPage;
import com.xuecheng.framework.domain.cms.response.CmsCode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.manage_cms_client.dao.CmsPageRepository;
import com.xuecheng.manage_cms_client.service.PageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

/**
 * Description: mq消费端，监听页面发布队列的消息
 * @author yindb
 * @date 2019/10/29
 */
@Component
public class ConsumerPostPage
{
    private static final Logger logger = LoggerFactory.getLogger(ConsumerPostPage.class);

    private CmsPageRepository cmsPageRepository;

    private PageService pageService;

    public ConsumerPostPage(CmsPageRepository cmsPageRepository, PageService pageService)
    {
        this.cmsPageRepository = cmsPageRepository;
        this.pageService = pageService;
    }

    /**
     * Description: 监听队列消息，处理
     * @author yindb
     * @date 2019/10/29
     */
    @RabbitListener(queues = {"${xuecheng.mq.queue}"})
    public void postPage(String msg)
    {
        // 解析消息
        Map map = JSON.parseObject(msg, Map.class);

        logger.info("receive cms post page:{}",msg);

        // 取页面ID
        String pageId = (String) map.get("pageId");

        // 查询页面信息
        Optional<CmsPage> optionalCmsPage = cmsPageRepository.findById(pageId);

        if (!optionalCmsPage.isPresent())
        {
            logger.error("receive cms post page,cmsPage is null:{}",msg);
            ExceptionCast.cast(CmsCode.CMS_PAGE_NOTEXISTS);
            return;
        }

        // 将页面保存到服务器物理路径
        pageService.savePageToServerPath(pageId);

    }



}
