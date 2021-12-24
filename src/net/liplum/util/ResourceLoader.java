package net.liplum.util;

import net.liplum.music.Music;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ResourceLoader {


    /**
     * @param fileName   需要加载的图片 的<font color=red>全称</font>(文件名+拓展名)
     * @param folderName 需要加载的图片 所在是上级目录   <font color=red>注：需在src文件夹下</font><br>格式例如："actor/hero"
     * @return 如果成功加载，则返回需要加载的图片
     */
    public static BufferedImage loadImage(String folderName, String fileName) {
        BufferedImage img = null;
        String filePath = "/" + folderName + "/" + fileName;
        try {
            img = ImageIO.read(ResourceLoader.class.getResourceAsStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }

    /**
     * @param file 需要加载的图片 的File对象
     * @return 如果加载成功，则返回需要加载的图片
     */
    public static BufferedImage loadImage(File file) {
        BufferedImage img = null;

        try {
            img = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return img;
    }


    /**
     * @param fileName   需要加载的音频 的<font color=red>全称</font>(文件名+拓展名)
     * @param folderName 需要加载的音频 所在是上级目录   <font color=red>注：需在src文件夹下</font>
     * @return 如果成功加载，则返回需要加载的音频
     */
    public static Music loadAudio(String folderName, String fileName) {
        Music music;
        String filePath = "/" + folderName + "/" + fileName;

        music = new Music(filePath);
        return music;
    }
}
