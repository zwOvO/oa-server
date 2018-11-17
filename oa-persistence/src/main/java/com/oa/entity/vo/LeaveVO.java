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
public class LeaveVO{

    private static final long serialVersionUID = 1L;
    /**
     * 请假标识
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
     * 请假理由
     */
    private String message;
    /**
     * 请假类型（0：事假、1：婚假、2：丧假、3：产假、5：年假、6：调休、7：病假）
     */
    private int leaveType;
    /**
     * 请假状态（0：未审核、1：通过、2：拒绝）
     */
    private int status;
    /**
     * 请假时间
     */
    private Timestamp createTime;

}
