package snake.main.objects;

import snake.main.KeyBoard;
import snake.main.SnakeMain;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Kolja on 13.03.2017.
 */
public class Snake {

    public int direction = 37;
    public List<Point> snakeList = new ArrayList<>();
    public boolean isEating=false;

    public Snake(int x, int y, int length){
        for(int i = 0; i< length; i++){
            Point point = new Point(x+i,y);
            snakeList.add(point);
        }
    }

    public void move(){
       int x = snakeList.get(0).posX;
       int y = snakeList.get(0).posY;
        //up
        if(direction==38) y-- ;
        //down
        if(direction==40) y++ ;
        //right
        if(direction==39) x++ ;
        //left
        if(direction==37) x-- ;

        //walls
        if(y> SnakeMain.height-1) y = 0;
        if(y< 0) y = SnakeMain.height-1;
        if(x> SnakeMain.width-1) x = 0;
        if(x< 0) x = SnakeMain.width-1;

        snakeList.add(0,new Point(x,y));
        if(!isEating) {
            snakeList.remove(snakeList.size() - 1);
        }else{
            isEating=false;
        }
    }


    public boolean selfCrashed(){
        for(int i = 1; i<snakeList.size(); i++){
            int x = snakeList.get(i).posX;
            int y = snakeList.get(i).posY;
            if((x==snakeList.get(0).posX)&&(y==snakeList.get(0).posY)){
                return true;
            }
        }
        return false;
    }

    public boolean insideSnake(int x, int y){
        for(Point point : snakeList){
            if((point.posX==x)&&(point.posY==y)) return true;
        }
        return false;
    }

    public boolean insideSnake(ArrayList<Poison> pointList){
        for(Point point : snakeList){
            for(Point other : pointList){
                if((point.posX==other.posX)&&(point.posY==other.posY))return true;
            }
        }
        return false;
    }


    public void paint(Graphics g){

        //head
        int h1 = snakeList.get(0).posX;
        int h2 = snakeList.get(0).posY;
        g.setColor(SnakeMain.snakeHeadColor);
        g.fillRect(h1*SnakeMain.scale+4, h2*SnakeMain.scale+4, SnakeMain.scale-10, SnakeMain.scale-10);

        //body
        for(int i = 1; i<snakeList.size();i++){
            int x = snakeList.get(i).posX;
            int y = snakeList.get(i).posY;
            g.setColor(SnakeMain.snakeBodyColor);
            g.fillRect(x*SnakeMain.scale+4, y*SnakeMain.scale+4, SnakeMain.scale-10, SnakeMain.scale-10);
        }
    }


}
