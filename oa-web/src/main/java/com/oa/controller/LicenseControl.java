package com.oa.controller;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.oa.base.PublicResultConstant;
import com.oa.config.ResponseHelper;
import com.oa.config.ResponseModel;
import com.oa.entity.License;
import com.oa.entity.vo.LicenseVO;
import com.oa.service.ILicenseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 *  授权码接口
 * @author zhengwen
 * @since 2018年10月21日11:44:22
 */
@RestController()
@RequestMapping("/license")
@Api(description="授权码模块")
@Slf4j
public class LicenseControl {
    @Autowired
    ILicenseService licenseService;

    @GetMapping
    public ResponseModel list() {
        List<LicenseVO> records = licenseService.selectLicenseVOList();
        return ResponseHelper.buildResponseModel(records);
    }

    @ApiOperation(value="添加授权码", notes="添加授权码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "license", value = "授权码", required = false, dataType = "String",paramType="body"),
    })
    @PostMapping
    public ResponseModel add(@ApiIgnore @RequestBody License license) {
        boolean res = licenseService.insert(license);
        if(res)
            return ResponseHelper.buildResponseModel("成功");
        else
            return ResponseHelper.notFound(PublicResultConstant.DATA_ERROR);
    }

    @ApiOperation(value="修改授权码", notes="修改授权码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "license", value = "授权码", required = true, dataType = "String",paramType="body"),
            @ApiImplicitParam(name = "openId", value = "用户编码", required = false, dataType = "String",paramType="body"),
            @ApiImplicitParam(name = "status", value = "是否使用", required = false, dataType = "int",paramType="body")
    })
    @PutMapping
    public ResponseModel update(@ApiIgnore @RequestBody License license) {
        boolean res = licenseService.updateById(license);
        if(res)
            return ResponseHelper.buildResponseModel("成功");
        else
            return ResponseHelper.notFound(PublicResultConstant.DATA_ERROR);
    }

    @ApiOperation(value="删除授权码", notes="删除授权码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "licenses", value = "授权码", required = true, dataType = "String",paramType="form"),
    })
    @DeleteMapping
    public ResponseModel delete(@ApiIgnore @RequestBody HashMap<String, ArrayList<String>> licenses) {
        boolean res = licenseService.deleteBatchIds(licenses.get("key"));
        if(res)
            return ResponseHelper.buildResponseModel("成功");
        else
            return ResponseHelper.notFound(PublicResultConstant.DATA_ERROR);
    }
}
