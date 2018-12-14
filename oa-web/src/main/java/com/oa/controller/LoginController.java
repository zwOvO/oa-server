package com.oa.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.oa.util.PublicResultConstant;
import com.oa.config.ResponseHelper;
import com.oa.config.ResponseModel;
import com.oa.util.HttpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 *  登录接口
 * @author zhengwen
 * @since 2018年10月21日11:44:22
 */
@RestController
@Api(description="身份认证模块")
@Slf4j
public class LoginController {

    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestJson", value = "{\"code\":\"011HOTqS0GZIX72WECsS01o0rS0HOTqT\"}"
                    , required = true, dataType = "String",paramType="body")
    })
    @PostMapping("/login/miniprogram")
    public ResponseModel<Map<String, Object>> miniProgramLogin(
            @RequestBody JSONObject requestJson) throws Exception{
        //由于 @ValidationParam注解已经验证过mobile和passWord参数，所以可以直接get使用没毛病。
        String code = requestJson.getString("code");
        if(StringUtils.isBlank(code)){
            return ResponseHelper.validationFailure(PublicResultConstant.UNAUTHORIZED);
        }
        String res = HttpUtil.get("https://api.weixin.qq.com/sns/jscode2session?appid=wx060c24db20a8d971&secret=65e088e6d48af9a995343b554c78cf64&js_code=" + code + "&grant_type=authorization_code");
        JSONObject jsonObject = JSON.parseObject(res);
        String openid = jsonObject.getString("openid");
        if(!StringUtils.isBlank(openid)){
            return ResponseHelper.buildResponseModel(jsonObject);
        }else{
            return ResponseHelper.validationFailure(PublicResultConstant.PARAM_ERROR);
        }
    }
}
