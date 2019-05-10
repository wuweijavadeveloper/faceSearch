package com.wuwei.hotel.test;


import cn.hutool.core.thread.ThreadUtil;
import com.wuwei.hotel.baidu.FaceApi;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.headless.HeadlessMediaPlayer;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;


public class VLCJTest {
    static BASE64Encoder encoder = new sun.misc.BASE64Encoder();
    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();

    private static BufferedImage image;
    private static MediaPlayerFactory mediaPlayerFactory;
    private static HeadlessMediaPlayer mediaPlayer;
    //--live-caching 0设置播放器缓存为0，保证获取到的都是实时画面，第二个参数可以不加，暂时没看出啥效果
    static String options[] = new String[]{"--live-caching 0", "--avcodec-hr=vaapi_drm"};
    //这两个参数可加可不加，如果想要通过窗口展示视频画面，就不加， 如果不想显示视频画面，就加上
   // static String[] VLC_ARGS = {"--vout", "dummy"};

    static String videoSources = "rtmp://60.222.222.19:1935/live/pag/60.222.222.19/7302/001703/0/SUB/TCP";
   // static String videoSources = "C:\\Users\\wuwei\\Desktop\\1.MP4";

    static int a=1;
    public static void main(String[] args) {
        new NativeDiscovery().discover();    //自动搜索libvlc路径并初始化，这行代码一定要加，且libvlc要已经安装，否则会报错
        // 创建播放器工厂
        mediaPlayerFactory = new MediaPlayerFactory();    //这样写的话则不展示视频图像， 要想展示图像的话则直接new MediaPlayerFactory()；
        // 创建一个HeadlessMediaPlayer ，在不需要展示视频画面的情况下，使用HeadlessMediaPlayer 是最合适的（尤其是在服务器环境下）
        mediaPlayer = mediaPlayerFactory.newHeadlessMediaPlayer();
        String url = videoSources;
        mediaPlayer.playMedia(url, options);    //开始播放视频，这里传入的是rtsp连接， 传入其他格式的链接也是可以的，网络链接、本地路径都行

        //开始播放之后，可以另起一个线程来获取视频帧 （这里使用的hutool框架来开启线程）

        ThreadUtil.execAsync(() -> {
            while (true) {

                if (mediaPlayer.isPlaying()) {
                    try {

                        while(image==null){
                            image = mediaPlayer.getSnapshot();
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

                /*   ByteArrayOutputStream baos = new ByteArrayOutputStream();

                    byte[] bytes = baos.toByteArray();

                     String pic=encoder.encodeBuffer(bytes).trim();//base64字符串*/
                    System.out.println(FaceApi.search(base64Img).get("result")+"===============");
                    mediaPlayer.saveSnapshot(file);





                }
            }
        });

    }
}
