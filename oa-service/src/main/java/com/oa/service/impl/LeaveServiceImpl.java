package com.oa.service.impl;

import com.oa.base.BaseServiceImpl;
import com.oa.entity.Leave;
import com.oa.entity.dto.LeaveQuery;
import com.oa.entity.vo.LeaveVO;
import com.oa.mapper.LeaveMapper;
import com.oa.service.ILeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveServiceImpl extends BaseServiceImpl<LeaveMapper, Leave> implements ILeaveService {
    @Autowired
    LeaveMapper leaveMapper;

    public List<LeaveVO> selectLeaveVOList(LeaveQuery leaveQuery) {
        List<LeaveVO> leaveList= leaveMapper.selectLeaveVOList(leaveQuery);
        return leaveList;
    }
}
