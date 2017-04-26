/*
*
* SnakeMain
*
* v. 1.0
*
* 26 april 2017
*
* (c) Mykola Myndyk
*
*/
package snake.main;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import snake.main.objects.*;

/**
 * The <name>SnakeMain</name> class represents the main window of Snake game.
 * The window is a <code>JPanel</code> with an <code>ActionListener</code>
 * which tracks key pressings.
 *
 * @autor Myndyk Mykola
 * @version 1.0
 * @since 2017-04-26
 */
public class SnakeMain extends JPanel implements ActionListener {

    /**
     * The color pink.  In the default sRGB space.
     */
    public static Color backgroundColor = Color.pink;
    /**
     * The color cyan.  In the default sRGB space.
     */
    public static Color snakeBodyColor = Color.CYAN;
    /**
     * The color dark gray.  In the default sRGB space.
     */
    public static Color snakeHeadColor = Color.darkGray;
    /**
     * The color red.  In the default sRGB space.
     */
    public static Color poisonColor = Color.red;
    /**
     * The color yellow.  In the default sRGB space.
     */
    public static Color appleColor = Color.yellow;
    /**
     * Root JFrame.
     */
    public static JFrame jFrame;
    /**
     * Scale measure used for displaying.
     */
    public static final int scale = 32;
    /**
     * Width measure used for displaying.
     */
    public static final int width = 20;
    /**
     * Height measure used for displaying.
     */
    public static final int height = 20;
    /**
     * Snake speed.
     */
    public static int speed = 15;
    /**
     * Initial poison count for Snake.
     */
    public static int poisonCount = 0;
    /**
     * Keylistener - interface for receiving keyboard events.
     */
    public static KeyListener kl = new KeyBoard();
    /**
     * Snake game object.
     */
    public static Snake snake = new Snake(5, 6, 5);
    /**
     * Timer - used for firing events with certain intervals.
     */
    public Timer timer = new Timer(1000 / speed, this);
    /**
     * Apple game object.
     */
    public Apple apple = new Apple(snake);
    /**
     * Collection containing a list of Poison objects to paint.
     */
    public ArrayList<Poison> poisonList = new ArrayList<>();

    /**
     * Constructs a JPanel with added KeyListener, and starts Timer.
     */
    public SnakeMain() {
        addKeyListener(kl);
        setFocusable(true);
        timer.start();
    }

    /**
     * Represents a main() method of the application.
     * Creates a root <code>JFrame</code>, configures it and adds a
     * <code>SnakeMain JPanel</code>
     * as its component.
     * @param args - not used.
     */
    public static void main(String[] args) {

        //name of window
        jFrame = new JFrame("SCORE" + " : " + snake.snakeList.size());
        //operation when close
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //sizing
        jFrame.setSize(scale * width + 7, scale * height + 30);
        //important option just to make our window visible
        jFrame.setVisible(true);
        //do not allow nobody to resize windiw
        jFrame.setResizable(false);
        //our window will be in the center of the screen
        jFrame.setLocationRelativeTo(null);


        jFrame.add(new SnakeMain());

    }

    /**
     * Method used to paint objects on JFrame.
     * @param g <code>Graphics</code> - graphic context.
     */
    public void paint(Graphics g) {

        //background
        g.setColor(backgroundColor);
        g.fillRect(0, 0, scale * width, scale * height);

        apple.paint(g);

        for (Poison poison : poisonList) poison.paint(g);

        snake.paint(g);
    }

    /**
     * Method invoked when <code>ActionEvent</code> (key pressing)
     * occurs.
     * Also moves the <code>Snake</code> and tracks interactions
     * with <code>Apple</code> and <code>Poison</code> objects.
     * Repaints the <code>Snake</code> when its dead.
     * @param e - <code>ActionEvent</code> (key pressing).
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        //moves the snake
        snake.move();
        //counts Poisons
        if (poisonCount >= 3) {
            poisonList.add(new Poison(snake));
            poisonCount = 0;
        }
        //checks if Snake eats Apple
        if (snake.insideSnake(apple.posX, apple.posY)) {
            do {
                apple.setRandomPosition(snake);
            } while (snake.insideSnake(apple.posX, apple.posY));
            snake.isEating = true;
            jFrame.setTitle("SCORE" + " : " + (snake.snakeList.size() + 1));
            poisonCount++;
        }
        //checks if Snake is crashed or poisoned
        if (snake.selfCrashed() || snake.insideSnake(poisonList)) {
            timer.stop();
            JOptionPane.showMessageDialog(null, "GAME OVER, TRY AGAIN?");
            snake = new Snake(5, 6, 5);
            apple.setRandomPosition(snake);
            poisonList = new ArrayList<>();
            timer.start();
        }
        //repaints the Snake.
        repaint();
    }
}
