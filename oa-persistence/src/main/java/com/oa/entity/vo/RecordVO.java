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
 *  打卡记录表
 * </p>
 *
 * @author zhengwen
 * @since 2018年10月22日14:33:03
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecordVO{
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private String id;
    /**
     * 用户标识
     */
    private String openId;
    /**
     * 用户姓名
     */
    private String username;
    /**
     * 刷脸是否通过（0：未通过，1：通过）
     */
    private int status;
    /**
     * 打卡时间
     */
    private Timestamp createTime;
}
