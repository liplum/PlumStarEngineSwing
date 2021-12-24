package net.liplum.animation;

import java.awt.*;
import java.awt.image.BufferedImage;

public class FadeInAnimation implements Animated {

    public static final int FadeInLayerCount = 35;

    private final Frame[] fs = new Frame[FadeInLayerCount];

    private final Animation animation;

    private final long totalDuration;

    public FadeInAnimation(int x, int y, BufferedImage img, long duration) {

        totalDuration = duration;

        float alpha = 0;
        long curDuration = 0;

        for (int i = 0; i < FadeInLayerCount; i++) {

            curDuration = (totalDuration * (i + 1)) / FadeInLayerCount;

            alpha = ((float) (i + 1)) / ((float) FadeInLayerCount);

            BufferedImage curImage = img.getSubimage(0, 0, img.getWidth(), img.getHeight());

            fs[i] = new Frame(curImage, curDuration, alpha);
        }

        animation = new Animation(x, y, fs);
        animation.setKeepLastFrame();
    }

    @Override
    public void update(long delta) {
        animation.update(delta);

    }

    @Override
    public void render(Graphics g) {
        animation.render(g);
    }


    @Override
    public void reset() {
        animation.reset();
    }

    @Override
    public void setEndAfterPlaying() {
    }

    @Override
    public void setKeepLastFrame() {
    }

    @Override
    public void setEndTime(long sumTime) {
        animation.setEndTime(sumTime);
    }

    public int getX() {
        return animation.getX();
    }


    public void setX(int x) {
        animation.setX(x);
    }


    public int getY() {
        return animation.getY();
    }


    public void setY(int y) {
        animation.setY(y);
    }

    @Override
    public boolean isLastFrame() {
        return animation.isLastFrame();
    }

}
