package com.oa.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.oa.base.PublicResultConstant;
import com.oa.config.ResponseHelper;
import com.oa.config.ResponseModel;
import com.oa.entity.Record;
import com.oa.entity.dto.RecordQuery;
import com.oa.entity.vo.RecordVO;
import com.oa.service.IRecordService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 *  打卡记录接口
 * @author zhengwen
 * @since 2018年10月21日11:44:22
 */
@RestController()
@RequestMapping("/record")
@Api(description="打卡记录模块")
@Slf4j
public class RecordController {
    @Autowired
    IRecordService recordService;

    @ApiOperation(value="打卡", notes="打卡")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "uuid", value = "打卡记录标识", required = true, dataType = "String",paramType = "body"),
        @ApiImplicitParam(name = "openId", value = "打卡记录标识", required = true, dataType = "String",paramType = "body")
    })
    @PostMapping()
    public ResponseModel punchTheClock(@ApiIgnore @RequestBody Record record) throws Exception{
        boolean res = recordService.insert(record);
        if(res)
            return ResponseHelper.buildResponseModel(PublicResultConstant.SUCCESS);
        else
            return ResponseHelper.validationFailure(PublicResultConstant.ERROR);
    }

    @GetMapping("/list")
    public ResponseModel list(RecordQuery recordQuery) {
        List<RecordVO> records = recordService.selectRecordVOList(recordQuery);
        ResponseModel responseModel = ResponseHelper.buildResponseModel(records);
        if(records.size()>0) {
            return responseModel;
        }else{
            responseModel.setCode(HttpStatus.NOT_FOUND.getReasonPhrase());
            return responseModel;
        }
    }

    @ApiOperation(value="分页获取指定用户打卡记录", notes="分页获取指定用户打卡记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "用户标识", required = false, dataType = "String",paramType = "path"),
            @ApiImplicitParam(name = "current", value = "0", required = false, dataType = "int",paramType="query"),
            @ApiImplicitParam(name = "size", value = "10", required = false, dataType = "int",paramType="query"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "stopTime", value = "结束时间", required = false, dataType = "String", paramType = "query")
    })
    @GetMapping("/list/{openId}")
    public ResponseModel list(@PathVariable("openId")String openId, @ApiIgnore Page page, @RequestParam ("startTime" ) String startTime, @RequestParam ("stopTime" )String stopTime) {
        Page records = recordService.selectPage(page,new EntityWrapper<Record>().eq("open_id",openId).between("create_time",startTime,stopTime).orderBy("create_time",true));
        if(records.getRecords().size()>0)
            return ResponseHelper.buildResponseModel(records.getRecords());
        else
            return ResponseHelper.notFound(PublicResultConstant.DATA_ERROR);
    }

    @ApiOperation(value="获取用户指定日期所有打卡记录", notes="获取用户指定日期所有打卡记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "用户标识", required = false, dataType = "String",paramType = "path"),
            @ApiImplicitParam(name = "date", value = "查询日期2018-10-25", required = false, dataType = "String", paramType = "query")
    })
    @GetMapping("/date/{openId}")
    public ResponseModel dateRecordList(@PathVariable("openId")String openId, @RequestParam ("date" ) String date) {
        List<Record> records = recordService.selectList(new EntityWrapper<Record>().like("create_time",date));
        if(records.size() > 0)
            return ResponseHelper.buildResponseModel(records);
        else
            return ResponseHelper.notFound(PublicResultConstant.DATA_ERROR);
    }
}
