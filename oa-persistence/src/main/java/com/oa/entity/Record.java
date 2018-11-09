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
@TableName("tb_record")
public class Record extends Model<Record> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.INPUT)
    private String id;
    /**
     * 用户标识
     */
    @TableField("open_id")
    private String openId;
    /**
     * 刷脸是否通过（0：未通过，1：通过）
     */
    @TableField("status")
    private int status;
    /**
     * 打卡时间
     */
    @TableField("create_time")
    private Timestamp createTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
