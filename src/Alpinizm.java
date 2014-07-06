// Tutorial 20: ScreenSaver.java
// Program simulates screen saver by drawing random shapes.

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

        addMouseListener(
            new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println(e.getPoint());
                    alpinist.handl.MoveTo(alpinist.body.getP1(), new Point(e.getX()-100, e.getY()-100));
                    repaint();
                }
            }
        );
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
            handl.MoveTo(body.getP1(), new Point2D.Double(40, -10));
        }
        public void render(Graphics2D g) {
            g.setColor(Color.black);
            handl.render(g);
            g.draw(body);
        }
    }

    public class Lapa{
        private int rootl=30, branchl=30;
        private Line2D root, branch;
        private boolean down;

        public Lapa(boolean dir){
            down = dir;
        }

        public void MoveTo(Point2D start, Point2D end){
            Double R = end.distance(0, 0);
            Double p1 = Math.acos(end.getX()/R);
            Double p2 = Math.acos((Math.pow(R,2)+Math.pow(rootl,2)-Math.pow(branchl,2))/(2*R*rootl));
            Double q1 = -p1+p2; //plus minus for both sides
            Point2D.Double lokot = new Point2D.Double((rootl*Math.cos(q1))+start.getX(), (rootl*Math.sin(q1))+start.getY());

            root = new Line2D.Double(start,lokot);
            branch = new Line2D.Double(lokot, new Point2D.Double(start.getX()+end.getX(),start.getY()+end.getY()));
        }

        public void render(Graphics2D g) {
            g.setColor(Color.black);
            g.draw(root);
            g.draw(branch);
        }
    }
} // end class ScreenSaver


