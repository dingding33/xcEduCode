package com.xuecheng.manage_cms.web.controller;

import com.xuecheng.api.cms.SysDicthinaryControllerApi;
import com.xuecheng.framework.domain.system.SysDictionary;
import com.xuecheng.manage_cms.service.SysDictionaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/dictionary")
public class SysDictionaryController implements SysDicthinaryControllerApi
{
    @Autowired
    SysDictionaryService sysDictionaryService;
    /**
     * Description: 根据字典分类查询字典信息
     * @author yindb
     * @date 2019/12/30
     * @param type :
     * @return : com.xuecheng.framework.domain.system.SysDictionary
     */
    @Override
    @RequestMapping("/get/{type}")
    public SysDictionary getByType(@PathVariable("type") String type)
    {
        return sysDictionaryService.findDictionaryByType(type);
    }
}
