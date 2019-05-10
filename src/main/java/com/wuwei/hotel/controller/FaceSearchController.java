package com.wuwei.hotel.controller;

;
import cn.hutool.core.thread.ThreadUtil;
import com.wuwei.hotel.baidu.FaceApi;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import sun.misc.BASE64Encoder;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;

@Controller
@RequestMapping("/face")
public class FaceSearchController {
    //设置APPID/AK/SK
    public static final String APP_ID = "16115978";
    public static final String API_KEY = "YynXVz9T7wfI1d18DZnMU7Gg";
    public static final String SECRET_KEY = "UigVzZzrxpmUjpoWsAYTIkBSC1LGMlr6";
    private static BufferedImage image;
    private static MediaPlayerFactory mediaPlayerFactory;
    private static HeadlessMediaPlayer mediaPlayer;
    //--live-caching 0设置播放器缓存为0，保证获取到的都是实时画面，第二个参数可以不加，暂时没看出啥效果
    static String options[] = new String[]{"--live-caching 0", "--avcodec-hr=vaapi_drm"};
    //这两个参数可加可不加，如果想要通过窗口展示视频画面，就不加， 如果不想显示视频画面，就加上
    //  static String[] VLC_ARGS = {"--vout", "dummy"};

    static String videoSources = "rtmp://60.222.222.19:1935/live/pag/60.222.222.19/7302/001703/0/SUB/TCP";
    static int a = 1;


    @RequestMapping("/search")
    @ResponseBody
    public void search() {
        new NativeDiscovery().discover();    //自动搜索libvlc路径并初始化，这行代码一定要加，且libvlc要已经安装，否则会报错
        // 创建播放器工厂
        mediaPlayerFactory = new MediaPlayerFactory();    //这样写的话则不展示视频图像， 要想展示图像的话则直接new MediaPlayerFactory()；
        // 创建一个HeadlessMediaPlayer ，在不需要展示视频画面的情况下，使用HeadlessMediaPlayer 是最合适的（尤其是在服务器环境下）
        mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();
        String url = videoSources;
        mediaPlayer.playMedia(url, options);    //开始播放视频，这里传入的是rtsp连接， 传入其他格式的链接也是可以的，网络链接、本地路径都行

        //开始播放之后，可以另起一个线程来获取视频帧 （这里使用的hutool框架来开启线程）

        ThreadUtil.execAsync(() -> {
            JSONObject res=null;
            while (true) {

                if (mediaPlayer.isPlaying()) {
                    try {

                        while (image == null) {
                            image = mediaPlayer.getSnapshot(100, 100);
                        }
                        //  Thread.sleep(10000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    System.out.println(image);
                    a++;
                    File file = new File("C:\\Users\\wuwei\\Pictures\\Camera Roll/"+a+".jpg");
                    // bufferImage->base64
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    ImageIO.write(image, "jpg", outputStream);
                    BASE64Encoder encoder = new BASE64Encoder();
                    String base64Img = encoder.encode(outputStream.toByteArray());


                    res = FaceApi.search(base64Img);
                        mediaPlayer.saveSnapshot(file);

                    System.out.println(res.get("result"));


                }
            }

        });



    }


    @RequestMapping("/iswuwei")
    @ResponseBody
    public String iswuwei(MultipartFile file) throws IOException {

        BASE64Encoder base64Encoder = new BASE64Encoder();
        String base64EncoderImg = base64Encoder.encode(file.getBytes());

        JSONObject res = FaceApi.search(base64EncoderImg);
        //    mediaPlayer.saveSnapshot(file);

        System.out.println(res.toString(2));
        System.out.println(res.get("result"));
        Object o=res.get("result");
        System.out.println(res.get("log_id"));
        if (o.equals(null)) {
            return "吴威迟到";
        } else {
           // return res.get("result").toString();
            return "你这分明是造反！";
        }

    }
}