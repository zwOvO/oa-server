package com.oa.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * <p>
 *  月打卡记录表
 * </p>
 *
 * @author zhengwen
 * @since 2018年10月23日09:33:03
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("tb_month_revord")
public class MonthRecord extends Model<MonthRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * 月打卡记录标识
     */
    @TableId(value = "id",type = IdType.UUID)
    private String id;
    /**
     * 应到天数
     */
    @TableField("should_num")
    private int shouldNum;
    /**
     * 实到天数
     */
    @TableField("trued_num")
    private float truedNum;
    /**
     * 请假天数
     */
    @TableField("leave_num")
    private float leaveNum;
    /**
     * 缺勤天数
     */
    @TableField("absence_num")
    private float absenceNum;
    /**
     * 月份
     */
    @TableField("for_date")
    private Date forDate;
    /**
     * 用户标识
     */
    @TableField("open_id")
    private String openId;
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
