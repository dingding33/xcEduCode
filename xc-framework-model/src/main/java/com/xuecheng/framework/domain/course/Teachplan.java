package com.xuecheng.framework.domain.course;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Description: 课程计划
 * @author yindb
 * @date 2019/10/30
 */
@Data
@ToString
@Entity
@Table(name="teachplan")
@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Teachplan implements Serializable {
    private static final long serialVersionUID = -916357110051689485L;
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 32)
    private String id;
    /** 课程名称 */
    private String pname;
    /** 父节点ID */
    private String parentid;
    /** 计划层级 */
    private String grade;
    /** 课程类型：1视频，2文档 */
    private String ptype;
    /** 章节及课程介绍 */
    private String description;
    /** 课程id */
    private String courseid;
    /** 状态：0未发布，1已发布 */
    private String status;
    /** 排序字段 */
    private Integer orderby;
    /** 时长（分钟） */
    private Double timelength;
    /** 是否试学：0不是，1是 */
    private String trylearn;

}
