package com.oa.service.impl;

import com.oa.base.BaseServiceImpl;
import com.oa.entity.Record;
import com.oa.mapper.RecordMapper;
import com.oa.service.IRecordService;
import org.springframework.stereotype.Service;

@Service
public class RecordServiceImpl extends BaseServiceImpl<RecordMapper,Record> implements IRecordService {

}
