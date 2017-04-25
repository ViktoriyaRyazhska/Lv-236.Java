package snake.main.objects;

import snake.main.SnakeMain;

import java.awt.*;

/**
 * Created by Kolja on 20.03.2017.
 */
public class Poison extends Point{

    public Poison(Snake snake){
        super(snake);

    }

    public void paint(Graphics g){
        g.setColor(SnakeMain.poisonColor);
        g.fillRect(posX*SnakeMain.scale+4, posY*SnakeMain.scale+4, SnakeMain.scale-10, SnakeMain.scale-10);
    }

}
