// Tutorial 20: ScreenSaver.java
// Program simulates screen saver by drawing random shapes.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class ScreenSaver extends JPanel implements ActionListener
{
    private int pointsCount = 250;

    private MyPoint[] mypoints = new MyPoint[pointsCount];

    // no-argument constructor
    public ScreenSaver()
    {
        setOpaque(true);
        this.setBackground(Color.BLACK);
    }

    public void addPoints(){
        GraphicsDevice monitor = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.setSize(monitor.getDisplayMode().getWidth(), monitor.getDisplayMode().getHeight());
        mypoints = new MyPoint[pointsCount];

        //todo add image parser
        for(int i=0; i<pointsCount; i++) {
            mypoints[i] = new MyPoint(getSize());
        }
        createScreenUpdate();
    }

    public void paint(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2.setRenderingHints(rh);

        for(int j=0; j<pointsCount; j++){
            mypoints[j].render(g2);
        }

    }

    private void createScreenUpdate()
    {
        Timer autoUpdate = new Timer(40, this );
        autoUpdate.start();

    }

    // main method
    public static void main( String[] args )
    {
        //todo make a screensaver
        JFrame frame = new JFrame("TimerBasedAnimation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(300,400);
        ScreenSaver application = new ScreenSaver();
        frame.add(application);
        application.addPoints();
//        frame.setVisible(true);

        frame.setUndecorated(true);
        frame.setResizable(false);
        GraphicsEnvironment.getLocalGraphicsEnvironment()
        .getDefaultScreenDevice()
        .setFullScreenWindow(frame);


        //todo refacktor this
        MouseListener ml = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent mouseEvent) {
                System.exit(0);
            }

            @Override
            public void mousePressed(MouseEvent mouseEvent) { }

            @Override
            public void mouseReleased(MouseEvent mouseEvent) {}

            @Override
            public void mouseEntered(MouseEvent mouseEvent) {}

            @Override
            public void mouseExited(MouseEvent mouseEvent) {}
        };

        frame.addMouseListener(ml);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
          repaint();
    }

} // end class ScreenSaver

class MyPoint extends Line2D {

    private double x,y,vx,vy,slower=0.001;
    private Color c;

    private Dimension ssize;
    public MyPoint(Dimension size) {
        int x = size.width;
        int y = size.height;
        ssize=size;
        this.x= Math.random()*x;
        this.y= Math.random()*y;
        this.vx=0;
        this.vy=0;
        this.c = new Color((float)Math.random(), (float)Math.random(), (float)Math.random());
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
