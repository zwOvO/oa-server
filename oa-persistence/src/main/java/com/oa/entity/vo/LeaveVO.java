package com.oa.entity.vo;

import lombok.*;

import java.sql.Timestamp;

/**
 * <p>
 *  请假
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
     * 请假开始时间
     */
    private String startTime;
    /**
     * 请假结束时间
     */
    private String stopTime;
    /**
     * 请假时间
     */
    private Timestamp createTime;

}
