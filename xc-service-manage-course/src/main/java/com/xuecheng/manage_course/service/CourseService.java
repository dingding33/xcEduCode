package com.xuecheng.manage_course.service;

import com.xuecheng.framework.domain.course.CourseBase;
import com.xuecheng.framework.domain.course.Teachplan;
import com.xuecheng.framework.domain.course.ext.TeachplanNode;
import com.xuecheng.framework.exception.ExceptionCast;
import com.xuecheng.framework.model.response.CommonCode;
import com.xuecheng.framework.model.response.ResponseResult;
import com.xuecheng.manage_course.dao.CourseBaseRepository;
import com.xuecheng.manage_course.dao.CourseMapper;
import com.xuecheng.manage_course.dao.TeachplanMapper;
import com.xuecheng.manage_course.dao.TeachplanRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService
{
    private TeachplanRepository teachplanRepository;

    private TeachplanMapper teachplanMapper;

    private CourseBaseRepository courseBaseRepository;

    private CourseMapper courseMapper;


    @Autowired
    public CourseService(TeachplanRepository teachplanRepository, TeachplanMapper teachplanMapper, CourseBaseRepository courseBaseRepository, CourseMapper
            courseMapper)
    {
        this.teachplanRepository = teachplanRepository;
        this.teachplanMapper = teachplanMapper;
        this.courseBaseRepository = courseBaseRepository;
        this.courseMapper = courseMapper;
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
        String parentid = !"".equals(teachplan.getParentid()) ? teachplan.getParentid() :getTeachplanRoot(courseid);
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
}
