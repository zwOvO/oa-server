package com.oa.controller;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.oa.base.PublicResultConstant;
import com.oa.config.ResponseHelper;
import com.oa.config.ResponseModel;
import com.oa.entity.License;
import com.oa.entity.User;
import com.oa.entity.dto.LoginDTO;
import com.oa.entity.dto.UserQuery;
import com.oa.entity.vo.LoginVO;
import com.oa.service.ILicenseService;
import com.oa.service.IUserService;
import com.oa.util.ComUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

/**
 * @author zhengwen
 * @since 2018年10月21日11:44:22
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Autowired
    IUserService userService;
    @Autowired
    ILicenseService licenseService;

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

    @GetMapping(value = "/list")
    public ResponseModel allList(UserQuery userQuery) {
        List<User> records = userService.selectUserList(userQuery);
        ResponseModel responseModel = ResponseHelper.buildResponseModel(records);
        if(records.size()>0) {
            return responseModel;
        }else{
            responseModel.setCode(HttpStatus.NOT_FOUND.getReasonPhrase());
            return responseModel;
        }
    }

    @PostMapping(value = "/login")
    public ResponseModel adminLogin(@RequestBody LoginDTO loginDTO, LoginVO loginVO) {
        String userName = loginDTO.getUserName();
        String password = loginDTO.getPassword();
        String type = loginDTO.getType();
        ResponseModel responseModel;
        if(userName == "" && password == ""){
            return ResponseHelper.buildResponseModel(PublicResultConstant.INVALID_USERNAME_PASSWORD);
        }else{
            if(("admin".equals(userName) && "888888".equals(password)) || ("user".equals(userName) && "123456".equals(password))){
                loginVO.setStatus("ok");
                loginVO.setCurrentAuthority("admin");
            }else{
                loginVO.setStatus("error");
                loginVO.setCurrentAuthority("guest");
            }
            loginVO.setType(loginDTO.getType());
            return ResponseHelper.buildResponseModel(loginVO);
        }
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

    @PostMapping("/bindLicense/{openId}/{licenseStr}")
    public ResponseModel bindLicense (@PathVariable("openId") String openId,@PathVariable("licenseStr")String licenseStr) throws Exception{
        License license =  licenseService.selectById(licenseStr);
        if(license !=null){
            if(license.getStatus() == 1){
                if(license.getOpenId().equals(openId)){
                    return ResponseHelper.buildResponseModel(PublicResultConstant.SUCCESS);
                }else {
                    return ResponseHelper.validationFailure(PublicResultConstant.PARAM_ERROR);
                }
            }else {
                license.setOpenId(openId);
                license.setStatus(1);
                boolean res = licenseService.update(license, new EntityWrapper<License>().eq("license", licenseStr));
                if (res)
                    return ResponseHelper.buildResponseModel(PublicResultConstant.SUCCESS);
                else
                    return ResponseHelper.validationFailure(PublicResultConstant.FAILED);
            }
        }else
            return ResponseHelper.validationFailure(PublicResultConstant.ERROR);
    }

    @ApiOperation(value="更新用户", notes="更新用户")
    @PutMapping("/{openId}")
    public ResponseModel update (@PathVariable("openId") String openId,@RequestBody User user) throws Exception{
        User userTemp = userService.selectById(user.getOpenId());
        userTemp.setGender(user.getGender());
        userTemp.setUsername(user.getNickname());
        userTemp.setStatus(user.getStatus());
        boolean res = userService.updateById(userTemp);
        if(res)
            return ResponseHelper.buildResponseModel(PublicResultConstant.SUCCESS);
        else
            return ResponseHelper.validationFailure(PublicResultConstant.ERROR);
    }

    @PostMapping("/register")
    public ResponseModel register (@RequestBody User user) throws Exception{

        boolean res = userService.insertOrUpdate(user);
        if(res)
            return ResponseHelper.buildResponseModel(PublicResultConstant.SUCCESS);
        else
            return ResponseHelper.validationFailure(PublicResultConstant.ERROR);
    }
}

