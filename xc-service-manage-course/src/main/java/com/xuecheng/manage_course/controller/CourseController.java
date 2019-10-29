package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.manage_course.service.CourseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: 课程管理
 * @author yindb
 * @date 2019/10/29
 */
@RestController
@RequestMapping("/course")
public class CourseController implements CourseControllerApi
{

    CourseService courseService;
    /**
     * Description: 课程计划查询
     * @author yindb
     * @date 2019/10/29
     * @param courseId : 课程ID
     * @return : com.xuecheng.framework.domain.course.ext.TeachplanNode
     */
    @Override
    @GetMapping("/teachplan/list/{courseId}")
    public TeachplanNode findTeachplanList(@PathVariable("courseId") String courseId)
    {
        return courseService.findTeachplanList(courseId);
    }
}
