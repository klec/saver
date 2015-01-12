import java.awt.*;

/**
 * Created by klec on 5/20/14.
 */
public class MyCircle extends java.awt.geom.Ellipse2D.Double {
//    protected double x;
//    protected double y;
    protected double vx;
    protected double vy;
    protected double slower=0.005;
    protected Color c;
    protected double size;

    public MyCircle(Point pos, Color c) {
        size=15;
        this.x =(pos.x-10)*size*1.4;
        this.y =(pos.y-10)*size*1.4;
        this.width=size;
        this.height=size;
        this.c = c;
    }

    public void revert(){
        this.vx=0-this.vx;
        this.vy=0-this.vy;
    }

    public void render(Graphics2D g) {
        g.setColor(this.c);
        g.fill(this);
        //move3();
    }

     public void initMovement(){
        this.vx=Math.random()*20+20;
        this.vy=Math.random()*6-3;
    }


}
