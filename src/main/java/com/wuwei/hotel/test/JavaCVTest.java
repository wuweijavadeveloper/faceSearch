package com.wuwei.hotel.test;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * TODO：处理视频.（1.将视频提取成帧图片）
 *
 * @author ChenP
 */
public class JavaCVTest {

    //视频文件路径
  //  private static String videoPath = "C:/Users/wuwei/Desktop/";
 //   private static String videoPath = "rtmp://60.222.222.19:1935/live/pag/60.222.222.19/7302/001703/0/SUB/TCP";
    private static String videoPath = "rtmp://60.222.250.118:1935/live/pag/192.168.10.240/7302/001108/0/SUB/TCP";

    //视频帧图片存储路径
    public static String videoFramesPath = "C:/Users/wuwei/Pictures/Camera Roll";

    /**
     * TODO 将视频文件帧处理并以“jpg”格式进行存储。
     * 依赖FrameToBufferedImage方法：将frame转换为bufferedImage对象
     *
     * @param videoFileName
     */
    public static void grabberVideoFramer(String videoFileName) throws FrameGrabber.Exception {
        //Frame对象
        Frame frame = null;
        //标识
        int flag = 0;
        /*
            获取视频文件
         */
//        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(videoPath + "/" + videoFileName);
        FFmpegFrameGrabber fFmpegFrameGrabber = new FFmpegFrameGrabber(videoPath );
        fFmpegFrameGrabber.setOption("rtmp_transport","tcp");


        try {
            fFmpegFrameGrabber.start();
            /*
            .getFrameRate()方法：获取视频文件信息,总帧数
             */
            int ftp = fFmpegFrameGrabber.getLengthInFrames();
//            System.out.println(fFmpegFrameGrabber.grabKeyFrame());
            System.out.println("时长 " + ftp / fFmpegFrameGrabber.getFrameRate() / 60);

            BufferedImage bImage = null;
            System.out.println("开始运行视频提取帧，耗时较长");

            while (true) {
                //文件绝对路径+名字
                String fileName = videoFramesPath + "/img_" + String.valueOf(flag) + ".jpg";
                //文件储存对象
                File outPut = new File(fileName);
                //获取帧
                frame = fFmpegFrameGrabber.grabImage();
//                System.out.println(frame);
                if (frame != null) {
                    ImageIO.write(FrameToBufferedImage(frame), "jpg", outPut);
                    //BufferedImage图片数据
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
    /*
        测试.....
     */
    public static void main(String[] args) throws FrameGrabber.Exception {
        String videoFileName = "";
        grabberVideoFramer(videoFileName);
    }
    public static String getVideoPath() {
        return videoPath;
    }
    public static void setVideoPath(String videoPath) {
        JavaCVTest.videoPath = videoPath;
    }
}

