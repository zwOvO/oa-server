package com.oa.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.oa.base.BaseService;
import com.oa.entity.User;
import com.baomidou.mybatisplus.service.IService;
import com.oa.entity.dto.UserQuery;
import org.apache.ibatis.annotations.Param;

import java.io.InputStream;
import java.util.List;
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

    boolean deleteByUserNo(String userNo);

    String validateFace(InputStream is, String groupId, Integer userId, boolean isInsert);

    List<User> selectUserList(@Param("query") UserQuery recordQuery);

    User selectByOpenId(@Param("open_id") String openId);

    boolean insertOrUpdateByOpenId(User user);
}
