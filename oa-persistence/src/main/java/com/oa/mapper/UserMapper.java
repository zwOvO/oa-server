package com.oa.mapper;

import com.oa.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oa.entity.dto.UserQuery;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  打卡记录Mapper 接口
 * </p>
 *
 * @author zhengwen
 * @since 2018年10月21日10:33:03
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    List<User> selectUserList(UserQuery query);

    int updateFaceTokenById(@Param("id")int id, @Param("faceToken")String faceToken);

    User selectByOpenId(@Param("openId")String openId);
}
