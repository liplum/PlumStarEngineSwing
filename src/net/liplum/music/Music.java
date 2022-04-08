package net.liplum.music;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Music implements IMusic {
    private final String musicPath;
    private final URL url;
    private volatile boolean run = true;
    private Thread mainThread;
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


    private void prefetch() {
        try {
            audioStream = AudioSystem.getAudioInputStream(url);
            audioFormat = audioStream.getFormat();
            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class,
                    audioFormat, AudioSystem.NOT_SPECIFIED);
            sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);

            sourceDataLine.open(audioFormat);
            sourceDataLine.start();

        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        sourceDataLine.drain();
        sourceDataLine.close();
        audioStream.close();
    }

    private void playMusic(boolean loop) throws InterruptedException {
        try {
            if (loop) {
                while (true) {
                    playMusic();
                }
            } else {
                playMusic();
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


    private void stopMusic() {
        synchronized (this) {
            run = false;
            notifyAll();
        }
    }

    private void continueMusic() {
        synchronized (this) {
            run = true;
            notifyAll();
        }
    }


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

    public void continues() {
        new Thread(new Runnable() {
            public void run() {
                continueMusic();
            }
        }).start();
    }
}
