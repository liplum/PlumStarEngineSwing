package net.liplum.music;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Music extends MusicBase {
    private final String musicPath; //音频文件
    private final URL url;
    private volatile boolean run = true;  //记录音频是否播放
    private Thread mainThread;   //播放音频的任务线程
    private AudioInputStream audioStream;
    private AudioFormat audioFormat;
    private SourceDataLine sourceDataLine;

    public Music(String musicPath) {
        this.musicPath = musicPath;
        this.url = this.getClass().getResource(musicPath);
        prefetch();
    }

    @Override
    public Music clone() {
        return new Music(this.musicPath);
    }

    @Override
    public void play() {
        this.start(false);
    }

    @Override
    public void loop() {
        this.start(true);
    }

    @Override
    public void stop() {
        new Thread(this::stopMusic).start();
    }


    //数据准备
    private void prefetch() {
        try {
            //获取音频输入流
//		File file = new File(musicPath);
//		audioStream = AudioSystem.getAudioInputStream(file);
//		InputStream ips = Music.class.getResourceAsStream(musicPath) ;
            audioStream = AudioSystem.getAudioInputStream(url);
            //获取音频的编码对�?
            audioFormat = audioStream.getFormat();
            //包装音频信息
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,
                    audioFormat, AudioSystem.NOT_SPECIFIED);
            //使用包装音频信息后的Info类创建源数据行，充当混频器的流
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

            sourceDataLine.open(audioFormat);
            sourceDataLine.start();

        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            ex.printStackTrace();
        }
    }

    //析构函数:关闭音频读取流和数据流
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        sourceDataLine.drain();
        sourceDataLine.close();
        audioStream.close();
    }

    //播放音频:通过loop参数设置是否循环播放
    private void playMusic(boolean loop) throws InterruptedException {
        try {
            if (loop) {
                while (true) {
                    playMusic();
                }
            } else {
                playMusic();
                //清空数据行并关闭
                sourceDataLine.drain();
                sourceDataLine.close();
                audioStream.close();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    private void playMusic() {
        try {
            synchronized (this) {
                run = true;
            }
            //通过数据行读取音频数据流，发送到混音器;
            //数据流传输过程：AudioInputStream -> SourceDataLine;
            audioStream = AudioSystem.getAudioInputStream(url);
            int count;
            byte[] tempBuff = new byte[1024];

            while ((count = audioStream.read(tempBuff, 0, tempBuff.length)) != -1) {
                synchronized (this) {
                    while (!run)
                        wait();
                }
                sourceDataLine.write(tempBuff, 0, count);

            }

        } catch (UnsupportedAudioFileException | IOException | InterruptedException ex) {
            ex.printStackTrace();
        }

    }


    //暂停播放音频
    private void stopMusic() {
        synchronized (this) {
            run = false;
            notifyAll();
        }
    }

    //继续播放音乐
    private void continueMusic() {
        synchronized (this) {
            run = true;
            notifyAll();
        }
    }


    //内部调用控制方法:生成音频主线程；
    private void start(boolean loop) {
        mainThread = new Thread(new Runnable() {
            public void run() {
                try {
                    playMusic(loop);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        mainThread.start();
    }

    //外部调用控制方法：暂停音频线程


    //外部调用控制方法：继续音频线程
    public void continues() {
        new Thread(new Runnable() {
            public void run() {
                continueMusic();
            }
        }).start();
    }


}
