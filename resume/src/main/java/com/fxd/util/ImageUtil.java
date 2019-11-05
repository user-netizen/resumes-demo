package com.fxd.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class ImageUtil {
    static Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    //获取系统路径根路径
    private static String baseOSPath = PathUtil.getImgBasePath();
    //获取当前日期
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    //随即数
    private static final Random r = new Random();

    //获取Resources相对路径
    private static String baseResourcesPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();

    //获取watermark水印图片路径
    private static String watermarkPath="/home/work/img";
    /**
     * 处理缩略图，并返回新生成图片的相对值路径
     *
     * @param io
     * @param targetAddr
     * @return
     */
    public static String generateThumbnail(InputStream io, String fileName, String targetAddr) {
        //生成随机文件
        String realFileName = getRandomFileName();
        //获取输入文件流扩展名
        String extension = getFileExtension(fileName);
        //创建指定目标文件路径
        makeDirPath(targetAddr);
        //获取相对路径（组合路径）
        String relativeAddr = targetAddr + realFileName + extension;
        logger.debug("current relativeAddr is：" + relativeAddr);
        //输出路径
        File dest = new File(PathUtil.getImgBasePath() + relativeAddr);
        logger.debug("current complete addr is：" + PathUtil.getImgBasePath() + relativeAddr);
        try {
            //配置参数 输出图片
            Thumbnails.of(io)
                    .size(500, 500).watermark(Positions.CENTER,
                    ImageIO.read(new File(URLDecoder.decode(watermarkPath + "/watermark.png","utf-8"))), 0.25f)
                    .outputQuality(1f)
                    .toFile(dest);
        } catch (IOException e) {
            logger.debug("Thumbnails error：" + e.getMessage());
            throw new RuntimeException("Thumbnails error：" + e.getMessage());
        }
        return relativeAddr;
    }

    /**
     * 创建目标路径所涉及到的目录，即D:/uploadFile/img/xxx.jpg
     * 那么uploadFile、img等文件夹需要自动创建
     *
     * @param targetAddress
     */
    public static void makeDirPath(String targetAddress) {
        String realFileParentPath = PathUtil.getImgBasePath() + targetAddress;
        File dirPath = new File(realFileParentPath);
        if (!dirPath.exists()) {
            //创建多级文件夹
            dirPath.mkdirs();
        }
    }

    /**
     * storePath是文件的路径还是目录的路径，
     * 如果storePath是文件路径则删除该文件，
     * 如果storePath是目录路径则删除该目录下的所有文件
     *
     * @param storePath
     */
    public static void deleteFileOrPath(String storePath) {
        File fileOrPath = new File(PathUtil.getImgBasePath() + storePath);
        if (fileOrPath.exists()) {
            if (fileOrPath.isDirectory()) {
                File files[] = fileOrPath.listFiles();
                for (int i = 0; i < files.length; i++) {
                    files[i].delete();
                }
            }
            fileOrPath.delete();
        }
    }

    /**
     * 获取输入文件流扩展名
     *
     * @param filename
     * @return
     */
    public static String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf("."));
    }

    /**
     * 生成随机文件名，当前年月日小时分钟秒钟+五位随机数
     *
     * @return 随机文件名
     */
    public static String getRandomFileName() {
        //获取随机的5位数
        int randomNum = r.nextInt(89999) + 10000;
        String nowTimeStr = sDateFormat.format(new Date());
        return nowTimeStr + randomNum;
    }

    //TODO：：这是测试
    public static void main(String[] args) throws IOException {
        File file = new File(baseOSPath + "/3.jpg");
        System.out.println(baseOSPath + "/3.jpg");

        InputStream io = null;
        try {
            io = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //配置参数 输出图片
        Thumbnails.of(io)
                .size(1000, 1000).watermark(Positions.CENTER,
                ImageIO.read(new File(baseResourcesPath + "/watermark.png")), 0.25f)
                .outputQuality(1f)
                .toFile(baseOSPath + "/upload/resumes/17/4_new.jpg");
//        Thumbnails.of(new File(baseOSPath + "/3.jpg"))
//                .size(1000, 1000).watermark(Positions.CENTER,
//                ImageIO.read(new File(baseResourcesPath + "/watermark.png")), 0.25f)
//                .outputQuality(1f)
//                .toFile(baseOSPath + "/3_new.jpg");
    }
}
