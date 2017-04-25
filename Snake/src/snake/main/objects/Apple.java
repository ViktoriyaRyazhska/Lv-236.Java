package snake.main.objects;

import snake.main.SnakeMain;

import java.awt.*;

/**
 * Created by Kolja on 14.03.2017.
 */
public class Apple extends Point{

    public Apple(Snake snake){
        super(snake);
    }

    public void paint(Graphics g){
        g.setColor(SnakeMain.appleColor);
        g.fillOval(posX*SnakeMain.scale+4, posY*SnakeMain.scale+4, SnakeMain.scale-8, SnakeMain.scale-8);
    }

}
