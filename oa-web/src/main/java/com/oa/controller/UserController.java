package com.oa.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.oa.base.PublicResultConstant;
import com.oa.config.ResponseHelper;
import com.oa.config.ResponseModel;
import com.oa.entity.User;
import com.oa.service.IUserService;
import com.oa.util.ComUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author oa
 * @since 2018-05-03
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping(value = "/pageList")
    public ResponseModel<Page<User>> findList(@RequestParam(name = "pageIndex", defaultValue = "1", required = false) Integer pageIndex,
                                 @RequestParam(name = "pageSize", defaultValue = "10", required = false) Integer pageSize,
                                 @RequestParam(value = "username", defaultValue = "",required = false) String username) {
        EntityWrapper<User> ew = new EntityWrapper<>();
        if (!ComUtil.isEmpty(username)) {
            ew.like("username", username);
        }
        return ResponseHelper.buildResponseModel(userService.selectPage(new Page<>(pageIndex, pageSize), ew));
    }

    @ApiOperation(value="判断当前用户是否存在", notes="根据url的id来查询账号是否存在")
    @ApiImplicitParam(name = "openId", value = "用户ID", required = true, dataType = "String",paramType = "path")
    @GetMapping(value = "/exist/{openId}")
    public ResponseModel findOneUser(@PathVariable("openId") String openId) {
        if(openId == null)
            return ResponseHelper.validationFailure(PublicResultConstant.INVALID_USER);
        User user = userService.selectById(openId);
        if(user == null)
            return ResponseHelper.validationFailure(PublicResultConstant.INVALID_USER);
        if(StringUtils.isBlank(user.getFaceToken()))
            return ResponseHelper.validationFailure(PublicResultConstant.UNAUTHORIZED);
        return ResponseHelper.buildResponseModel(PublicResultConstant.USER_EXIST);
    }

    @ApiOperation(value="删除用户", notes="根据url的id来删除用户")
    @ApiImplicitParam(name = "openId", value = "用户ID", required = true, dataType = "String",paramType = "path")
    @DeleteMapping(value = "/{openId}")
    public ResponseModel deleteUser(@PathVariable("openId") String openId) {
        User user = userService.selectById(openId);
        if (ComUtil.isEmpty(user)) {
            return ResponseHelper.validationFailure(PublicResultConstant.INVALID_USER);
        }
        boolean result = userService.deleteByUserNo(openId);
        return ResponseHelper.buildResponseModel(result);
    }

    @ApiOperation(value="更新用户照片信息", notes="根据openId更新照片信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "openId", value = "用户openId", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "file", value = "用户照片", required = true, dataType = "file", paramType = "form")
    })

    @PostMapping(value = "/face/{openId}")
    public ResponseModel validateFace(@PathVariable("openId") String openId, @RequestParam ("file" ) MultipartFile file) throws Exception {

        if(file .isEmpty()) {
            return ResponseHelper.buildResponseModel(false);
        }
        User user = userService.selectById(openId);
        boolean isInsert = StringUtils.isBlank(user.getFaceToken());
        String faceToken =  userService.validateFace(file.getInputStream(),"FJUT_CS1701_OA",user.getOpenId(),isInsert);
        if(!StringUtils.isBlank(faceToken)) {
            return ResponseHelper.buildResponseModel(faceToken);
        }else{
            return ResponseHelper.internalServerError("认证失败");
        }
    }


    @PostMapping("/register")
    public ResponseModel register (@RequestBody User user) throws Exception{
        boolean res = userService.insertOrUpdate(user);
        if(res)
            return ResponseHelper.buildResponseModel("成功");
        else
            return ResponseHelper.buildResponseModel("失败");
    }
}
