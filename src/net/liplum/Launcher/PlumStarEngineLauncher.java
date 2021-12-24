package net.liplum.Launcher;

import net.liplum.state.GUIStateMachine;

import javax.swing.*;
import java.awt.*;

/**
 * <br>
 * <font color=red>引擎的主类，请继承此类</font>
 *
 * @author 普冷姆plum
 * @version 1.4
 */
public abstract class PlumStarEngineLauncher extends JPanel implements Runnable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 设定的帧率
     */
    protected static int FPS = 30;
    /**
     * 每次更新最大的睡眠时长（毫秒）
     */
    protected static long maxSleepTime = (long) (1.0f / ((float) FPS) * 1000 + 0.5);
    /**
     * 窗口的宽、高度
     */
    private final int WinWidth;
    private final int WinHeight;
    /**
     * GUIPanel的状态机（负责每个大的界面：如菜单、暂停、加载、保存）
     */
    protected volatile GUIStateMachine StateMachine;

//	public static boolean hasRendered = false;
    /**
     * 当前需要渲染的画面
     */
    private volatile Image curFrame;

    /**
     * 进行渲染和更新的主要线程
     */
    private Thread MainThread;

    /**
     * 游戏是否正在进行
     */
    private volatile boolean isRunning = false;

    /**
     * @param width  设定窗口的宽度
     * @param height 设定窗口的高度
     */
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
     * 它会启动PlumStarEngineLauncher<br>
     * <font color=RED>请覆写它，并执行初始化工作。记得首先调用super方法，不要忘了在完成后添加以下几件事</font><br>
     * 为StateMachine添加新状态<br>
     * requestFocus();
     */
    @Override
    public void addNotify() {
        super.addNotify();
        initGUI();
    }

    /**
     * 初始化主要线程<br>
     * <font color=red>注意：它会开始新的线程（主要线程）</font>
     */
    private void initGUI() {
        isRunning = true;
        MainThread = new Thread(this, "MainThread");
        MainThread.start();
    }

    /**
     * 它会更新和渲染<br>
     * 当isRunning标记位为false时，退出程序<br>
     * <font color=red>注意：这是主要线程的Runnable</font>
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
     * 若没有画面，则准备一副新的画面图像<br>
     * 默认为白色背景
     */
    private void prepareFrame() {
        if (curFrame == null)
            curFrame = this.createImage(WinWidth, WinHeight);
        Graphics g = curFrame.getGraphics();
        g.clearRect(0, 0, WinWidth, WinHeight);
    }

    /**
     * 退出整个程序
     */
    public void exit() {
        isRunning = false;
    }

    /**
     * @param g 需要渲染的图像<br>
     *          渲染结束后会丢弃原本的画面
     */
    private void renderImageToScreen(Graphics g) {
        if (curFrame != null) {
            g.drawImage(curFrame, 0, 0, null);
        }
        g.dispose();
    }

    /**
     * @param delta 距离上一次更新和渲染总度过的时长(毫秒)<br>
     *              <font color=red>注意：先进行更新update，再进行渲染render</font>
     */
    private void updateAndRender(long delta) {
        StateMachine.update(delta);

        extraUpdate();

        StateMachine.execute();

        prepareFrame();
        StateMachine.render(curFrame.getGraphics());
//		此处可调用其他代码，额外地进行渲染图像

        extraRender();

//		Other code can be called here to render the image additionally.
        renderImageToScreen(getGraphics());
    }

    /**
     * 覆写此方法，可以额外的进行计算 <br>
     * Override this method to update extra.
     */
    protected abstract void extraUpdate();

    /**
     * 覆写此方法，可额外地进行渲染图像 <br>
     * Override this method to render the image extra.
     */
    protected abstract void extraRender();

}