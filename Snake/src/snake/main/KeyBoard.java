package snake.main;

import snake.main.objects.Snake;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by Kolja on 14.03.2017.
 */
public class KeyBoard extends KeyAdapter {

    final static int LEFT = 37;
    final static int UP = 38;
    final static int RIGHT = 39;
    final static int DOWN = 40;

    public void keyPressed(KeyEvent e){

        if((e.getKeyCode() == UP)&&(SnakeMain.snake.direction!=DOWN)) SnakeMain.snake.direction = 38;
        if((e.getKeyCode() == DOWN)&&(SnakeMain.snake.direction!=UP)) SnakeMain.snake.direction = 40;
        if((e.getKeyCode() == LEFT)&&(SnakeMain.snake.direction!=RIGHT)) SnakeMain.snake.direction = 37;
        if((e.getKeyCode() == RIGHT)&&(SnakeMain.snake.direction!=LEFT)) SnakeMain.snake.direction = 39;

    }


}
