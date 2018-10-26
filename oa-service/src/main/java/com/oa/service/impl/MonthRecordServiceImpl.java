package com.oa.service.impl;

import com.oa.base.BaseServiceImpl;
import com.oa.entity.MonthRecord;
import com.oa.mapper.MonthRecordMapper;
import com.oa.service.IMonthRecordService;
import org.springframework.stereotype.Service;

@Service
public class MonthRecordServiceImpl extends BaseServiceImpl<MonthRecordMapper, MonthRecord> implements IMonthRecordService {
}
