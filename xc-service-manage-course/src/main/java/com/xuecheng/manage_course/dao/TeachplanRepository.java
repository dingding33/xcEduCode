package com.xuecheng.manage_course.dao;

import com.xuecheng.framework.domain.course.Teachplan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Administrator.
 */
public interface TeachplanRepository extends JpaRepository<Teachplan, String>
{

    /**
     * Description: 根据课程id和父节点id查询结点列表
     * @author yindb
     * @date 2019/10/30
     * @return : java.util.List<com.xuecheng.framework.domain.course.Teachplan>
     */
    List<Teachplan> findByCourseidAndParentid(String courseid, String parentid);
}
