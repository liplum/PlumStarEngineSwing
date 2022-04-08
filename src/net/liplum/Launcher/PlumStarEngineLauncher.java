package net.liplum.Launcher;

import net.liplum.state.GUIStateMachine;

import javax.swing.*;
import java.awt.*;

/**
 * The main class, please create a class extending this
 */
public abstract class PlumStarEngineLauncher extends JPanel implements Runnable {

    /**
     * The fps
     */
    protected static int FPS = 30;
    /**
     * The max sleep time after updating (unit:ms)
     */
    protected static long maxSleepTime = (long) (1.0f / ((float) FPS) * 1000 + 0.5);
    private final int WinWidth;
    private final int WinHeight;
    protected volatile GUIStateMachine StateMachine;

//	public static boolean hasRendered = false;
    /**
     * Current graphic to be rendered
     */
    private volatile Image curFrame;

    /**
     * The main thread of update and render
     */
    private Thread MainThread;

    /**
     * Whether this game is running
     */
    private volatile boolean isRunning = false;

    public PlumStarEngineLauncher(int width, int height) {
        WinWidth = width;
        WinHeight = height;

        StateMachine = new GUIStateMachine(this);

        setPreferredSize(new Dimension(width, height));

        setFocusable(true);

        setVisible(true);

        requestFocus();
    }

    /**
     * It will start PlumStarEngineLauncher
     * please create a class extending this and initialize your own module.
     * Such as adding state into {@link PlumStarEngineLauncher#StateMachine}
     */
    @Override
    public void addNotify() {
        super.addNotify();
        initGUI();
        requestFocus();
    }

    /**
     * Initialize the engine and create a main thread.
     */
    private void initGUI() {
        isRunning = true;
        MainThread = new Thread(this, "MainThread");
        MainThread.start();
    }

    /**
     * Update and render.
     */
    @Override
    public void run() {

        long updateDuration = 0;
        long sleepDuration = 0;
        long beforeUpdateRender = 0;
        long deltaTime = 0;

        while (isRunning) {
            beforeUpdateRender = System.nanoTime();
            deltaTime = updateDuration + sleepDuration;

            updateAndRender(deltaTime);

            updateDuration = (System.nanoTime() - beforeUpdateRender) / 1000000L;
            sleepDuration = Math.max(0, maxSleepTime - updateDuration);

            try {
                Thread.sleep(sleepDuration);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        System.exit(0);
    }

    /**
     * If it's no graphic then creating a new one.
     * The background is white as default.
     */
    private void prepareFrame() {
        if (curFrame == null)
            curFrame = this.createImage(WinWidth, WinHeight);
        Graphics g = curFrame.getGraphics();
        g.clearRect(0, 0, WinWidth, WinHeight);
    }

    public void exit() {
        isRunning = false;
    }

    private void renderImageToScreen(Graphics g) {
        if (curFrame != null) {
            g.drawImage(curFrame, 0, 0, null);
        }
        g.dispose();
    }

    /**
     * Update and then render.
     *
     * @param delta the delta after last calling this
     */
    private void updateAndRender(long delta) {
        StateMachine.update(delta);

        extraUpdate();

        StateMachine.execute();

        prepareFrame();
        StateMachine.render(curFrame.getGraphics());

        extraRender();

        // Other code can be called here to render the image additionally.
        renderImageToScreen(getGraphics());
    }

    protected abstract void extraUpdate();

    protected abstract void extraRender();
}