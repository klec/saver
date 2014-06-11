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
    protected double slower=0.005;
    protected Color c;
    protected Dimension ssize;

    public MyPoint(Dimension size,Point pos, Color c) {

        ssize = new Dimension((int)size.getWidth()/2, (int)size.getHeight()/2);
        this.x= ssize.width-165+pos.getX();
        this.y= ssize.height-80+pos.getY();

        this.c = c;
        //ssize=size;
    }

    public MyPoint(Dimension size, Color c) {
        int x = size.width;
        int y = size.height;
        //ssize=size;
        ssize = new Dimension((int)size.getWidth()/2, (int)size.getHeight()/2);
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
        g.setColor(this.c);
        g.draw(this);
        //move3();
    }

    private void move1() {
        //todo make it revertable
        if(vx == 0)
            this.vx=Math.random()*80-40;
        if(vy == 0)
            this.vy=Math.random()*60-30;

        this.x=this.x+this.vx;
        this.y=this.y+this.vy;

        if(this.x>ssize.width)
            this.vx--;
        else
            this.vx++;

        if(this.y>ssize.height)
            this.vy--;
        else
            this.vy++;
    }

    private void move2() {
        if(vx == 0)
            this.vx=Math.random()*80-40;
        if(vy == 0)
            this.vy=Math.random()*40-40;

        if(this.y>ssize.height+80){
            this.vy=-vy*0.5;
            this.vx=vx*0.5;
        }else{
            this.vy++;}
        vx = vx*(1-slower);
        vy = vy*(1-slower);
        this.x=this.x+this.vx;
        this.y=this.y+this.vy;
    }

    public void move3(){

        if(this.x>ssize.width)
            this.vx--;
        else
            this.vx++;

        if(this.y>ssize.height)
            this.vy--;
        else
            this.vy++;
        vx = vx*(1-slower);
        vy = vy*(1-slower);
        this.x=this.x+this.vx;
        this.y=this.y+this.vy;
    }

    public void init(){
        this.vx=Math.random()*20+20;
        this.vy=Math.random()*6-3;
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
        return x;
//        return x+vx;
    }

    @Override
    public double getY2() {
        return y;
//        return y+vy;
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
