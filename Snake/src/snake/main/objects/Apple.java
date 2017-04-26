/*
*
* Apple
*
* v. 1.0
*
* 26 april 2017
*
* (c) Mykola Myndyk
*
*/
package snake.main.objects;
import snake.main.SnakeMain;
import java.awt.*;

/**
 * The <name>Apple</name> class represents a relevant game object
 * and is used for painting it.
 *
 * @autor Myndyk Mykola
 * @version 1.0
 * @since 2017-04-26
 */
public class Apple extends Point {

    /**
     * Constructs a new <code>Apple</code>.
     * @param snake - <code>Snake</code> object.
     */
    public Apple(Snake snake) {
        super(snake);
    }

    /**
     * Method used to paint <code>Apple</code>.
     * @param g - <code>Graphics</code> context.
     */
    public void paint(Graphics g) {
        g.setColor(SnakeMain.appleColor);
        g.fillOval(posX * SnakeMain.scale + 4, posY * SnakeMain.scale + 4, SnakeMain.scale - 8, SnakeMain.scale - 8);
    }

}
