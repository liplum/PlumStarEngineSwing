package net.liplum.util;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageTools {


    /**
     * @param g                背景图层
     * @param paster           贴纸图片
     * @param dx               x轴的位移
     * @param dy               y轴的位移
     * @param pasterZoomWidth  贴纸图片的缩放宽度
     * @param pasterZoomHeight 贴纸图片的缩放高度
     */
    public static void pasterImage(Graphics g, BufferedImage paster, int dx, int dy, int pasterZoomWidth, int pasterZoomHeight) {
        g.drawImage(paster, dx, dy, pasterZoomWidth, pasterZoomHeight, null);
    }

    /**
     * @param width            图片的宽度
     * @param height           图片的高度
     * @param color            图片的背景颜色。为null时，默认背景
     * @param paster           图片上的贴纸。为null时，为不贴贴纸。
     * @param dx               贴纸的x轴位移
     * @param dy               贴纸的y轴位移
     * @param pasterZoomWidth  贴纸的缩放宽度
     * @param pasterZoomHeight 贴纸的缩放高度
     * @return 一个带有ARBG通道的图片
     */
    public static BufferedImage newBufferedImage(int width, int height, Color color, BufferedImage paster, int dx, int dy, int pasterZoomWidth, int pasterZoomHeight) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        if (color != null)
            g.setColor(color);
        g.fillRect(0, 0, width, height);
        if (paster != null)
            pasterImage(g, paster, dx, dy, pasterZoomWidth, pasterZoomHeight);
        return image;
    }
}
