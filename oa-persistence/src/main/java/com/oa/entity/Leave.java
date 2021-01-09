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
@TableName("tb_leave")
public class Leave extends Model<Leave> {

    private static final long serialVersionUID = 1L;
    /**
     * 请假标识
     */
    @TableId(value = "id",type = IdType.UUID)
    private String id;
    /**
     * 用户标识
     */
    @TableField("open_id")
    private String openId;
    /**
     * 请假理由
     */
    @TableField("message")
    private String message;
    /**
     * 请假类型（0：事假、1：婚假、2：丧假、3：产假、5：年假、6：调休、7：病假）
     */
    @TableField("leave_type")
    private int leaveType;
    /**
     * 请假状态（0：未审核、1：通过、2：拒绝）
     */
    @TableField("status")
    private int status;
    /**
     * 请假开始时间
     */
    @TableField("start_time")
    private String startTime;
    /**
     * 请假结束时间
     */
    @TableField("stop_time")
    private String stopTime;
    /**
     * 请假申请时间
     */
    @TableField("create_time")
    private Timestamp createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
