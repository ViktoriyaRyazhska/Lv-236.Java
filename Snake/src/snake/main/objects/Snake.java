/*
*
* Snake
*
* v. 1.0
*
* 26 april 2017
*
* (c) Mykola Myndyk
*
*/
package snake.main.objects;
import snake.main.KeyBoard;
import snake.main.SnakeMain;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The <name>Snake</name> class represents a relevant game object
 * and is used for painting
 *
 * @autor Myndyk Mykola
 * @version 1.0
 * @since 2017-04-26
 */
public class Snake {

    /**
     * Direction of a <code>Snake</code> with initial value.
     */
    public int direction = 37;
    /**
     * List of <code>Point</code> which form up the <code>Snake</code>.
     */
    public List<Point> snakeList = new ArrayList<>();
    /**
     * Triggers when the <code>Snake</code> eats.
     */
    public boolean isEating = false;

    /**
     * Constructs a new <code>Snake</code> object
     * in given dimensions and of given length.
     * @param x - dimension x
     * @param y - dimension y
     * @param length - length of a <code>Snake</code>
     */
    public Snake(int x, int y, int length) {
        for (int i = 0; i < length; i++) {
            Point point = new Point(x + i, y);
            snakeList.add(point);
        }
    }

    /**
     * Method used to move a <code>Snake</code>
     * across the game area.
     */
    public void move() {
        int x = snakeList.get(0).posX;
        int y = snakeList.get(0).posY;
        //up
        if (direction == 38) y--;
        //down
        if (direction == 40) y++;
        //right
        if (direction == 39) x++;
        //left
        if (direction == 37) x--;

        //walls
        if (y > SnakeMain.height - 1) y = 0;
        if (y < 0) y = SnakeMain.height - 1;
        if (x > SnakeMain.width - 1) x = 0;
        if (x < 0) x = SnakeMain.width - 1;

        snakeList.add(0, new Point(x, y));
        if (!isEating) {
            snakeList.remove(snakeList.size() - 1);
        } else {
            isEating = false;
        }
    }

    /**
     * Method used to determine if the <code>Snake</code>
     * has crashed itself.
     * @return true if <code>Snake</code> crashed itself
     * @return false otherwise
     */
    public boolean selfCrashed() {
        for (int i = 1; i < snakeList.size(); i++) {
            int x = snakeList.get(i).posX;
            int y = snakeList.get(i).posY;
            if ((x == snakeList.get(0).posX) && (y == snakeList.get(0).posY)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method used to determine if the given dimensions are
     * inside the <code>Snake</code> object.
     * @param x - dimension x
     * @param y - dimension y
     * @return true if inside the <code>Snake</code>
     * @return false otherwise
     */
    public boolean insideSnake(int x, int y) {
        for (Point point : snakeList) {
            if ((point.posX == x) && (point.posY == y)) return true;
        }
        return false;
    }

    /**
     * Method used to determine if the provided <code>ArrayList</code>
     * of <code>Poison</code> objects intersect with <code>Snake</code>
     * @param pointList - <code>ArrayList</code> of <code>Poison</code> objects
     * @return true - if Poison is inside the Snake
     * @return false otherwise
     */
    public boolean insideSnake(ArrayList<Poison> pointList) {
        for (Point point : snakeList) {
            for (Point other : pointList) {
                if ((point.posX == other.posX) && (point.posY == other.posY)) return true;
            }
        }
        return false;
    }

    /**
     * Method used to paint the <code>Snake</code> object.
     * @param g - <code>Graphics</code> context.
     */
    public void paint(Graphics g) {

        //head
        int h1 = snakeList.get(0).posX;
        int h2 = snakeList.get(0).posY;
        g.setColor(SnakeMain.snakeHeadColor);
        g.fillRect(h1 * SnakeMain.scale + 4, h2 * SnakeMain.scale + 4, SnakeMain.scale - 10, SnakeMain.scale - 10);

        //body
        for (int i = 1; i < snakeList.size(); i++) {
            int x = snakeList.get(i).posX;
            int y = snakeList.get(i).posY;
            g.setColor(SnakeMain.snakeBodyColor);
            g.fillRect(x * SnakeMain.scale + 4, y * SnakeMain.scale + 4, SnakeMain.scale - 10, SnakeMain.scale - 10);
        }
    }


}
