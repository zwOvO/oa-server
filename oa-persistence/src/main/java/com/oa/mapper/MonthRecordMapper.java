package com.oa.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oa.entity.MonthRecord;
import com.oa.entity.Record;
import com.oa.entity.dto.MonthRecordQuery;
import com.oa.entity.vo.MonthRecordVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  打卡记录Mapper 接口
 * </p>
 *
 * @author zhengwen
 * @since 2018年10月22日15:21:03
 */
@Repository
public interface MonthRecordMapper extends BaseMapper<MonthRecord> {
    List<MonthRecordVO> selectLeaveVOList(MonthRecordQuery monthRecordQuery);
}
