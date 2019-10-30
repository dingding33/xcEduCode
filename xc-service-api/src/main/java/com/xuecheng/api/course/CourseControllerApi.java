package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "课程管理接口",description = "提供课程管理功能")
public interface CourseControllerApi
{
    // @ApiOperation("课程查询")
    // QueryResponseResult<CourseInfo> findCourseList(int page, int size, CourseListRequest courseListRequest);
    // // @ApiOperation("课程修改")
    // @ApiOperation("新增课程")
    // ResponseResult addCourse(CourseBase courseBase);

    @ApiOperation("课程计划查询")
    TeachplanNode findTeachplanList(String courseId);

    @ApiOperation("添加课程计划")
    ResponseResult addTeachplan(Teachplan teachplan);

    @ApiOperation("删除课程计划")
    ResponseResult delTeachplan(String courseId);

    @ApiOperation("修改课程计划")
    ResponseResult editTeachplan(Teachplan teachplan);


}
