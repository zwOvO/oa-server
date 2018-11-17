package com.oa.entity.vo;

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
public class LicenseVO{

    private static final long serialVersionUID = 1L;
    /**
     * 授权码
     */
    private String license;
    /**
     * 用户标识
     */
    private String openId;
    /**
     * 用户姓名
     */
    private String username;
    /**
     * 是否使用（0：未使用，1：已使用）
     */
    private int status;
    /**
     * 创建时间
     */
    private Timestamp createTime;

}
