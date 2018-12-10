package com.oa.service.impl;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.Font;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.alibaba.excel.metadata.TableStyle;
import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.oa.base.BaseServiceImpl;
import com.oa.entity.MonthRecord;
import com.oa.entity.User;
import com.oa.entity.dto.MonthRecordExcelModel;
import com.oa.entity.dto.MonthRecordQuery;
import com.oa.entity.vo.MonthRecordVO;
import com.oa.mapper.MonthRecordMapper;
import com.oa.service.IMonthRecordService;
import com.oa.service.IUserService;
import com.oa.util.DozerFactory;
import com.oa.util.EasyExcelUtil;
import com.oa.util.ExcelReaderFactory;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class MonthRecordServiceImpl extends BaseServiceImpl<MonthRecordMapper, MonthRecord> implements IMonthRecordService {
    @Autowired
    MonthRecordMapper monthRecordMapper;

    @Autowired
    IUserService userService;
    @Autowired
    protected DozerFactory dozerFactory;

    @Override
    public List<MonthRecordVO> selectMonthRecordVOList(MonthRecordQuery monthRecordQuery) {
        List<MonthRecordVO> leaveList= monthRecordMapper.selectLeaveVOList(monthRecordQuery);
        return leaveList;
    }

    @Override
    public void getExcelTemplateWithMonthRecord(HttpServletResponse response){
        try {
            List<User> userList = userService.selectList(new EntityWrapper<User>().eq("status", 1));
            List<MonthRecordExcelModel> etuInfoList = new LinkedList<MonthRecordExcelModel>();
            MonthRecordExcelModel monthRecordExcelModel;
            for (User user : userList) {
                monthRecordExcelModel = new MonthRecordExcelModel();
                monthRecordExcelModel.setShouldNum("0");
                monthRecordExcelModel.setTruedNum("0");
                monthRecordExcelModel.setLeaveNum("0");
                monthRecordExcelModel.setAbsenceNum("0");

                monthRecordExcelModel.setForDate("1900/1/1");
                monthRecordExcelModel.setOpenId(user.getOpenId());
                monthRecordExcelModel.setUserName(user.getUsername());
                etuInfoList.add(monthRecordExcelModel);
            }
            //定义Excel正文背景颜色
            TableStyle tableStyle = new TableStyle();
            tableStyle.setTableContentBackGroundColor(IndexedColors.WHITE);

            //定义Excel正文字体大小
            Font font = new Font();
            font.setFontHeightInPoints((short) 10);
            tableStyle.setTableContentFont(font);

            Table table = new Table(0);
            table.setTableStyle(tableStyle);
            response.reset();
            // 文件名称转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
            response.addHeader("Content-Disposition",
                    "attachment;filename=" + new String("template.xlsx".getBytes("utf-8"), "iso8859-1"));
            response.setContentType("application/octet-stream");
            OutputStream os = response.getOutputStream();
            EasyExcelUtil.writeExcelWithModel(os, etuInfoList, table, MonthRecordExcelModel.class, ExcelTypeEnum.XLSX);
            os.flush();
            os.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    @Transactional
    public boolean upLoadExcelWithMonthRecord(MultipartFile file) {
        try {
            if (!file.isEmpty()) {
                AnalysisEventListener<MonthRecordExcelModel> listener = new AnalysisEventListener<MonthRecordExcelModel>() {

                    @Override
                    public void invoke(MonthRecordExcelModel object, AnalysisContext context) {
                        if(object.getForDate().equals("1900/1/1")){
                            object.setForDate("0");
                        }
                        SimpleDateFormat out = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
                        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
                        String dstr="1900-01-01";
                        Date date= null;
                        try {
                            date = sdf.parse(dstr);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        date = DateUtils.addDays(date,Integer.valueOf(object.getForDate()));
                        object.setForDate(out.format(date));
                        System.err.println("Row:" + context.getCurrentRowNum() + " Data:"
                                + object.toString());
                        MonthRecord monthRecord = dozerFactory.convert(object,MonthRecord.class);
                        monthRecord.setCreateTime(new Timestamp(new Date().getTime()));
                        insert(monthRecord);
                    }

                    @Override
                    public void doAfterAllAnalysed(AnalysisContext context) {
                        System.err.println("doAfterAllAnalysed...");
                    }
                };
                ExcelReader excelReader = ExcelReaderFactory.getExcelReader(file.getInputStream(), null, listener);
                excelReader.read(new Sheet(1, 1, MonthRecordExcelModel.class));
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
        return true;
    }
}
