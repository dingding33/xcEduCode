package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.TeachplanMedia;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.manage_course.dao.TeachplanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseService
{

    @Autowired
    TeachplanMapper teachplanMapper;

    /**
     * Description: 课程计划查询
     * @author yindb
     * @date 2019/10/29
     * @param courseId :
     * @return : com.xuecheng.framework.domain.course.ext.TeachplanNode
     */
    public TeachplanNode findTeachplanList(String courseId)
    {
        return null;
    }
}
