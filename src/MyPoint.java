import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by klec on 5/20/14.
 */
public class MyPoint extends Line2D {
    protected double x;
    protected double y;
    protected double vx;
    protected double vy;
    protected double slower=0.001;
    protected Color c;
    protected Dimension ssize;

    public MyPoint(Dimension size,Point pos, Color c) {

        this.x= size.width/2+pos.getX();
        this.y= size.height/2+pos.getY();
        this.vx=Math.random()*20-10;
        this.vy=Math.random()*14-7;
        this.c = c;
        ssize=size;
    }

    public MyPoint(Dimension size, Color c) {
        int x = size.width;
        int y = size.height;
        ssize=size;
        this.x= Math.random()*x;
        this.y= Math.random()*y;
        this.vx=0;
        this.vy=0;
        this.c = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());

    }

    public void revert(){
        this.vx=0-this.vx;
        this.vy=0-this.vy;
    }

    public void render(Graphics2D g) {

        //todo refacktor this
        if(this.x>ssize.width/2)
            this.vx--;
        else
            this.vx++;

        if(this.y>ssize.height/2)
            this.vy--;
        else
            this.vy++;
        vx = vx*(1-slower);
        vy = vy*(1-slower);
        this.x=this.x+this.vx;
        this.y=this.y+this.vy;
        g.setColor(this.c);
        g.draw(this);

    }

    //todo refacktor this (change a type of Line)
    @Override
    public double getX1() {
        return x;
    }

    @Override
    public double getY1() {
        return y;
    }

    @Override
    public Point2D getP1() {
        return null;
    }

    @Override
    public double getX2() {
        return x+vx;
    }

    @Override
    public double getY2() {
        return y+vy;
    }

    @Override
    public Point2D getP2() {
        return null;
    }

    @Override
    public void setLine(double v, double v2, double v3, double v4) {
    }

    @Override
    public Rectangle2D getBounds2D() {
        return null;
    }
}
