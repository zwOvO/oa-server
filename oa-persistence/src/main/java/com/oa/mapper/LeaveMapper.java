package com.oa.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oa.entity.Leave;
import com.oa.entity.Record;
import com.oa.entity.dto.LeaveQuery;
import com.oa.entity.vo.LeaveVO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  打卡记录Mapper 接口
 * </p>
 *
 * @author zhengwen
 * @since 2018年10月22日09:33:03
 */
@Repository
public interface LeaveMapper extends BaseMapper<Leave> {
    List<LeaveVO> selectLeaveVOList(LeaveQuery query);
}
