
package com.oa.entity.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;
import lombok.*;

import java.sql.Date;

/**
 * <p>
 *  月打卡记录表
 * </p>
 *
 * @author zhengwen
 * @since 2018年11月30日21:49:47
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MonthRecordExcelModel extends BaseRowModel {

    /**
     * 用户标识
     */
    @ExcelProperty(value = "用户标识", index = 0)
    private String openId;
    /**
     * 用户姓名
     */
    @ExcelProperty(value = "用户姓名", index = 1)
    private String userName;
    /**
     * 应到天数
     */
    @ExcelProperty(value = "应到天数", index = 2)
    private String shouldNum;
    /**
     * 实到天数
     */
    @ExcelProperty(value = "实到天数", index = 3)
    private String truedNum;
    /**
     * 请假天数
     */
    @ExcelProperty(value = "请假天数", index = 4)
    private String leaveNum;
    /**
     * 缺勤天数
     */
    @ExcelProperty(value = "缺勤天数", index = 5)
    private String absenceNum;
    /**
     * 月份
     */
    @ExcelProperty(value = "考勤年月", index = 6)
    private String forDate;

}
