package com.xuecheng.manage_course.controller;

import com.xuecheng.api.course.CourseControllerApi;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.service.CourseService;
import org.springframework.web.bind.annotation.*;

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

    /**
     * Description: 添加课程计划
     * @author yindb
     * @date 2019/10/30
     * @param teachplan :
     * @return : com.xuecheng.framework.model.response.ResponseResult
     */
    @Override
    @PostMapping("/teachplan/add")
    public ResponseResult addTeachplan(@RequestBody Teachplan teachplan)
    {
        ResponseResult responseResult;
        // TODOY :--必填项校验 -- 2019-10-30 15:27

        responseResult = courseService.addTeachplan(teachplan);
        return responseResult;
    }

    /**
     * Description: 删除课程计划
     * @author yindb
     * @date 2019/10/30
     * @param courseId :
     * @return : com.xuecheng.framework.model.response.ResponseResult
     */
    @Override
    @DeleteMapping("/teachplan/del/{courseId}")
    public ResponseResult delTeachplan(@PathVariable("courseId") String courseId)
    {

        return null;
    }

    /**
     * Description: 修改课程计划
     * @author yindb
     * @date 2019/10/30
     * @param teachplan :
     * @return : com.xuecheng.framework.model.response.ResponseResult
     */
    @Override
    @PutMapping("/teachplan/edit")
    public ResponseResult editTeachplan(@RequestBody Teachplan teachplan)
    {
        return null;
    }
}
