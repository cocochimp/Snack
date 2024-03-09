package snakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

public class printGame extends JPanel implements KeyListener, ActionListener {

    //1、蛇和食物的属性
    int length; //蛇的长度
    int[] snakeX=new int[600]; //蛇的X轴
    int[] snakeY=new int[500]; //蛇的Y轴
    String fx; //控制方向
    int foodx;  //食物的X轴
    int foody;  //食物的Y轴
    Random r=new Random();  //随机生成


    //2、游戏体制属性
    boolean isStart=false; //游戏是否开始
    Timer timer=new Timer(100,this); //刷新频率
    boolean isFail=false;  //死亡判断
    int score;  //积分系统
    int min=0;  //时间机制:分
    int sec=0;  //时间机制:秒
    int times=0; //刷新次数

    //3、存储数据
    int[] arr=new int[3];
    int[] arr123={1,2,3};



    //一、初始化数据
    public void init(){
        //初始化蛇的形态
        length=3;
        snakeX[0]=100; snakeY[0]=100;
        snakeX[1]=75;snakeY[1]=100;
        snakeX[2]=50;snakeY[2]=100;

        //初始化蛇的方向
        fx="R";

        //初始化食物的生成位置
        foodx =25+25*r.nextInt(29);
        foody =25+25*r.nextInt(25);

        //初始化分数
        score=0;
    }


    //二、画笔：Graphics
    @Override
    protected void paintComponent(Graphics g) {
        //绘制游戏界面
        printInterface(g);

        //绘制蛇和食物
        printSnake(g);

        //绘制游戏提示
        printRemind(g);
    }



    //三、接收键盘的输入:监听功能
    @Override
    public void keyPressed(KeyEvent e) {
       //键盘按下,未释放
       //获取按下的键盘是哪个键
       int keyCode = e.getKeyCode();

       //按空格启动或暂停
       if(keyCode==KeyEvent.VK_SPACE){
           if(isFail){
               isFail=false;
               init();  //重新进入游戏
           }else{
               isStart=!isStart;  //暂停游戏
           }

           repaint(); //刷新界面
       }

       //键盘控制走向
       if(keyCode==KeyEvent.VK_D || keyCode==KeyEvent.VK_RIGHT){
           fx="R";
       }else if(keyCode==KeyEvent.VK_A || keyCode==KeyEvent.VK_LEFT){
           fx="L";
       }else if(keyCode==KeyEvent.VK_W || keyCode==KeyEvent.VK_UP){
           fx="W";
       }else if(keyCode==KeyEvent.VK_S || keyCode==KeyEvent.VK_DOWN){
           fx="S";
       }

   }



    //四、定时器,执行定时操作
    @Override
    public void actionPerformed(ActionEvent e) {
       //如果游戏开始,且游戏没有失败！
       if(isStart && !isFail){
           startGame();
       }
       //游戏失败
       else if(isFail){
           endGame();
       }

       timer.start(); //让时间动起来
   }



    //五、构造器
    public printGame(){
        init();
        //获取键盘的监听事件
        this.setFocusable(true);
        this.addKeyListener(this);
        timer.start();  //让时间动起来
    }


    //1、绘制小蛇和食物
    public void printSnake(Graphics g){
        //画一条小蛇
        switch (fx) {
            case "R":
                storeData.righter.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case "L":
                storeData.lefter.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case "W":
                storeData.uper.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            case "S":
                storeData.downer.paintIcon(this, g, snakeX[0], snakeY[0]);
                break;
            default:
                // Handle the default case if necessary
                break;
        }


        //蛇的身体长度会变
        for(int i=1;i<length;i++){
            storeData.bodyer.paintIcon(this,g,snakeX[i],snakeY[i]);
        }

        //画食物
        storeData.fooder.paintIcon(this,g,foodx,foody);
    }


    //2、绘制游戏界面
    public void printInterface(Graphics g){
        //清屏
        super.paintComponent(g);

        //设置背景的颜色
        this.setBackground(Color.BLACK);

        //绘制游戏区域
        g.fillRect(25,25,725,625);


        //画面板
        g.setColor(Color.WHITE); //设置画笔颜色
        g.setFont(new Font("微软雅黑",Font.BOLD,18)); //设置字体
        g.drawString("当前长度:",770,35);
        g.drawString(String.valueOf(length),770,70);
        g.drawString("当前分数:",770,105);
        g.drawString(String.valueOf(score),770,140);
        g.drawString("已玩时间:",770,175);

        if(sec<=9 && min<=9){
            g.drawString("0"+min+":0"+sec,770,210);
        }else if(sec<=9){
            g.drawString(min+":"+sec,770,210);
        }else if(min<=9){
            g.drawString("0"+min+":"+sec,770,210);
        }else{
            g.drawString(min+":"+sec,770,210);
        }


        g.drawString("最高分数:",770,545);
        g.drawString("No.1:"+arr[0],770,580);
        g.drawString("No.2:"+arr[1],770,615);
        g.drawString("No.3:"+arr[2],770,650);

    }


    //3、绘制游戏提示
    public void printRemind(Graphics g){
        //游戏提示
        if(!isStart){
            //画一个文字,String
            g.setColor(Color.WHITE); //设置画笔颜色
            g.setFont(new Font("微软雅黑",Font.BOLD,40)); //设置字体
            g.drawString("按下空格开始游戏！",220,330);
        }

        //失败提醒
        if(isFail){
            //画一个文字,String
            g.setColor(Color.RED); //设置画笔颜色
            g.setFont(new Font("微软雅黑",Font.BOLD,40)); //设置字体
            g.drawString("游戏失败",300,300);
            g.drawString("按下空格重新开始！",220,350);
        }
    }


    //附加方法
    //4、游戏开始，但是游戏没有失败
    public void startGame(){
        //右移
        for(int i=length-1;i>0;i--){
            snakeX[i]=snakeX[i-1];
            snakeY[i]=snakeY[i-1];
        }


        //通过控制方向让头部移动
        switch (fx) {
            case "R":
                snakeX[0] = snakeX[0] + 25;
                if (snakeX[0] > 725) {
                    snakeX[0] = 25;
                }
                break;
            case "L":
                snakeX[0] = snakeX[0] - 25;
                if (snakeX[0] < 25) {
                    snakeX[0] = 725;
                }
                break;
            case "W":
                snakeY[0] = snakeY[0] - 25;
                if (snakeY[0] < 25) {
                    snakeY[0] = 625;
                }
                break;
            case "S":
                snakeY[0] = snakeY[0] + 25;
                if (snakeY[0] > 625) {
                    snakeY[0] = 25;
                }
                break;
        }

        //如果小蛇的头和食物坐标重合了
        if(snakeX[0]==foodx && snakeY[0]==foody){
            //长度+1 & 分数+10
            length++;
            score=score+10;

            //重新生成食物
            foodx =25+25*r.nextInt(29);
            foody =25+25*r.nextInt(25);
        }

        //结束判断
        for(int i=1;i<length;i++){
            if (snakeX[0] == snakeX[i] && snakeY[0] == snakeY[i]) {
                isFail = true;
                break;
            }
        }

        //刷新界面
        repaint();
        times++;
        if(times==10){
            sec++;
            times=0;
            if(sec==60){
                sec=0;
                min++;
            }
        }
    }


    //5、游戏失败
    public void endGame(){
        array(arr);
        if(score>arr[0]){
            arr[2]=arr[1];
            arr[1]=arr[0];
            arr[0]=score;
        }else if(score<arr[0] && score>arr[1]){
            arr[2]=arr[1];
            arr[1]=score;
        }else if(score<arr[1] && score>arr[2]){
            arr[2]=score;
        }
        updateData(arr,arr123);
    }


    //6、数组排序：
    public static void array(int[] arr){

        for(int x=0;x<arr.length-1;x++){
            for(int i=0;i<arr.length-1-x;i++){
                if(arr[i]<arr[i+1]){
                    int temp=arr[i];
                    arr[i]=arr[i+1];
                    arr[i+1]=temp;
                }
            }
        }

    }


    //7、删除一条原有数据
    public static void delete(int No){

        Connection con=null;
        PreparedStatement st=null;

        try {
            //3、获取数据库对象
            con = JdbcUtils.getConnection();

            //区别
            //4、执行SQL语句
            //使用占位符代替参数
            String sql="delete from snakescore where No=?";

            //预编译，先写sql，不执行
            st=con.prepareStatement(sql);
            st.setInt(1,No);

            //执行
            st.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            //6、释放资源
            JdbcUtils.release(con,st,null);
        }
    }


    //8、插入一条新数据
    public static void insert(int No,int Score){

        Connection con=null;
        PreparedStatement st=null;

        try {
            //3、获取数据库对象
            con = JdbcUtils.getConnection();

            //区别
            //4、执行SQL语句
            //使用占位符代替参数
            String sql="insert into snakescore(No,Score) values(?,?)";

            //预编译，先写sql，不执行
            st=con.prepareStatement(sql);
            st.setInt(1,No);
            st.setInt(2,Score);

            //执行
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }finally{
            //6、释放资源
            JdbcUtils.release(con,st,null);
        }
    }


    //9、控制数据库操作
    public static void updateData(int[] arr,int[] arr123){
        for(int i=0;i<arr.length;i++){
            delete(arr123[i]);
            insert(arr123[i],arr[i]);
        }
    }


    //不用的父类方法
    //释放某个键
    @Override
    public void keyReleased(KeyEvent e) {
    }

    //键盘按下,弹起:敲击
    @Override
    public void keyTyped(KeyEvent e) {
    }
}


