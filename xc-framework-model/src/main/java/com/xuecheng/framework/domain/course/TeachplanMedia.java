package com.xuecheng.framework.domain.course;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by admin on 2018/2/7.
 */
@Data
@ToString
@Entity
@Table(name="teachplan_media")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
public class TeachplanMedia implements Serializable {
    private static final long serialVersionUID = -916357110051689485L;
    /** 课程计划ID */
    @Id
    @GeneratedValue(generator = "jpa-assigned")
    @Column(name="teachplan_id")
    private String teachplanId;
    /** 媒体资源文件ID */
    @Column(name="media_id")
    private String mediaId;
    /** 媒资文件原始名称 */
    @Column(name="media_fileoriginalname")
    private String mediaFileOriginalName;
    /** 媒资文件方位地址 */
    @Column(name="media_url")
    private String mediaUrl;
    /** 课程id */
    private String courseId;

}
