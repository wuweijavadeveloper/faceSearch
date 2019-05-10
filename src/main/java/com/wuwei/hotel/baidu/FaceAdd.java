package com.wuwei.hotel.baidu;

import com.baidu.aip.face.AipFace;
import com.wuwei.hotel.utils.GsonUtils;
import com.wuwei.hotel.utils.HttpUtil;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

public class FaceAdd {
    //设置APPID/AK/SK
    public static final String APP_ID = "16115978";
    public static final String API_KEY = "YynXVz9T7wfI1d18DZnMU7Gg";
    public static final String SECRET_KEY = "UigVzZzrxpmUjpoWsAYTIkBSC1LGMlr6";

/*    public static void main(String[] args) {
        // 初始化一个AipFace
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
     //   client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
      //  client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
     //   System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");

        // 调用接口
        String path = "test.jpg";
        JSONObject res = client.detect(path, "imageType",new HashMap<String, String>());
        System.out.println(res.toString(2));

    }*/

    /**
     * 人脸注册
     * @param client
     */
    public void sample(AipFace client) {
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("user_info", "user's info");
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");

        String image = "取决于image_type参数，传入BASE64字符串或URL字符串或FACE_TOKEN字符串";
        String imageType = "BASE64";
        String groupId = "group1";
        String userId = "user1";

        // 人脸注册
        JSONObject res = client.addUser(image, imageType, groupId, userId, options);
        System.out.println(res.toString(2));

    }

    public static String add() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/faceset/user/add";
        try {
            Map<String, Object> map = new HashMap<>();
            map.put("image", "027d8308a2ec665acb1bdf63e513bcb9");
            map.put("group_id", "group_repeat");
            map.put("user_id", "user1");
            map.put("user_info", "abc");
            map.put("liveness_control", "NORMAL");
            map.put("image_type", "FACE_TOKEN");
            map.put("quality_control", "LOW");

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = "[调用鉴权接口获取的token]";

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        FaceAdd.add();
    }
}
