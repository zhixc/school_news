package org.zxc.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author 知行成
 * @since 2021-07-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_news")
public class News implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 新闻id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 新闻标题
     */
    private String title;

    /**
     * 作者
     */
    private String author;

    /**
     * 内容
     */
    private String content;

    /**
     * 图片
     */
    private String pictureUrl;

    /**
     * 外键关联新闻类型表主键
     */
    private Integer typeId;

    /**
     * 外键关联用户表主键
     */
    private Integer userId;

    /**
     * 发布时间，@JsonFormat注解返回时间格式
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date issueTime;

    /**
     * 更新时间，@JsonFormat注解返回时间格式
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateTime;

    /**
     * 状态
     */
    private Integer status;


}
