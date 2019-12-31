package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "课程管理接口",description = "提供课程管理功能")
public interface CourseControllerApi
{
    @ApiOperation("课程列表查询")
    QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest);

    @ApiOperation("新增课程基础信息")
    AddCourseResult addCourseBase(CourseBase courseBase);

    @ApiOperation("课程计划查询")
    TeachplanNode findTeachplanList(String courseId);

    @ApiOperation("添加课程计划")
    ResponseResult addTeachplan(Teachplan teachplan);

    @ApiOperation("删除课程计划")
    ResponseResult delTeachplan(String courseId);

    @ApiOperation("修改课程计划")
    ResponseResult editTeachplan(Teachplan teachplan);



}
