package com.oa.mapper;

import com.oa.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

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


    //等同于编写一个普通 list 查询，mybatis-plus 自动替你分页
    int updateFaceTokenById(@Param("openId")String openId, @Param("faceToken")String faceToken);


}
