package com.wuwei.hotel.baidu;

import com.baidu.aip.face.AipFace;
import org.json.JSONObject;
import org.junit.Test;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;

public class FaceApi {
    //设置APPID/AK/SK
    public static final String APP_ID = "16143778";
    public static final String API_KEY = "IKRfcRGBtaz6wvb3GCxcBzTP";
    public static final String SECRET_KEY = "uFW1zBk0nZEGTox7AdD1RjBuTVGbADMq";



    public static AipFace getAipFace() {
        // 初始化一个AipFace
        AipFace client = new AipFace(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

/*        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理*/

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
  /*      System.setProperty("aip.log4j.conf", "path/to/your/log4j.properties");*/


        return client;

    }
    @Test
    public static  void facedemo(){

        // 调用接口
        String path = "https://bj.bcebos.com/v1/wisdomedu/c9ff66fe94a24efab50bc06c1a7e1547?authorization=bce-auth-v1%2F7642b4b48046452697e90662eed41ace%2F2019-03-15T03%3A55%3A19Z%2F-1%2F%2F31217c9d53cd85c7ce7bcfd9a1a3c6e9f298f756b0a8caa5f8efff519c7c8009";
        JSONObject res = getAipFace().detect(path,"URL", new HashMap<String, String>());
        System.out.println(res.toString(2));

    }

    @Test
    public static void adddemo(){
        // 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("user_info", "周杰伦");
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");

        String image = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1556545713471&di=0289d00ee916106cce5f473b2e21687e&imgtype=0&src=http%3A%2F%2Fimg4.a0bi.com%2Fupload%2Fttq%2F20180126%2F1516929021698.jpg%3FimageView2%2F0%2Fw%2F600%2Fh%2F600";
        String imageType = "URL";
        String groupId = "group1";
        String userId = "jay";

        // 人脸注册
        JSONObject res = getAipFace().addUser(image, imageType, groupId, userId, options);
        System.out.println(res.toString(2));



    }

    @Test
    public static  JSONObject search(String image){

// 传入可选参数调用接口
        HashMap<String, String> options = new HashMap<String, String>();
        options.put("quality_control", "NORMAL");
        options.put("liveness_control", "LOW");
      //  options.put("user_id", "233451");
        options.put("max_user_num", "3");

       // String image = "";
        String imageType = "BASE64";
        String groupIdList = "group1";

        // 人脸搜索
        JSONObject res = getAipFace().search(image, imageType, groupIdList, options);
       System.out.println(res.toString(2));
        return  res;
    }


}
