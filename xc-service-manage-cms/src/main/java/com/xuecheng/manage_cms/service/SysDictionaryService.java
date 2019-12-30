package com.xuecheng.manage_cms.service;

import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.dao.SysDictionaryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysDictionaryService
{
    @Autowired
    SysDictionaryDao sysDictionaryDao;

    /**
     * Description: 根据字典分类type查询字典信息
     * @author yindb
     * @date 2019/12/30
     * @param type :
     * @return : com.xuecheng.framework.domain.system.SysDictionary
     */
    public SysDictionary findDictionaryByType(String type)
    {
        return sysDictionaryDao.findByDType(type);
    }

}
