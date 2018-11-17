package com.oa.service.impl;

import com.oa.base.BaseServiceImpl;
import com.oa.entity.Record;
import com.oa.entity.dto.RecordQuery;
import com.oa.entity.vo.RecordVO;
import com.oa.mapper.RecordMapper;
import com.oa.service.IRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl extends BaseServiceImpl<RecordMapper,Record> implements IRecordService {
    @Autowired
    RecordMapper recordMapper;

    @Override
    public List<RecordVO> selectRecordVOList(RecordQuery recordQuery) {
        List<RecordVO> leaveList= recordMapper.selectRecordVOList(recordQuery);
        return leaveList;
    }
}
