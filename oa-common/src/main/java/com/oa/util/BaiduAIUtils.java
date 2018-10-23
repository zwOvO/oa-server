package com.oa.util;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;
import jdk.internal.util.xml.impl.Input;
import org.json.JSONObject;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class BaiduAIUtils {
    //设置APPID/AK/SK
    public static final String APP_ID = "10681507";
    public static final String API_KEY = "h0rikGrSmtBLYiWAeOE6HQcz";
    public static final String SECRET_KEY = "VZSQFUVuY5BKPNE9YCWnoKpzQOn4I7mH";
    public static final AipFace client;
    static{
        // 初始化一个AipFace
        client = new AipFace(APP_ID, API_KEY, SECRET_KEY);
    }

    public static void main(String[] args) {
//        String res = addUser("G:/1.jpg","FJUT_CS1701_OA","test");
//        String res = detect("G:/1.jpg").toString();
//        //String res = match("fe3bfd789fd8e19dd016127794461656","G:/2.jpg").toString();
//        System.out.println(res);
    }

    //人脸对比
    public static JSONObject match(String faceToken,InputStream is) {
        try {
            String image1 = faceToken;
            String image2 = image2Base64String(is);

            // image1/image2也可以为url或facetoken, 相应的imageType参数需要与之对应。
            MatchRequest req1 = new MatchRequest(image1, "FACE_TOKEN");
            MatchRequest req2 = new MatchRequest(image2, "BASE64");
            ArrayList<MatchRequest> requests = new ArrayList<MatchRequest>();
            requests.add(req1);
            requests.add(req2);

            JSONObject res = client.match(requests);
            return res;
        }catch (Exception e){
            return new JSONObject().put("error_code",-1).put("error_msg",e.getMessage());
        }
    }

    //人脸检测
    public static JSONObject detect(InputStream content) {
        try {
            // 传入可选参数调用接口
            HashMap<String, String> options = new HashMap<String, String>();
            options.put("face_field", "age,gender,face_type,quality,blur");
            options.put("max_face_num", "2");
            options.put("face_type", "LIVE");

            String image = image2Base64String(content);
            String imageType = "BASE64";

            // 人脸检测
            JSONObject res = client.detect(image, imageType, options);
            return res;
        }catch (Exception e){
            return new JSONObject().put("error_code",-1).put("error_msg",e.getMessage());
        }
    }

    //人脸注册
    public static JSONObject addUser(String faceToken, String groupId, String userId) {
        try {
            // 传入可选参数调用接口
            HashMap<String, String> options = new HashMap<String, String>();
            options.put("user_info", "user's info");
            options.put("quality_control", "NORMAL");
            options.put("liveness_control", "LOW");

            String image = faceToken;
            String imageType = "FACE_TOKEN";

            // 人脸注册
            JSONObject res = client.addUser(image, imageType, groupId, userId, options);
            return res;
        }catch (Exception e){
            return new JSONObject().put("error_code",-1).put("error_msg",e.getMessage());
        }
    }

    //人脸更新
    public static JSONObject updateUser(String faceToken, String groupId, String userId) {
        try {
            // 传入可选参数调用接口
            HashMap<String, String> options = new HashMap<String, String>();
            options.put("user_info", "user's info");
            options.put("quality_control", "NORMAL");
            options.put("liveness_control", "LOW");

            String image = faceToken;
            String imageType = "FACE_TOKEN";

            // 人脸注册
            JSONObject res = client.updateUser(image, imageType, groupId, userId, options);
            return res;
        }catch (Exception e){
            return new JSONObject().put("error_code",-1).put("error_msg",e.getMessage());
        }
    }


    public static String image2Base64String(InputStream content) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            int length = 0;
            byte[] buffer = new byte[1024];
            while((length = content.read(buffer)) > 0){
                out.write(buffer,0,length);
            }
        } finally {
            if(out != null) out.close();
        }
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(out.toByteArray());
    }
}
