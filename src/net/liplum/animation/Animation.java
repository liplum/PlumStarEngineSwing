package net.liplum.animation;

import net.liplum.util.Parallel;
import net.liplum.util.ParallelArray;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


/**
 * Play the animation repeatedly as default
 */
public class Animation extends AnimatedBase {

    private final AnimationIterator iterator;
    private final AnimationClock clock;
    private final int length;
    private final Parallel<Frame, Long> frames;
    private final boolean hasAlpha;
    private long totalDuration = 0;

    //Frame stands for a graphic
    //Long类型是每一帧图像播放时的最后秒数

    //指ParallelArray<BufferedImage,Long>中的索引
    private ArrayList<AlphaComposite> alphas;
    private volatile boolean isKeepLastFrame = false;
    private volatile boolean isEndAfterPlaying = false;
    private long untilEndTime = 0;
    private volatile boolean isEarlyTermination = false;
    private volatile boolean isVisible = true;

    public Animation(int x, int y, Frame... fs) {
        super(x, y);
        length = fs.length;
        hasAlpha = fs[0].hasAlpha();

        if (hasAlpha)
            alphas = new ArrayList<>(length);

        frames = new ParallelArray<>(length);

        for (Frame f : fs) {
            totalDuration += f.getDuration();
            frames.add(f, totalDuration);
            if (hasAlpha)
                alphas.add(AlphaComposite.SrcOver.derive(f.getAlpha()));
        }


        this.iterator = new AnimationIterator();
        this.clock = new AnimationClock();

    }

    @Override
    public synchronized void reset() {
        isVisible = true;
        isEndAfterPlaying = false;
        isKeepLastFrame = false;
        isEarlyTermination = false;
        untilEndTime = 0;
        iterator.reset();
        clock.resetAll();
    }

    /**
     * 在第一次播放完成后，保持最后一帧
     */
    @Override
    public synchronized void setKeepLastFrame() {
        this.isKeepLastFrame = true;
    }

    /**
     * 在第一次播放完成后，不再显示
     */
    @Override
    public synchronized void setEndAfterPlaying() {
        this.isEndAfterPlaying = true;
    }

    /**
     * 设置一个时间，在播放此时长后，不再显示
     */
    @Override
    public synchronized void setEndTime(long sumTime) {
        this.isEarlyTermination = true;
        this.untilEndTime = sumTime;
    }

    public synchronized boolean isLastFrame() {
        return iterator.isLastFrame();
    }

    @Override
    public synchronized void update(long delta) {

        if (isVisible) {
            clock.addTime(delta);

            if (isEarlyTermination)
                if (clock.totalIsExceeded(untilEndTime))
                    isVisible = false;

            if (clock.isExceeded(iterator.getDuration())) {
                if (iterator.next())
                    ;
                else
                    clock.reset();
            }
        }
    }

    @Override
    public synchronized void render(Graphics g) {
        if (isVisible) {
            if (hasAlpha) {
                Graphics2D g2d = (Graphics2D) g;
                g2d.setComposite(alphas.get(iterator.getCurIndex()));
                g.drawImage(iterator.getCurImage(), x, y, null);
            } else
                g.drawImage(iterator.getCurImage(), x, y, null);
        }
    }

    @Override
    public synchronized String toString() {
        String str = "[";
        str += "TotalDuration(ms) : " + totalDuration + "\n";

        str += "KeepLastFrame : " + isKeepLastFrame + "\n";
        str += "EndAfterPlaying : " + isEndAfterPlaying + "\n";
        str += isEarlyTermination ? "EndTime : " + untilEndTime + "\n" : "";
        str += "is Visible now ? " + isVisible + "\n";

        for (int i = 0; i < frames.size(); i++) {
            str += "<" + (i + 1) + ">";
            str += frames.getFirstAt(i) + "->" + frames.getSecondAt(i);
            str += "\n";
        }
        return str += "]";
    }

    private class AnimationIterator {
        private final int maxIndex = length - 1;
        private volatile int curIndex = 0;

        public synchronized int getCurIndex() {
            return Math.min(curIndex, maxIndex);
        }


        //		前置条件：	当前的图片索引小于最大索引
//		后置条件：	如果满足前置条件，递增当前索引；返回true;
//				否则，重置当前索引为0，并返回fasle
        public synchronized boolean next() {
            curIndex++;
            if (curIndex > maxIndex) {
                if (isKeepLastFrame) {
                    curIndex = maxIndex;
                    return true;
                } else if (isEndAfterPlaying) {
                    isVisible = false;
                    curIndex = 0;
                    return false;
                }
                curIndex = 0;
                return false;
            }
            return true;
        }


        public synchronized void reset() {
            curIndex = 0;

        }


        public synchronized BufferedImage getCurImage() {
            return frames.getFirstAt(curIndex).getImage();
        }

        public synchronized long getDuration() {
            return frames.getSecondAt(curIndex);
        }

        public synchronized boolean isLastFrame() {
            return curIndex == maxIndex;
        }

    }

//	public synchronized void render(Graphics g,int width,int height) {
//		if(isVisible) {
//			if(hasAlpha) {
//				Graphics2D g2d= (Graphics2D) g;
//				g2d.setComposite(alphas.get(iterator.getCurIndex()));
//				g.drawImage(iterator.getCurImage(),x,y,width,height,null);
//			}else
//				g.drawImage(iterator.getCurImage(),x,y,width,height,null);
//		}
//	}

    private class AnimationClock {
        private volatile long curTime = 0;

        private volatile long totalTime = 0;

        public synchronized void addTime(long delta) {
            curTime += delta;
            totalTime += delta;
        }

        public synchronized void resetAll() {
            curTime = 0;
            totalTime = 0;
        }

        public synchronized void reset() {
            curTime %= totalDuration;
        }

        public synchronized boolean isExceeded(long comparison) {
            return curTime > comparison;
        }

        public synchronized boolean totalIsExceeded(long comparison) {
            return totalTime > comparison;
        }
    }

}
