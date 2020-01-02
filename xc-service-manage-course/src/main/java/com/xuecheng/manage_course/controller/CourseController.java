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

    @Autowired
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

    /**
     * Description: 分页查询课程列表
     *
     * @param page              :
     * @param size              :
     * @param courseListRequest :
     * @return : com.xuecheng.framework.model.response.QueryResponseResult
     * @author yindb
     * @date 2019/12/31
     */
    @Override
    @GetMapping("/coursebase/list/{page}/{size}")
    public QueryResponseResult findCourseList(@PathVariable("page") int page, @PathVariable("size") int size,
                                              CourseListRequest courseListRequest)
    {
        return courseService.findCourseList(page, size, courseListRequest);
    }

    /**
     * Description: 新增课程基础信息
     * @author yindb
     * @date 2019/12/31
     * @param courseBase :
     * @return : com.xuecheng.framework.domain.course.response.AddCourseResult
     */
    @PostMapping("/coursebase/add")
    @Override
    public AddCourseResult addCourseBase(@RequestBody CourseBase courseBase)
    {
        return courseService.addCourseBase(courseBase);
    }

    /**
     * 添加课程图片
     * @param courseId
     * @param pic
     * @return
     */
    @Override
    @PostMapping("/coursepic/add")
    public ResponseResult addCoursePic(@RequestParam("courseId") String courseId, @RequestParam("pic") String pic) {
        return courseService.saveCoursePic(courseId,pic);
    }

    /**
     * 查询课程图片
     * @param courseId
     * @return
     */
    @Override
    @GetMapping("/coursepic/list/{courseId}")
    public CoursePic findCoursePic(@PathVariable("courseId") String courseId) {
        return courseService.findCoursePic(courseId);
    }
}
