package com.oa.mapper;

import com.baomidou.mybatisplus.plugins.Page;
import com.oa.entity.User;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liugh123
 * @since 2018-05-03
 */
@Repository
public interface UserMapper extends BaseMapper<User> {


    //等同于编写一个普通 list 查询，mybatis-plus 自动替你分页
    int updateFaceTokenById(@Param("openId")String openId, @Param("faceToken")String faceToken);


}
