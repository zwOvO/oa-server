package com.oa.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.oa.base.BaseService;
import com.oa.entity.User;
import com.baomidou.mybatisplus.service.IService;

import java.io.InputStream;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhengwen
 * @since 2018年10月21日11:44:22
 */
public interface IUserService extends BaseService<User> {

    /**
     * 根据用户名查询用户
     * @param username 用户名
     * @return 用户
     */
    User getUserByUserName(String username);

    /**
     * 注册用户
     * @param user
     * @param roleCode
     * @return
     */
    User register(User user, String roleCode);

    boolean deleteByUserNo(String userNo);

    String validateFace(InputStream is, String groupId, String userId, boolean isInsert);
}
