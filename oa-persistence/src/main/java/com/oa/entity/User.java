package com.oa.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.*;

/**
 * <p>
 *  用户表
 * </p>
 *
 * @author zhengwen
 * @since 2018年10月21日13:22:41
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("tb_user")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;
    /**
     * 用户标识
     */
    @TableId
    private Integer id;
    /**
     * 小程序用户标识
     */
    private String openId;
    /**
     * 昵称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 姓名
     */
    @TableField("username")
    private String username;
    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;
    /**
     * 人脸库标识
     */
    @TableField("face_token")
    private String faceToken;
    /**
     * 性别（1：男，2：女）
     */
    @TableField("gender")
    private int gender;
    /**
     * 状态（0：禁用，1：启用）
     */
    @TableField("status")
    private int status;
    /**
     * 创建时间
     */
    @TableField("create_time")
    private Timestamp createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
