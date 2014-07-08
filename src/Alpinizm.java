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
        createScreenUpdate();
        addMouseListener(
            new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println(e.getPoint());
                    alpinist.grab(e.getPoint());
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
        Timer autoUpdate = new Timer(20, this );
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

    private void newPose(){
        alpinist.handl.target = new Point2D.Double(-Math.random() * 40 - 20, Math.random() * 80 - 40);
        alpinist.handr.target = new Point2D.Double( Math.random() * 40 + 20, Math.random() * 80 - 40);
        if(Math.random()>0.6) {
            alpinist.footl.target = new Point2D.Double(-Math.random() * 20, Math.random() * 5 + 52);
            alpinist.footr.target = new Point2D.Double(Math.random() * 20, Math.random() * 5 + 52);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(Math.random()>0.8) newPose();
        alpinist.handl.MoveFor();
        alpinist.handr.MoveFor();
        alpinist.footl.MoveFor();
        alpinist.footr.MoveFor();
        repaint();
        //System.out.println("qw");
    }

    public class Alpinist{
        private Ellipse2D head;
        private Line2D body;
        private Lapa handl, handr, footl, footr;

        private Alpinist(){
            body = new Line2D.Double(150,150,150,200);
            head = new Ellipse2D.Double(145,125,10,16);
            handl = new Lapa(new Point2D.Double(body.getX1()-5,body.getY1()), true, false);
            handr = new Lapa(new Point2D.Double(body.getX1()+5,body.getY1()), true, true);
            footl = new Lapa(new Point2D.Double(body.getX2()-3,body.getY2()+3), false, false);
            footr = new Lapa(new Point2D.Double(body.getX2()+3,body.getY2()+3), false, true);
        }
        public void render(Graphics2D g) {
            g.setColor(Color.black);
            handl.render(g);
            handr.render(g);
            footl.render(g);
            footr.render(g);
            g.setStroke(new BasicStroke(10.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.draw(body);
            g.draw(head);
        }

        public void grab(Point point) {
            Lapa nearest = footl;
            if(point.distance(footr.branch.getP2())<point.distance(nearest.branch.getP2())) {
                nearest = footr;
            }
            if(point.distance(handl.branch.getP2())<point.distance(nearest.branch.getP2())) {
                nearest = handl;
            }
            if(point.distance(handr.branch.getP2())<point.distance(nearest.branch.getP2())) {
                nearest = handr;
            }
            //nearest.MoveTo(point);
            nearest.target = new Point2D.Double(point.getX()-nearest.start.getX(), point.getY()-nearest.start.getY());
        }
    }

    public class Lapa{
        private int rootl=30, branchl=32;
        private float roots=6.0f, branchs=5.0f;
        private Line2D root, branch;
        private Point2D start, target;
        private boolean isHand, isLeft;

        public Lapa(Point2D start, boolean isHand, boolean isLeft){
            this.isHand = isHand;
            this.isLeft = isLeft;
            int x,y;
            if(isHand)  y = -10;
            else y= 40;

            if(isLeft) x=20;
            else x = -20;
            target = new Point2D.Double(x,y);
            this.start=start;
            MoveTo(start, target);
        }

        public void MoveTo(Point2D end){
            Point2D.Double relativeEnd = new Point2D.Double(end.getX()-start.getX(),end.getY()-start.getY());
            MoveTo(start, relativeEnd);
        }

        public void MoveTo(Point2D start, Point2D end){
            Double R = end.distance(0, 0);
            Double p1 = Math.acos(end.getX()/R);
            Double p2 = Math.acos((Math.pow(R,2)+Math.pow(rootl,2)-Math.pow(branchl,2))/(2*R*rootl));
            if(end.getX()<0 ) p2 = -p2;
            if(end.getY()<0 ) p1 = -p1;
            if(!isHand) {p2 = -p2;}

            Double q1 = p1+p2; //plus minus for both sides
            Point2D.Double lokot = new Point2D.Double((rootl*Math.cos(q1))+start.getX(), (rootl*Math.sin(q1))+start.getY());

            root = new Line2D.Double(start,lokot);
            branch = new Line2D.Double(lokot.getX(), lokot.getY(), start.getX()+end.getX(),start.getY()+end.getY());
        }

        public void render(Graphics2D g) {
            g.setColor(Color.black);
            g.setStroke(new BasicStroke(roots, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.draw(root);
            g.setStroke(new BasicStroke(branchs, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.draw(branch);
        }

        public void MoveFor() {
            Point2D.Double curent = new Point2D.Double(-start.getX() + branch.getX2(), - start.getY() + branch.getY2());
            int length = 2;
            if(target.distance(curent)<length) {
                MoveTo(start, target);
            }else{
                if ((target.getX() - curent.getX()) != 0) {
                    double a = Math.atan((target.getY() - curent.getY()) / (target.getX() - curent.getX()));
                    if (target.getY() + start.getY() - branch.getY2() < 0) length = -length;
                    if (a < 0) length = -length;
                    MoveFor(new Point2D.Double(length * Math.cos(a), length * Math.sin(a)));
                }
            }
        }

        public void MoveFor(Point2D.Double point) {
            MoveTo(new Point2D.Double(branch.getX2()+point.getX(), branch.getY2()+point.getY()));
        }
    }
} // end class ScreenSaver


