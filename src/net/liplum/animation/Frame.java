package net.liplum.animation;

import java.awt.image.BufferedImage;

public class Frame {
    private final BufferedImage image;
    private final long duration;
    private float alpha = 1.0f;
    private boolean hasAlpha = false;

    public Frame(BufferedImage img, long dur) {
        this.image = img;
        this.duration = dur;

    }

    public Frame(BufferedImage img, long dur, float alpha) {
        this.image = img;
        this.duration = dur;
        this.alpha = alpha;
        this.hasAlpha = true;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Long getDuration() {
        return duration;
    }

    public float getAlpha() {
        return alpha;
    }

    public boolean hasAlpha() {
        return hasAlpha;
    }

}
