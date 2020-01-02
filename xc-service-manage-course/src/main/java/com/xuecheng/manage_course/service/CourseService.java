package com.xuecheng.manage_course.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.CoursePic;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.CourseInfo;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.domain.course.request.CourseListRequest;
import com.xuecheng.framework.domain.course.response.AddCourseResult;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.QueryResponseResult;
import com.xuecheng.framework.model.response.QueryResult;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService
{
    private TeachplanRepository teachplanRepository;

    private TeachplanMapper teachplanMapper;

    private CourseBaseRepository courseBaseRepository;

    private CourseMapper courseMapper;

    private CoursePicRepository coursePicRepository;


    @Autowired
    public CourseService(TeachplanRepository teachplanRepository, TeachplanMapper teachplanMapper, CourseBaseRepository courseBaseRepository, CourseMapper
            courseMapper, CoursePicRepository coursePicRepository)
    {
        this.teachplanRepository = teachplanRepository;
        this.teachplanMapper = teachplanMapper;
        this.courseBaseRepository = courseBaseRepository;
        this.courseMapper = courseMapper;
        this.coursePicRepository = coursePicRepository;
    }


    /**
     * Description: 课程计划查询
     * @author yindb
     * @date 2019/10/29
     * @param courseId :
     * @return : com.xuecheng.framework.domain.course.ext.TeachplanNode
     */
    public TeachplanNode findTeachplanList(String courseId)
    {
        return teachplanMapper.selectList(courseId);
    }

    /**
     * Description: 新增课程计划
     * @author yindb
     * @date 2019/10/30
     * @param teachplan :
     * @return : com.xuecheng.framework.model.response.ResponseResult
     */
    public ResponseResult addTeachplan(Teachplan teachplan)
    {
        // 校验课程id和课程计划名称是否为空
        if (teachplan == null || StringUtils.isBlank(teachplan.getCourseid()) || StringUtils.isBlank(teachplan.getPname()))
        {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        // 课程ID
        String courseid = teachplan.getCourseid();
        // 父节点ID
        // 如果父节点为空则获取根节点
        String parentid = teachplan.getParentid();
        if (StringUtils.isBlank(parentid))
        {
            parentid = getTeachplanRoot(courseid);
        }
        if (StringUtils.isBlank(parentid))
        {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        // 查询父节点信息
        Optional<Teachplan> optional = teachplanRepository.findById(parentid);
        if (!optional.isPresent())
        {
            ExceptionCast.cast(CommonCode.INVALID_PARAM);
        }
        // 父节点
        Teachplan teachplanParent = optional.get();
        String parentGrade = teachplanParent.getGrade();
        // 设置父节点
        teachplan.setParentid(parentid);
        teachplan.setStatus("0");
        // 设置子节点级别
        if ("1".equals(parentGrade))
        {
            teachplan.setGrade("2");
        } else if ("2".equals(parentGrade))
        {
            teachplan.setGrade("3");
        }
        // 设置课程id
        teachplan.setCourseid(teachplanParent.getCourseid());
        teachplanRepository.save(teachplan);

        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * Description: 获取根节点ID
     * @author yindb
     * @date 2019/10/30
     * @param courseid :
     * @return : java.lang.String
     */
    private String getTeachplanRoot(String courseid)
    {
        // 校验课程ID
        Optional<CourseBase> optional = courseBaseRepository.findById(courseid);
        // 查询不到则无该课程
        if (!optional.isPresent())
        {
            return null;
        }
        CourseBase courseBase = optional.get();
        // 课程计划根节点
        List<Teachplan> teachplanList =  teachplanRepository.findByCourseidAndParentid(courseid, "0");
        // 没有课程计划根节点则新增一个根节点
        if (teachplanList == null || teachplanList.size() == 0){
            Teachplan root = new Teachplan();
            root.setPname(courseBase.getName());
            root.setParentid("0");
            root.setGrade("1");
            root.setCourseid(courseid);
            root.setStatus("0");
            teachplanRepository.save(root);
            return root.getId();
        }
        // 已有课程计划则取已有根节点
        return teachplanList.get(0).getId();
    }

    /**
     * 添加课程图片
     * @param courseId
     * @param pic
     * @return
     */
    @Transactional
    public ResponseResult saveCoursePic(String courseId, String pic) {
        // 查询课程图片
        Optional<CoursePic> optional = coursePicRepository.findById(courseId);

        CoursePic coursePic = null;

        if (optional.isPresent()) {
            coursePic = optional.get();
        }

        if (coursePic == null) {
            coursePic = new CoursePic();
        }

        coursePic.setCourseid(courseId);
        coursePic.setPic(pic);

        // 保存课程图片
        coursePicRepository.save(coursePic);
        return new ResponseResult(CommonCode.SUCCESS);


    }

    /**
     * 查询课程图片
     * @param courseId
     * @return
     */
    public CoursePic findCoursePic(String courseId) {
        Optional<CoursePic> picOptional = coursePicRepository.findById(courseId);
        return picOptional.isPresent() ? null : picOptional.get();
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
    public QueryResponseResult findCourseList(int page, int size, CourseListRequest courseListRequest)
    {
        if (courseListRequest == null)
        {
            courseListRequest = new CourseListRequest();
        }
        if (page <= 0)
        {
            page = 0;
        }
        if (size <= 0)
        {
            size = 20;
        }

        // 设置分页参数
        PageHelper.startPage(page, size);
        // 分页查询
        Page<CourseInfo> courseListPage = courseMapper.findCourseListPage(courseListRequest);
        // 查询列表
        List<CourseInfo> list = courseListPage.getResult();
        // 总记录数
        long total = courseListPage.getTotal();
        // 查询结果集
        QueryResult<CourseInfo> courseInfoQueryResult = new QueryResult<>();
        courseInfoQueryResult.setList(list);
        courseInfoQueryResult.setTotal(total);
        return new QueryResponseResult(CommonCode.SUCCESS, courseInfoQueryResult);
    }

    /**
     * Description: 添加课程基础信息
     * @author yindb
     * @date 2019/12/31
     * @param courseBase :
     * @return : com.xuecheng.framework.domain.course.response.AddCourseResult
     */
    @Transactional
    public AddCourseResult addCourseBase(CourseBase courseBase)
    {
        // 课程状态默认为未发布
        courseBase.setStatus("202001");
        CourseBase save = courseBaseRepository.save(courseBase);
        return new AddCourseResult(CommonCode.SUCCESS,courseBase.getId());
    }
}
