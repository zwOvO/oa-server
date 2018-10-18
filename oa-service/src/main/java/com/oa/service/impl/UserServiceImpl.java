package com.oa.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.oa.base.Constant;
import com.oa.entity.Menu;
import com.oa.service.IMenuService;
import com.oa.service.IUserService;
import com.oa.service.IUserToRoleService;
import com.oa.entity.User;
import com.oa.entity.UserToRole;
import com.oa.mapper.UserMapper;
import com.oa.util.BaiduAIUtils;
import com.oa.util.GenerationSequenceUtil;
import com.oa.util.JWTUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liugh123
 * @since 2018-05-03
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private IUserToRoleService userToRoleService;
    @Autowired
    private IMenuService menuService;

    @Autowired
    private UserMapper mapper;

    @Override
//    @Cacheable(value = "UserToRole",keyGenerator="wiselyKeyGenerator")
    public User getUserByUserName(String username) {
        System.out.println("执行getUserByUserName方法了.....");
        EntityWrapper<User> ew = new EntityWrapper<>();
        ew.where("user_name={0}", username);
        return this.selectOne(ew);
    }

    @Override
    public User getUserByMobile(String mobile) {
        EntityWrapper<User> ew = new EntityWrapper<>();
        ew.eq("mobile", mobile);
        return this.selectOne(ew);
    }

    @Override
    public User register(User user, String  roleCode) {
        user.setUserNo(GenerationSequenceUtil.generateUUID("user"));
        user.setCreateTime(System.currentTimeMillis());
        boolean result = this.insert(user);
        if (result) {
            UserToRole userToRole  = UserToRole.builder().userNo(user.getUserNo()).roleCode(roleCode).build();
            userToRoleService.insert(userToRole);
        }
        return user;
    }

    @Override
    public Map<String, Object> getLoginUserAndMenuInfo(User user) {
        Map<String, Object> result = new HashMap<>();
        UserToRole userToRole = userToRoleService.selectByUserNo(user.getUserNo());
        user.setToken(JWTUtil.sign(user.getUserNo(), user.getPassword()));
        result.put("user",user);
        List<Menu> buttonList = new ArrayList<Menu>();
        //根据角色主键查询启用的菜单权限
        List<Menu> menuList = menuService.findMenuByRoleCode(userToRole.getRoleCode());
        List<Menu> retMenuList = menuService.treeMenuList(Constant.ROOT_MENU, menuList);
        for (Menu buttonMenu : menuList) {
            if(buttonMenu.getMenuType() == Constant.TYPE_BUTTON){
                buttonList.add(buttonMenu);
            }
        }
        result.put("menuList",retMenuList);
        result.put("buttonList",buttonList);
        return result;
    }

    @Override
    public boolean deleteByUserNo(String userNo) {
        EntityWrapper<UserToRole> ew = new EntityWrapper<>();
        ew.eq("user_no", userNo);
        boolean resultRole = userToRoleService.delete(ew);
        boolean  resultUser = this.deleteById(userNo);
        return resultRole && resultUser;
    }

    @Override
    public Page<User> selectPageByConditionUser(Page<User> userPage, String info, Integer[] status, String startTime, String endTime) {
        //注意！！ 分页 total 是经过插件自动 回写 到传入 page 对象
        return userPage.setRecords(mapper.selectPageByConditionUser(userPage, info,status,startTime,endTime));
    }

    @Override
    public boolean updateAvatar(String  imgPath,String groupId,String userId) {
        JSONObject res = BaiduAIUtils.detect("G:/1.jpg");
        int resCode = res.getInt("error_code");
        res = res.getJSONObject("result");
        int faceNum = res.getInt("face_num");
        if(resCode == 0 && faceNum == 1) {
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

            double left_check = occlusion.getDouble("left_check");
            double t_left_check = 0.8;

            double right_check = occlusion.getDouble("right_check");
            double t_right_check = 0.8;

            double chin_contour = occlusion.getDouble("chin_contour");
            double t_chin_contour = 0.6;

            double blur = quality.getDouble("blur");
            double t_blur = 0.7;

            int illumination = quality.getInt("illumination");
            int t_illumination = 40;

            double pitch = angle.getDouble("pitch");
            int t_pitch = 20;

            double roll = angle.getDouble("roll");
            int t_roll = 20;

            double yaw = angle.getDouble("yaw");
            int t_yaw = 20;

            int completeness = occlusion.getInt("completeness");
            int t_completeness = 1;

            int width = location.getInt("width");
            int t_width = 100;

            int height = location.getInt("height");
            int t_height = 100;
            if(
                    left_eye < t_left_eye
                    && right_eye < t_right_eye
                    && nose < t_nose
                    && mouth < t_mouth
                    && left_check < t_left_check
                    && right_check < t_right_check
                    && chin_contour < t_chin_contour
                    && blur > t_blur
                    && illumination > t_illumination
                    && pitch < t_pitch
                    && roll < t_roll
                    && yaw < t_yaw
                    && completeness == t_completeness
                    && width >= t_width
                    && height >= t_height
            ) {
                res = BaiduAIUtils.addUser(imgPath, groupId, userId);
                resCode = res.getInt("error_code");
                if(resCode == 0)
                    return true;
                else
                    return false;
            }else
                return false;
        }
        else
            return false;
    }

}
