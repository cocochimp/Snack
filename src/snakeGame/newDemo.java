package snakeGame;

import java.io.File;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.SourceDataLine;
import javax.swing.*;


public class newDemo {
    public static void main(String[] args) {
        //绘制一个静态窗口
        JFrame frame = new JFrame("贪吃蛇小游戏");
        frame(frame);

        //面板设置
        frame.add(new printGame());

        //播放音乐
        new Thread(()->{while(true) {newDemo.playMusic();} //while中的true可换成参数来控制音乐的停止播放
        }).start();
    }


    //1、绘制一个静态窗口
    public static void frame(JFrame frame){
        frame.setBounds(300,40,900,720);//设置界面大小
        frame.setResizable(false);//窗口大小是否可以改变
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭游戏，结束进程
        frame.setVisible(true);//让窗口能够展示出来
    }

    //2、播放音乐
    static void playMusic() {// 背景音乐播放
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File("E:\\专业资料\\project_Java\\Finish_Project\\greedySnake\\src\\resource\\zhiwu.wav"));    //绝对路径
            AudioFormat aif = ais.getFormat();
            final SourceDataLine sdl;
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, aif);
            sdl = (SourceDataLine) AudioSystem.getLine(info);
            sdl.open(aif);
            sdl.start();
            FloatControl fc = (FloatControl) sdl.getControl(FloatControl.Type.MASTER_GAIN);
            // value可以用来设置音量，从0-2.0
            double value = 2;
            float dB = (float) (Math.log(value) / Math.log(10.0) * 20.0);
            fc.setValue(dB);
            int nByte = 0;
            final int SIZE = 1024 * 64;
            byte[] buffer = new byte[SIZE];
            while (nByte != -1) {
                nByte = ais.read(buffer, 0, SIZE);
                sdl.write(buffer, 0, nByte);
            }
            sdl.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
