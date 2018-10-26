package com.oa.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.oa.entity.Record;
import com.oa.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  打卡记录Mapper 接口
 * </p>
 *
 * @author zhengwen
 * @since 2018年10月22日09:33:03
 */
@Repository
public interface RecordMapper extends BaseMapper<Record> {


}
