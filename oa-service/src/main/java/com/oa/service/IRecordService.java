package com.oa.service;

import com.oa.base.BaseService;
import com.oa.entity.Record;
import com.oa.entity.dto.RecordQuery;
import com.oa.entity.vo.RecordVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IRecordService extends BaseService<Record> {
    List<RecordVO> selectRecordVOList(@Param("query") RecordQuery recordQuery);
}
