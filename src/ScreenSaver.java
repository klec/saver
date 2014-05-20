// Tutorial 20: ScreenSaver.java
// Program simulates screen saver by drawing random shapes.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScreenSaver extends JFrame implements ActionListener
{
    private MyPoint[] mypoints;

    // no-argument constructor
    public ScreenSaver()
    {
        this.getContentPane().setBackground(Color.BLACK);
//        this.setUndecorated(true);
        this.setResizable(false);
        this.setSize(300,400);
        this.setVisible(true);
//        GraphicsEnvironment.getLocalGraphicsEnvironment()
//        .getDefaultScreenDevice()
//        .setFullScreenWindow(this);
    }

    public void addPoints(){
        mypoints = new MyPoint[10];
        for(int i=0; i<10; i++) {
            mypoints[i] = new MyPoint(getSize());
            this.getContentPane().add(mypoints[i]);
        }
        createScreenUpdate();
    }

    // create and position GUI components; register event handlers
    private void createScreenUpdate()
    {
        Timer autoUpdate = new Timer(100, this );
        autoUpdate.start();

    } // end method createScreenUpdate

    // main method
    public static void main( String[] args )
    {
        ScreenSaver application = new ScreenSaver();
        application.addPoints();
        application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

    } // end method main

    @Override
    public void actionPerformed(ActionEvent e) {
          this.repaint();
//        for(int j=0; j<10; j++){
//            mypoints[j].repaint();
//        }
    }

} // end class ScreenSaver

class MyPoint extends JComponent {

    private int x,y,vx,vy;
    private Dimension ssize;
    public MyPoint(Dimension size) {
        setSize(1,1);
        int x = size.width;
        int y = size.height;
        ssize=size;
        this.x=(int) (Math.random()*300);
        this.y=(int) (Math.random()*400);
        vx=0;
        vy=0;
    }

    public void paint(Graphics g) {
        if(this.x>ssize.width/2)
            vx--;
        else
            vx++;

        if(this.y>ssize.height/2)
            vy--;
        else
            vy++;
        //setLocation(getX()+1, getY());
        this.x=this.x+vx;
        this.y=this.y+vy;
        //System.out.println(x+"_"+vx+"_"+ssize.width/2);
        g.setColor(Color.BLUE);
        g.drawLine(this.x, this.y, this.x+vx, this.y+vy);

    }
}
