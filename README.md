# StandardProject
package com.sf.community.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/***
 * 运营位表
 */
@Data
@Entity
@Table(name = "tb_operate")
@org.hibernate.annotations.Table(appliesTo = "tb_operate", comment = "运营位")
public class Operate implements Serializable {

    @Id
    @Column(name = "uuid")
    private String uuid;

    @Column(name = "type", columnDefinition = "int(2) COMMENT '运营位类型，0启动广告1轮播图'")
    private Integer type;

    @Column(name = "position", columnDefinition = "int(2) COMMENT '投放位置，0APP启动广告 1APP首页轮播图 2任务中心轮播图'")
    private Integer position;

    @Column(name = "specific_position", columnDefinition = "int(2) COMMENT 'bananer投放具体位置，从1开始 1代表第一张轮播图，以此类推，0代表是启动广告'")
    private Integer specificPosition;

    @Column(name = "title", nullable = false, columnDefinition = "varchar(255) COMMENT '活动名称'")
    private String title;

    @Column(name = "intro", columnDefinition = "varchar(255) COMMENT '活动描述'")
    private String intro;

    @Column(name = "logo", columnDefinition = "varchar(255) COMMENT '活动图片'")
    private String logo;

    @Column(name = "activity_type", columnDefinition = "varchar(255) COMMENT '活动类型，0是图片类型 1是内部url类型 2外部url类型'")
    private Integer activityType;

    @Column(name = "url", columnDefinition = "varchar(255) COMMENT '和activity_type相对应，对应的URL链接'")
    private Integer url;

    @Column(name = "activity_id", columnDefinition = "varchar(255) COMMENT '活动id 和activity_type=1相对应，对应的uuid'")
    private Integer activityId;

    @Column(name = "duration", columnDefinition = "bigint COMMENT '广告或者是轮播间隔时长 单位是毫秒'")
    private Long duration;

    @Column(name = "is_delete", columnDefinition = "varchar(255) COMMENT '是否删除（看是需要直接物理删除） 0未删除 1已删除'")
    private Integer isDelete;

    @Column(name = "status", columnDefinition = "varchar(255) COMMENT '状态，0未使用1已使用'")
    private Integer status;

    @Column(name = "start_time", columnDefinition = "datetime COMMENT '活动开始时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name = "end_time", columnDefinition = "datetime COMMENT '活动结束时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Column(name = "publish_time", columnDefinition = "datetime COMMENT '发布时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date publishTime;

    @Column(name = "create_time", columnDefinition = "datetime COMMENT '创建时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @Column(name = "update_time", columnDefinition = "datetime COMMENT '更新时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

}
