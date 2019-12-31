package com.xuecheng.manage_course.dao;

import com.github.pagehelper.Page;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by Administrator.
 */
@Mapper
public interface CourseMapper {
   /**
    * Description: 根据课程id查询课程信息
    * @author yindb
    * @date 2019/12/31
    * @param id :
    * @return : com.xuecheng.framework.domain.course.CourseBase
    */
   CourseBase findCourseBaseById(String id);

   /**
    * Description: 分页查询课程
    * @author yindb
    * @date 2019/12/31
    * @param courseListRequest :
    * @return : com.github.pagehelper.Page<com.xuecheng.framework.domain.course.ext.CourseInfo>
    */
   Page<CourseInfo> findCourseListPage(CourseListRequest courseListRequest);
}
