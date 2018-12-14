package com.oa.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.oa.util.PublicResultConstant;
import com.oa.config.ResponseHelper;
import com.oa.config.ResponseModel;
import com.oa.entity.Leave;
import com.oa.entity.dto.LeaveQuery;
import com.oa.entity.vo.LeaveVO;
import com.oa.service.ILeaveService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 *  打卡记录接口
 * @author zhengwen
 * @since 2018年10月21日11:44:22
 */
@RestController()
@RequestMapping("/leave")
@Api(description="请假模块")
@Slf4j
public class LeaveController {
    @Autowired
    ILeaveService leaveService;

    @ApiOperation(value="获取所有用户请假记录", notes="所有用户请假记录")
    @GetMapping("/list")
    public ResponseModel allList(LeaveQuery leaveQuery) {
        List<LeaveVO> records = leaveService.selectLeaveVOList(leaveQuery);
        ResponseModel responseModel = ResponseHelper.buildResponseModel(records);
        if(records.size()>0) {
            return responseModel;
        }else{
            responseModel.setCode(HttpStatus.NOT_FOUND.getReasonPhrase());
            return responseModel;
        }
    }

    @ApiOperation(value="分页获取指定用户请假记录", notes="获取指定用户请假记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "用户标识", required = false, dataType = "String",paramType = "path"),
            @ApiImplicitParam(name = "current", value = "0", required = false, dataType = "int",paramType="query"),
            @ApiImplicitParam(name = "size", value = "10", required = false, dataType = "int",paramType="query")
    })
    @GetMapping("/list/{openId}")
    public ResponseModel list(@PathVariable("openId")String openId, @ApiIgnore Page page) {
        Page records = leaveService.selectPage(page,new EntityWrapper<Leave>().eq("open_id",openId).orderBy("create_time",false));
        if(records.getRecords().size()>0)
            return ResponseHelper.buildResponseModel(records.getRecords());
        else
            return ResponseHelper.notFound(PublicResultConstant.DATA_ERROR);
    }

    @ApiOperation(value="用户提交请假申请", notes="用户提交请假申请")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "用户标识", required = true, dataType = "String",paramType = "path"),
            @ApiImplicitParam(name = "leaveType", value = "请假类型", required = true, dataType = "int",paramType="form"),
            @ApiImplicitParam(name = "message", value = "请假理由", required = true, dataType = "String",paramType="form")
    })
    @PostMapping("/submit/{openId}")
    public ResponseModel summit(@PathVariable("openId")String openId, @ApiIgnore @RequestBody Leave leave) {
        leave.setOpenId(openId);
        boolean res = leaveService.insert(leave);
        if(res)
            return ResponseHelper.buildResponseModel("成功");
        else
            return ResponseHelper.notFound(PublicResultConstant.DATA_ERROR);
    }

    @ApiOperation(value="管理员审批请假", notes="管理员审批请假")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "请假标识", required = true, dataType = "String",paramType = "body"),
            @ApiImplicitParam(name = "status", value = "审批结果", required = true, dataType = "int",paramType="body"),
//            @ApiImplicitParam(name = "message", value = "审批理由", required = true, dataType = "String",paramType="form")
    })
    @PutMapping("/audit")
    public ResponseModel audit(@RequestBody JSONObject json) {
        Leave leave = leaveService.selectById(json.getString("id"));
        leave.setStatus(json.getInteger("status"));
        boolean res = leaveService.updateById(leave);
        if(res)
            return ResponseHelper.buildResponseModel("成功");
        else
            return ResponseHelper.notFound(PublicResultConstant.DATA_ERROR);
    }
}
