package com.xuecheng.framework.domain.course;

import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Description: 课程分类
 * @author yindb
 * @date 2019/10/30
 */
@Data
@ToString
@Entity
@Table(name="category")
@GenericGenerator(name = "jpa-assigned", strategy = "assigned")
//@GenericGenerator(name = "jpa-uuid", strategy = "uuid")
public class Category implements Serializable {
    private static final long serialVersionUID = -906357110051689484L;
    @Id
    @GeneratedValue(generator = "jpa-assigned")
    @Column(length = 32)
    private String id;
    /** 分类名称 */
    private String name;
    /** 分类标签，默认与名称一样 */
    private String label;
    /** 父节点ID */
    private String parentid;
    /** 是否显示 */
    private String isshow;
    /** 排序字段 */
    private Integer orderby;
    /** 是否是叶子 */
    private String isleaf;

}
