package com.xuecheng.manage_cms.dao;

import com.xuecheng.framework.domain.system.SysDictionary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SysDictionaryDao extends MongoRepository<SysDictionary, String>
{

    /**
     * Description: 根据字段分类查询字典信息
     * @author yindb
     * @date 2019/12/30
     * @param dType :
     * @return : com.xuecheng.framework.domain.system.SysDictionary
     */
    SysDictionary findByDType(String dType);

}
