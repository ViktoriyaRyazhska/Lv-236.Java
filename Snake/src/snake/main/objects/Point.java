/*
*
* Point
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

/**
 * The <name>Point</name> class represents a unit, used to build up a game field.
 *
 * @autor Myndyk Mykola
 * @version 1.0
 * @since 2017-04-26
 */
public class Point {

    /**
     * Dimensions of the <code>Point</code>.
     */
    public int posX;
    public int posY;

    /**
     * Constructs a <code>Point</code> for given dimensions.
     * @param x - dimension x.
     * @param y - dimension y.
     */
    public Point(int x, int y) {
        this.posX = x;
        this.posY = y;
    }

    /**
     * Constructs a <code>Point</code> for a <code>Snake</code>
     * at random position.
     * @param snake - <code>Snake</code> object.
     */
    Point(Snake snake) {
        setRandomPosition(snake);
    }

    /**
     * Sets random position for given <code>Snake</code>.
     * @param snake - <code>Snake</code> object.
     */
    public void setRandomPosition(Snake snake) {
        do {
            posX = Math.abs((int) (Math.random() * SnakeMain.height - 1));
            posY = Math.abs((int) (Math.random() * SnakeMain.width - 1));
        } while (snake.insideSnake(posX, posY));
    }

}
