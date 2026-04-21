import java.awt.*;

public class Alien {
    public String name;
    public int xpos;
    public int ypos;
    public int dx;
    public int dy;
    public int width;
    public int height;
    public Rectangle hitbox;
    public boolean isAlive;
    public boolean isCrashing;

    public Alien(int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;
        dx = 5;
        dy = 5;
        width = 85;
        height = 85;
        isAlive = false;
        isCrashing = false;
        hitbox = new Rectangle(xpos, ypos, width, height);
        //constructor
    }
    public void move(){
        if(xpos>=1000-width){
            xpos = 0;
        }
        if(xpos<=0){
            xpos=999-width;
        }
        if(ypos<=0){
            ypos=700-height;
        }
        if(ypos>=750){
            ypos=1;
        }
        xpos = xpos + dx;
        ypos = ypos + dy;
        hitbox = new Rectangle(xpos,ypos,width,height);
    }
}