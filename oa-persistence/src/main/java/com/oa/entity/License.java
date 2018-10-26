package com.oa.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.*;

import java.io.Serializable;
import java.sql.Timestamp;

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
@TableName("tb_license")
public class License extends Model<License> {

    private static final long serialVersionUID = 1L;
    /**
     * 授权码
     */
    @TableId(value = "id",type = IdType.UUID)
    private String id;
    /**
     * 用户标识
     */
    @TableField("open_id")
    private String openId;
    /**
     * 是否使用（0：未使用，1：已使用）
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
