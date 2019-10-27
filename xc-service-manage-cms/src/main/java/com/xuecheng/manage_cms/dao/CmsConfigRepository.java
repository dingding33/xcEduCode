package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author yindb
 * @date 2019/10/22
 */
public interface CmsConfigRepository extends MongoRepository<CmsConfig,String>
{


}
