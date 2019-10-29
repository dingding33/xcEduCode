package com.xuecheng.api.course;

import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "课程管理接口",description = "提供课程管理功能")
public interface CourseControllerApi
{

    @ApiOperation("课程计划查询")
    TeachplanNode findTeachplanList(String courseId);


}
