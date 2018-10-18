package com.oa.service.impl;

import com.oa.base.Constant;
import com.oa.entity.User;
import com.oa.entity.UserThirdparty;
import com.oa.mapper.UserThirdpartyMapper;
import com.oa.model.ThirdPartyUser;
import com.oa.service.IUserService;
import com.oa.service.IUserThirdpartyService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 第三方用户表 服务实现类
 * </p>
 *
 * @author liugh123
 * @since 2018-07-27
 */
@Service
public class UserThirdpartyServiceImpl extends ServiceImpl<UserThirdpartyMapper, UserThirdparty> implements IUserThirdpartyService {

    @Autowired
    private IUserService userService;

    @Override
    public User insertThirdPartyUser(ThirdPartyUser param, String password) throws Exception{
        User sysUser = User.builder().password(password).username("游客"+param.getOpenid()).mobile(param.getOpenid())
                .avatar(param.getAvatarUrl()).build();
        User register = userService.register(sysUser, Constant.RoleType.USER);
        // 初始化第三方信息
        UserThirdparty thirdparty = UserThirdparty.builder().providerType(param.getProvider()).openId(param.getOpenid()).createTime(System.currentTimeMillis())
                .userNo(register.getUserNo()).status(Constant.ENABLE).accessToken(param.getToken()).build();
        this.insert(thirdparty);
        return register;
    }
}
