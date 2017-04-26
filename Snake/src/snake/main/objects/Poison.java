/*
*
* Poison
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
 * The <name>Poison</name> class represents a relevant game object
 * and is used for painting it.
 *
 * @autor Myndyk Mykola
 * @version 1.0
 * @since 2017-04-26
 */
public class Poison extends Point {

    /**
     * Constructs a new <code>Snake</code>.
     * @param snake - <code>Snake</code> object.
     */
    public Poison(Snake snake) {
        super(snake);

    }

    /**
     * Method used to paint <code>Poison</code>.
     * @param g - <code>Graphics</code> context.
     */
    public void paint(Graphics g) {
        g.setColor(SnakeMain.poisonColor);
        g.fillRect(posX * SnakeMain.scale + 4, posY * SnakeMain.scale + 4, SnakeMain.scale - 10, SnakeMain.scale - 10);
    }

}
