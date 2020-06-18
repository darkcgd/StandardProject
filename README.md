package com.sf.community.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/***
 * 活动表（邀请有礼和抽奖模块）
 */
@Data
@Entity
@Table(name = "tb_activity")
@org.hibernate.annotations.Table(appliesTo = "tb_activity", comment = "活动表（邀请有礼和抽奖模块）")
public class Activity implements Serializable {

    @Id
    @Column(name = "uuid", columnDefinition = "varchar(32) COMMENT '唯一uuid'")
    private String uuid;

    @Column(name = "name", nullable = false, columnDefinition = "varchar(255) COMMENT '名称'")
    private String name;

    @Column(name = "activity_type", columnDefinition = "int(2) COMMENT '活动类型，2邀请有礼 3抽奖'")
    private Integer activity_type;

    @Column(name = "activity_url", nullable = false, columnDefinition = "varchar(255) COMMENT '背景地址'")
    private String activity_url;

    @Column(name = "img_url", nullable = false, columnDefinition = "varchar(255) COMMENT '图标地址'")
    private String img_url;

    @Column(name = "start_time", columnDefinition = "datetime COMMENT '活动开始时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Column(name = "end_time", columnDefinition = "datetime COMMENT '活动结束时间'")
    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Column(name = "prize_type", columnDefinition = "int(2) COMMENT '奖品类型 0虚拟商品1实物商品 2积分'")
    private Integer prize_type;

    @Column(name = "yqyl_rule", nullable = false, columnDefinition = "varchar(255) COMMENT '邀请有礼邀请人H5页面活动规则 邀请有礼专用'")
    private String yqyl_rule;

    @Column(name = "yqyl_invite_rule", nullable = false, columnDefinition = "varchar(255) COMMENT '邀请有礼受邀人H5页面活动规则 邀请有礼专用'")
    private String yqyl_invite_rule;

    @Column(name = "new_register_prize_id", nullable = false, columnDefinition = "varchar(255) COMMENT '新用户注册奖品id 邀请有礼专用'")
    private String new_register_prize_id;

    @Column(name = "invite_prize_id", nullable = false, columnDefinition = "varchar(255) COMMENT '老用户的邀请奖品id 邀请有礼专用'")
    private String invite_prize_id;

    @Column(name = "new_register_first_order_prize_id", nullable = false, columnDefinition = "varchar(255) COMMENT '新用户首单奖品id 邀请有礼专用'")
    private String new_register_first_order_prize_id;

    @Column(name = "new_register_first_order_invite_prize_id", nullable = false, columnDefinition = "varchar(255) COMMENT '新用户首单对应的邀请人奖品id 邀请有礼专用'")
    private String new_register_first_order_invite_prize_id;

    @Column(name = "Xrzcylzccg_prize_id", nullable = false, columnDefinition = "varchar(255) COMMENT '注册有礼注册成功奖品id 注册有礼专用'")
    private String Xrzcylzccg_prize_id;

    @Column(name = "Xrzcylsd_prize_id", nullable = false, columnDefinition = "varchar(255) COMMENT '注册有礼注册成功首单奖品id 注册有礼专用'")
    private String Xrzcylsd_prize_id;

    @Column(name = "Xrzcylsdyc_prize_id", nullable = false, columnDefinition = "varchar(255) COMMENT '注册有礼注册成功首单异常奖品id 注册有礼专用'")
    private String Xrzcylsdyc_prize_id;

    @Column(name = "activity_copywriting", nullable = false, columnDefinition = "varchar(255) COMMENT '活动文案 抽奖专用'")
    private String activity_copywriting;

    @Column(name = "rule", nullable = false, columnDefinition = "varchar(255) COMMENT '规则'")
    private String rule;

    @Column(name = "is_delete", columnDefinition = "int(2)  COMMENT '是否删除（看是需要直接物理删除） 0未删除 1已删除'")
    private Integer isDelete;

    @Column(name = "status", columnDefinition = "int(2)  COMMENT '状态，0未使用1已使用'")
    private Integer status;

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
