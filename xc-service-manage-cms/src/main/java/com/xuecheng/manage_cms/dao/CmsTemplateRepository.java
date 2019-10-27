package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author yindb
 * @date 2019/10/22
 */
public interface CmsTemplateRepository extends MongoRepository<CmsTemplate,String>
{


}
