package com.oa.entity.vo;

import lombok.*;

import java.sql.Date;
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
public class MonthRecordVO {
    private String id;
    private int shouldNum;
    private float truedNum;
    private float leaveNum;
    private float absenceNum;
    private String openId;
    private String userName;
    private Date forDate;
    private Timestamp createTime;
}
