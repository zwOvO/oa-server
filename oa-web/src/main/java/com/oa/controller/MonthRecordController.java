package com.oa.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.oa.util.PublicResultConstant;
import com.oa.config.ResponseHelper;
import com.oa.config.ResponseModel;
import com.oa.entity.MonthRecord;
import com.oa.entity.dto.MonthRecordQuery;
import com.oa.entity.vo.MonthRecordVO;
import com.oa.service.IMonthRecordService;
import com.oa.util.DozerFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *  打卡记录接口
 * @author zhengwen
 * @since 2018年10月21日11:44:22
 */
@RestController()
@RequestMapping("/monthRecord")
@Api(description="月打卡记录模块")
@Slf4j
public class MonthRecordController {
    @Autowired
    IMonthRecordService monthRecordService;
    @Autowired
    DozerFactory dozerFactory;

    @PostMapping
    public ResponseModel add(@RequestBody MonthRecord monthRecord) {
        boolean res = monthRecordService.insert(monthRecord);
        return ResponseHelper.buildResponseModel(res);
    }

    @PutMapping
    public ResponseModel update(@RequestBody MonthRecord monthRecord) {
        boolean res = monthRecordService.updateById(monthRecord);
        return ResponseHelper.buildResponseModel(res);
    }

    @ApiOperation(value="下载Excel", notes="下载Excel")
    @GetMapping(value = "/excel")
    public void getExcelTemplateWithMonthRecord(HttpServletResponse response) {
        monthRecordService.getExcelTemplateWithMonthRecord(response);
    }

    @ApiOperation(value="管理员导入excel上传用户月打卡记录", notes="管理员导入excel上传用户月打卡记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "Excel", required = true, dataType = "file", paramType = "form")
    })
    @PostMapping(value = "/excel")
    public ResponseModel upLoadExcelWithMonthRecord(@RequestParam ("file" ) MultipartFile file) throws Exception {
        boolean res = monthRecordService.upLoadExcelWithMonthRecord(file);
        return ResponseHelper.buildResponseModel(res);
    }

    @DeleteMapping
    public ResponseModel delete(@ApiIgnore @RequestBody HashMap<String, ArrayList<String>> ids) {
        boolean res = monthRecordService.deleteBatchIds(ids.get("key"));
        if(res)
            return ResponseHelper.buildResponseModel("成功");
        else
            return ResponseHelper.internalServerError(PublicResultConstant.DATA_ERROR);
    }

    @GetMapping("/list")
    public ResponseModel list(MonthRecordQuery monthRecordQuery) {
        List<MonthRecordVO> records = monthRecordService.selectMonthRecordVOList(monthRecordQuery);
        ResponseModel responseModel = ResponseHelper.buildResponseModel(records);
        if(records.size()>0) {
            return responseModel;
        }else{
            responseModel.setCode(HttpStatus.NOT_FOUND.getReasonPhrase());
            return responseModel;
        }
    }

    @ApiOperation(value="分页获取指定用户月打卡记录", notes="分页获取指定用户月打卡记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "用户标识", required = false, dataType = "String",paramType = "path"),
            @ApiImplicitParam(name = "current", value = "0", required = false, dataType = "int",paramType="query"),
            @ApiImplicitParam(name = "size", value = "10", required = false, dataType = "int",paramType="query"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stopTime", value = "结束时间", required = false, dataType = "String", paramType = "query")
    })
    @GetMapping("/list/{openId}")
    public ResponseModel list(@PathVariable("openId")String openId, @ApiIgnore Page page, @RequestParam ("startTime" ) String startTime, @RequestParam ("stopTime" )String stopTime) {
        Page records = monthRecordService.selectPage(page,new EntityWrapper<MonthRecord>().eq("open_id",openId).between("create_time",startTime,stopTime).orderBy("create_time",true));
        if(records.getRecords().size()>0)
            return ResponseHelper.buildResponseModel(records.getRecords());
        else
            return ResponseHelper.notFound(PublicResultConstant.DATA_ERROR);
    }
}
