package net.liplum.animation;

import java.awt.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class AnimeGroup {

    private final List<AnimationEntity> animations;
    private final AnimationGroupClock clock;
    private volatile Iterator<AnimationEntity> iterator;


    public AnimeGroup(AnimationEntity... as) {
        animations = Arrays.asList(as);

        animations.sort(null);

        clock = new AnimationGroupClock();

    }

    public synchronized void render(Graphics g) {
        iterator = animations.iterator();
        while (iterator.hasNext())
            iterator.next().render(g);

    }

    public synchronized void update(long delta) {
        clock.addTime(delta);

        iterator = animations.iterator();

        while (iterator.hasNext()) {
            AnimationEntity e = iterator.next();
            if (!e.canRender()) {
                if (clock.isExceeded(e.getUntilStartingTime()))
                    e.startRender();
            } else
                e.update(delta);
        }


    }

    public static class AnimationEntity implements Comparable<AnimationEntity> {

        private final Animated animation;

        private final Long UntilStartingTime;


        private boolean canRender = false;

        public AnimationEntity(Animated a, long UntilStartingTime) {
            this.animation = a;
            this.UntilStartingTime = UntilStartingTime;
        }

        public synchronized void render(Graphics g) {
            if (canRender)
                animation.render(g);
        }

        public synchronized void update(long delta) {
            if (canRender)
                animation.update(delta);
        }

        @Override
        public int compareTo(AnimationEntity b) {
            if (this.UntilStartingTime < b.UntilStartingTime)
                return -1;
            else if (this.UntilStartingTime > b.UntilStartingTime)
                return 1;
            return 0;
        }

        public synchronized void startRender() {
            canRender = true;
        }

        public synchronized boolean canRender() {
            return canRender;
        }


        public synchronized long getUntilStartingTime() {
            return UntilStartingTime;
        }
    }

    private static class AnimationGroupClock {
        private volatile long curTime = 0;

        public synchronized void addTime(long delta) {
            curTime += delta;
        }

//		public synchronized void reset() {
//			curTime %= totalDuration;
//		}

        public synchronized boolean isExceeded(long comparison) {
            return curTime > comparison;
        }
    }

}
