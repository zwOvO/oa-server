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
@TableName("tb_leave_approval")
public class LeaveApproval extends Model<LeaveApproval> {

    private static final long serialVersionUID = 1L;
    /**
     * 审批标识
     */
    @TableId(value = "id",type = IdType.UUID)
    private String id;
    /**
     * 请假标识
     */
    @TableField("leave_id")
    private String leaveId;

    /**
     * 审批留言
     */
    @TableField("message")
    private String message;
    /**
     * 审批时间
     */
    @TableField("create_time")
    private Timestamp createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
