package com.wuwei.hotel.baidu;



import com.wuwei.hotel.utils.HttpUtil;
import com.wuwei.hotel.utils.GsonUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.junit.Test;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

/**
 * 人脸搜索
 */
public class FaceSearch {
    private static String videoPath = "rtmp://60.222.250.118:1935/live/pag/192.168.10.240/7302/001108/0/SUB/TCP";



    public static void grabberVideoFramer() throws FrameGrabber.Exception {
        //Frame对象
        Frame frame = null;
        //标识
        int flag = 0;
        /*
            获取视频文件
         */
//        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(videoPath + "/" + videoFileName);
        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(videoPath );
        fFmpegFrameGrabber.setOption("rtmp_transport","tcp");//设置为tcp，否则检测几秒就掉线



        try {
            fFmpegFrameGrabber.start();
            /*
            .getFrameRate()方法：获取视频文件信息,总帧数
             */
            int ftp = fFmpegFrameGrabber.getLengthInFrames();
//            System.out.println(fFmpegFrameGrabber.grabKeyFrame());
            System.out.println("时长 " + ftp / fFmpegFrameGrabber.getFrameRate() / 30);

            BufferedImage bImage = null;
            System.out.println("开始运行视频提取帧，耗时较长");

            while (true) {

                //获取帧
                frame = fFmpegFrameGrabber.grabImage();
//                System.out.println(frame);
                if (frame != null) {

                    BufferedImage image=FrameToBufferedImage(frame);   //转化为bufferedImage
                    // bufferImage->base64
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    ImageIO.write(image, "jpg", outputStream);
                    BASE64Encoder encoder = new BASE64Encoder();
                    String base64Img = encoder.encode(outputStream.toByteArray());
                    search2(base64Img);//检测

                }
                flag++;
            }
//            System.out.println("============运行结束============");
//            fFmpegFrameGrabber.stop();
        } catch (IOException E) {
        }
    }




    public static BufferedImage FrameToBufferedImage(Frame frame) {
        //创建BufferedImage对象
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage bufferedImage = converter.getBufferedImage(frame);
        return bufferedImage;
    }













    /**
     * 重要提示代码中所需工具类
     * FileUtil,Base64Util,HttpUtil,GsonUtils请从
     * https://ai.baidu.com/file/658A35ABAB2D404FBF903F64D47C1F72
     * https://ai.baidu.com/file/C8D81F3301E24D2892968F09AE1AD6E2
     * https://ai.baidu.com/file/544D677F5D4E4F17B4122FBD60DB82B3
     * https://ai.baidu.com/file/470B3ACCA3FE43788B5A963BF0B625F3
     * 下载
     */
    public static String search() {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/multi-search";
        try {
            InputStream in=null;
            byte[] data=null;
            //读取图片字节数组
            in =new FileInputStream("C:/Users/wuwei/Desktop/微信图片_20190430180726.jpg");
            data=new byte[in.available()];
            in.read(data);
            in.close();
            BASE64Encoder encoder=new BASE64Encoder();
            String image=encoder.encode(data);


            Map<String, Object> map = new HashMap<>();
            map.put("image",image);
          //  map.put("liveness_control", "NORMAL");
            map.put("group_id_list", "group1");
            map.put("image_type", "BASE64");
          //  map.put("quality_control", "NONE");
            map.put("max_user_num",3);
            map.put("max_face_num",10);

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getToken();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result.toString());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void search2(String image) {
        // 请求url
        String url = "https://aip.baidubce.com/rest/2.0/face/v3/multi-search";
        try {

            Map<String, Object> map = new HashMap<>();
            map.put("image",image);
            //  map.put("liveness_control", "NORMAL");
            map.put("group_id_list", "group1");
            map.put("image_type", "BASE64");
            //  map.put("quality_control", "NONE");
            map.put("max_user_num",3);
            map.put("max_face_num",10);

            String param = GsonUtils.toJson(map);

            // 注意这里仅为了简化编码每一次请求都去获取access_token，线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
            String accessToken = AuthService.getToken();

            String result = HttpUtil.post(url, accessToken, "application/json", param);
            System.out.println(result.toString());
          //  return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws FrameGrabber.Exception {
        FaceSearch.grabberVideoFramer();
    }
}
