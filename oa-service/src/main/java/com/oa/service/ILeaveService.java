package com.oa.service;

import com.oa.base.BaseService;
import com.oa.entity.Leave;
import com.oa.entity.dto.LeaveQuery;
import com.oa.entity.vo.LeaveVO;

import java.util.List;

public interface ILeaveService extends BaseService<Leave> {
    List<LeaveVO> selectLeaveVOList(LeaveQuery leaveQuery);
}
