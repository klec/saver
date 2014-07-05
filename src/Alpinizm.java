// Tutorial 20: ScreenSaver.java
// Program simulates screen saver by drawing random shapes.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class Alpinizm extends JPanel implements ActionListener
{
    private int ri = 0;
    private Alpinist alpinist;

    // no-argument constructor
    public Alpinizm(){
        setOpaque(true);
        this.setBackground(Color.GRAY);
        alpinist = new Alpinist();
    }

    public void paint(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
        RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g2.setRenderingHints(rh);
        alpinist.render(g2);
    }

    private void createScreenUpdate()
    {
        Timer autoUpdate = new Timer(5, this );
        autoUpdate.setInitialDelay(2000);
        autoUpdate.start();

    }

    // main method
    public static void main( String[] args )
    {
        JFrame frame = new JFrame("TimerBasedAnimation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,400);
        Alpinizm application = new Alpinizm();
        frame.add(application);
        frame.setVisible(true);

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

    public class Alpinist{
        private Ellipse2D head;
        private Line2D body;
        private Lapa handl, handr, footl, footr;

        private Alpinist(){
            body = new Line2D.Double(100,100,100,150);
            handl = new Lapa(true);
            handl.MoveTo(new Point2D.Double(20, 10));
        }
        public void render(Graphics2D g) {
            g.setColor(Color.black);
            handl.render(g);
            //g.draw(foots);
        }
    }

    public class Lapa{
        private int rootl=40, branchl=35;
        private Line2D root, branch;
        private boolean down;

        public Lapa(boolean dir){
            down = dir;
        }

        public void MoveTo(Point2D.Double vect){
            Double R = vect.distance(0,0);
            Double p1 = Math.asin(vect.getX()/R);
            Double p2 = Math.asin((Math.pow(R,2)+Math.pow(rootl,2)-Math.pow(branchl,2))/(2*R*branchl));
            Double p3 = Math.pow(R,2)+Math.pow(rootl,2)-Math.pow(branchl,2);
            Double q1 = Math.PI-p1-p2;
            Double x1 = rootl*Math.cos(q1);
            Double y1 = rootl*Math.sin(q1);
            root = new Line2D.Double(0,0,x1,y1);
            branch = new Line2D.Double(x1,y1,vect.getX(),vect.getY());
        }

        public void render(Graphics2D g) {
            g.setColor(Color.black);
            g.draw(root);
            g.draw(branch);
        }
    }
} // end class ScreenSaver


