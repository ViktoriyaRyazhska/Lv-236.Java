/*
*
* KeyBoard
*
* v. 1.0
*
* 26 april 2017
*
* (c) Mykola Myndyk
*
*/
package snake.main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * The <name>KeyBoard</name> class is an extension of abstract
 * <code>KeyAdapter</code> class used to change <code>Snake</code>
 * direction according to relevant <code>KeyEvent</code>.
 *
 * @version 1.0
 * @autor Myndyk Mykola
 * @since 2017-04-26
 */
class KeyBoard extends KeyAdapter {

    /**
     * Key mappings
     */
    private final static int LEFT = 37;
    private final static int UP = 38;
    private final static int RIGHT = 39;
    private final static int DOWN = 40;

    /**
     * Method used to change direction of <code>Snake</code> according to
     * the pressed key.
     *
     * @param e - <code>KeyEvent</code>, which contains info about the key pressed.
     */
    public void keyPressed(KeyEvent e) {

        if ((e.getKeyCode() == UP) && (SnakeMain.snake.direction != DOWN)) SnakeMain.snake.direction = 38;
        if ((e.getKeyCode() == DOWN) && (SnakeMain.snake.direction != UP)) SnakeMain.snake.direction = 40;
        if ((e.getKeyCode() == LEFT) && (SnakeMain.snake.direction != RIGHT)) SnakeMain.snake.direction = 37;
        if ((e.getKeyCode() == RIGHT) && (SnakeMain.snake.direction != LEFT)) SnakeMain.snake.direction = 39;

    }


}
