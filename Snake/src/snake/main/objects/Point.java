package snake.main.objects;

import snake.main.SnakeMain;

import java.awt.*;

/**
 * Created by Kolja on 20.03.2017.
 */
public class Point {

    public int posX;
    public int posY;

    public Point(int x, int y){
        this.posX = x;
        this.posY = y;
    }

    public Point(Snake snake){
        setRandomPosition(snake);
    }

    public void setRandomPosition(Snake snake){
        do {
            posX = Math.abs((int) (Math.random() * SnakeMain.height - 1));
            posY = Math.abs((int) (Math.random() * SnakeMain.width - 1));
        }while(snake.insideSnake(posX,posY));
    }



}
