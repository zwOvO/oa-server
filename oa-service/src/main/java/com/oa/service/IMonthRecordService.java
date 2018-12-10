package com.oa.service;

import com.oa.base.BaseService;
import com.oa.entity.MonthRecord;
import com.oa.entity.dto.MonthRecordQuery;
import com.oa.entity.vo.MonthRecordVO;
import com.oa.entity.vo.RecordVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

public interface IMonthRecordService extends BaseService<MonthRecord> {
    List<MonthRecordVO> selectMonthRecordVOList(@Param("query") MonthRecordQuery monthRecordQuery);

    void getExcelTemplateWithMonthRecord(HttpServletResponse response);

    boolean upLoadExcelWithMonthRecord(MultipartFile file);
}
