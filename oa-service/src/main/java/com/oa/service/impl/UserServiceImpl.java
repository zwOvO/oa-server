package com.oa.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oa.entity.dto.UserQuery;
import com.oa.service.IUserService;
import com.oa.entity.User;
import com.oa.mapper.UserMapper;
import com.oa.util.BaiduAIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhengwen
 * @since 2018年10月21日11:44:22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    UserMapper userMapper;

    @Override
    public boolean deleteByUserNo(String userNo) {
        boolean  resultUser = this.deleteById(userNo);
        return resultUser;
    }

    @Override
    public String validateFace(InputStream is, String groupId, Integer userId, boolean isInsert) {
        try {
            JSONObject res = JSONObject.parseObject(BaiduAIUtils.detect(is));
            int resCode = res.getIntValue("error_code");
            res = res.getJSONObject("result");
            int faceNum = res.getIntValue("face_num");
            if (resCode == 0 && faceNum == 1) {
                //http://ai.baidu.com/docs#/Face-Java-SDK/ca2bad80 人脸质量检测参数
                res = res.getJSONArray("face_list").getJSONObject(0);
                JSONObject quality = res.getJSONObject("quality");
                JSONObject location = res.getJSONObject("location");
                JSONObject angle = res.getJSONObject("angle");
                JSONObject occlusion = quality.getJSONObject("occlusion");
                double left_eye = occlusion.getDouble("left_eye");
                double t_left_eye = 0.6;

                double right_eye = occlusion.getDouble("right_eye");
                double t_right_eye = 0.6;

                double nose = occlusion.getDouble("nose");
                double t_nose = 0.7;

                double mouth = occlusion.getDouble("mouth");
                double t_mouth = 0.7;

                double left_cheek = occlusion.getDouble("left_cheek");
                double t_left_cheek = 0.8;

                double right_cheek = occlusion.getDouble("right_cheek");
                double t_right_cheek = 0.8;

                double chin_contour = occlusion.getDouble("chin_contour");
                double t_chin_contour = 0.6;

                double blur = quality.getDouble("blur");
                double t_blur = 0.7;

                int illumination = quality.getIntValue("illumination");
                int t_illumination = 40;

                double pitch = angle.getDouble("pitch");
                int t_pitch = 20;

                double roll = angle.getDouble("roll");
                int t_roll = 20;

                double yaw = angle.getDouble("yaw");
                int t_yaw = 20;

                int completeness = quality.getIntValue("completeness");
                int t_completeness = 1;

                int width = location.getIntValue("width");
                int t_width = 100;

                int height = location.getIntValue("height");
                int t_height = 100;
                if (
                        left_eye < t_left_eye
                                && right_eye < t_right_eye
                                && nose < t_nose
                                && mouth < t_mouth
                                && left_cheek < t_left_cheek
                                && right_cheek < t_right_cheek
                                && chin_contour < t_chin_contour
                                && blur < t_blur
                                && illumination > t_illumination
                                && pitch < t_pitch
                                && roll < t_roll
                                && yaw < t_yaw
                                && completeness == t_completeness
                                && width >= t_width
                                && height >= t_height
                ) {
                    String faceToken = res.getString("face_token");
                    if (isInsert) {
                        res = JSONObject.parseObject(BaiduAIUtils.addUser(faceToken, groupId, userId));
                    } else {
                        res = JSONObject.parseObject(BaiduAIUtils.updateUser(faceToken, groupId, userId));
                        faceToken = res.getJSONObject("result").getString("face_token");
                    }
                    resCode = res.getIntValue("error_code");
                    // success || error_msg -> face already exist
                    if (resCode == 0 || 223105 == res.getIntValue("error_code")) {
                        userMapper.updateFaceTokenById(userId, faceToken);
                        return faceToken;
                    } else
                        return null;
                } else
                    return null;
            } else
                return null;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<User> selectUserList(UserQuery userQuery) {
        List<User> userList= userMapper.selectUserList(userQuery);
        return userList;
    }

    @Override
    public User selectByOpenId(String openId) {
        return userMapper.selectByOpenId(openId);
    }

    @Override
    public boolean insertOrUpdateByOpenId(User user) {
        User dbUser = userMapper.selectByOpenId(user.getOpenId());
        if (dbUser != null) {
            user.setId(dbUser.getId());
            userMapper.updateAllColumnById(user);
        } else {
            userMapper.insert(user);
        }
        return true;
    }
}
