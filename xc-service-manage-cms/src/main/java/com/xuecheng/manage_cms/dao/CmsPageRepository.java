package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.cms.CmsPage;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Description: 分页查询
 * @author yindb
 * @date 2019/10/22
 */
public interface CmsPageRepository extends MongoRepository<CmsPage,String>
{

}
