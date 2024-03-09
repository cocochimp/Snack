package snakeGame;

import javax.swing.*;
import java.net.URL;

public class storeData {
    //头部:
    public static URL uphead=storeData.class.getResource("/resource/up.png");
    public static ImageIcon uper=new ImageIcon(uphead);

    public static URL downhead=storeData.class.getResource("/resource/down.png");
    public static ImageIcon downer=new ImageIcon(downhead);

    public static URL lefthead=storeData.class.getResource("/resource/left.png");
    public static ImageIcon lefter=new ImageIcon(lefthead);

    public static URL righthead=storeData.class.getResource("/resource/right.png");
    public static ImageIcon righter=new ImageIcon(righthead);

    //身体:
    public static URL body=storeData.class.getResource("/resource/body.png");
    public static ImageIcon bodyer=new ImageIcon(body);

    //食物:
    public static URL food=storeData.class.getResource("/resource/food.png");
    public static ImageIcon fooder=new ImageIcon(food);
}
