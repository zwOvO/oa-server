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
 * 用户表
 * </p>
 *
 * @author liugh123
 * @since 2018-06-25
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
     * 用户主键
     */
    @TableId("open_id")
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
     * 创建时间
     */
    @TableField("create_time")
    private Timestamp createTime;
    /**
     * 头像
     */
    @TableField("avatar")
    private String avatar;
    /**
     * 性别
     */
    @TableField("gender")
    private String gender;
    /**
     * 人脸库标识
     */
    @TableField("face_token")
    private String faceToken;

    @Override
    protected Serializable pkVal() {
        return this.openId;
    }

}
