
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
        alpinist = new Alpinist(new Point2D.Float(150,150));
        createScreenUpdate();
        addMouseListener(
            new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    System.out.println(e.getPoint());
                    alpinist.SetTarget(e.getPoint());
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
        Timer autoUpdate = new Timer(50, this );
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
//        if(Math.random()>0.8) newPose();
//        alpinist.handl.MoveFor();
//        alpinist.handr.MoveFor();
//        alpinist.footl.MoveFor();
//        alpinist.footr.MoveFor();
        alpinist.Move();
        repaint();
        //System.out.println("qw");
    }

    public class Alpinist{
        private Ellipse2D head;
        private Line2D body;
        private Lapa handl, handr, footl, footr;
        private Point2D target, direction=new Point2D.Double(0,0);

        private Alpinist(Point2D startPoint){
            body = new Line2D.Double(startPoint.getX(),startPoint.getY(),startPoint.getX(),startPoint.getY()+50);
            head = new Ellipse2D.Double(startPoint.getX()-5,startPoint.getY()-25,10,16);
            target = body.getP1();
            handl = new Lapa(body, true, false);
            handr = new Lapa(body, true, true);
            footl = new Lapa(body, false, false);
            footr = new Lapa(body, false, true);
        }

        public void Move(){
            Point2D curent = body.getP1();
            int length = 2;
            Point2D newPosition = curent;
            if(target.distance(curent)<length) {
                 newPosition = new Point2D.Double(target.getX(), target.getY());
            }else{
                if ((target.getX() - curent.getX()) != 0) {
                    double a = Math.atan2(target.getY() - curent.getY(), target.getX() - curent.getX());
                    //System.out.println(a);
                    direction = new Point2D.Double(length * Math.cos(a), length * Math.sin(a));
                    //if (target.getY() - curent.getY() < 0) length = -length;
                    //if (a < 0) length = -length;
                    newPosition = new Point2D.Double(curent.getX()+length * Math.cos(a), curent.getY()+length * Math.sin(a));
                }
            }
            body.setLine(newPosition.getX(),newPosition.getY(),newPosition.getX(),newPosition.getY()+50);
            head = new Ellipse2D.Double(newPosition.getX()-5,newPosition.getY()-25,10,16);

            moveHands();
        }

        private void moveHands() {
            handl.start.setLocation(body.getX1()-5, body.getY1());
            handr.start.setLocation(body.getX1()+5, body.getY1());
            footl.start.setLocation(body.getX2()-3, body.getY2()+3);
            footr.start.setLocation(body.getX2()+3, body.getY2()+3);
            if(!handl.tooFar())handl.getNewTarget(direction);
            if(!handr.tooFar())handr.getNewTarget(direction);
            if(!footl.tooFar())footl.getNewTarget(direction);
            if(!footr.tooFar())footr.getNewTarget(direction);
        }

        public void SetTarget(Point point){
            target = point;
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

        public void grab(Point2D.Double point) {
            Lapa nearest = footl;
            if(point.distance(footr.target)<point.distance(nearest.target)) {
                nearest = footr;
            }
            if(point.distance(handl.target)<point.distance(nearest.target)) {
                nearest = handl;
            }
            if(point.distance(handr.target)<point.distance(nearest.target)) {
                nearest = handr;
            }
            //nearest.MoveTo(point);
            nearest.target = point;
        }
    }

    public class Lapa{
        private int rootl=30, branchl=32;
        private float roots=6.0f, branchs=5.0f;
        private Line2D root, branch;
        private Point2D.Double start, begin, end, target;
        private boolean isHand, isLeft;

        public Lapa(Line2D body, boolean isHand, boolean isLeft){
            if(isHand && isLeft) start = new Point2D.Double((body.getX1()+5),body.getY1());
            if(isHand && !isLeft) start = new Point2D.Double((body.getX1()-5),body.getY1());
            if(!isHand && isLeft) start = new Point2D.Double(body.getX2()-3,body.getY2()+3);
            if(!isHand && !isLeft) start = new Point2D.Double(body.getX2()+3,body.getY2()+3);

            this.isHand = isHand;
            this.isLeft = isLeft;
            getNewTarget(new Point2D.Double(0,0));
            begin=target;
            MoveTo(target);
        }

        public void getNewTarget(Point2D direction){
            System.out.println(direction);
            double x=start.getY();//+direction.getY()+1;
            double y =start.getX();//+direction.getX()+1;
            if(isHand)  y = start.getY()-20-Math.random()*10+direction.getY()*8;
            else y = start.getY()+40-Math.random()*10+direction.getY()*8;

            if(isLeft) x=start.getX()+20+Math.random()*20+direction.getX()*8;
            else x = start.getX()-20-Math.random()*20+direction.getX()*8;

            target = new Point2D.Double(x,y);

        }

        public boolean tooFar(){
            Point2D endPoint = new Point2D.Double(branch.getX2()-start.getX(),branch.getY2()-start.getY());
            boolean result =
                    (
                        (isHand && endPoint.getY()<0 && endPoint.getY()>-40)||
                        (!isHand && endPoint.getY()>0&& endPoint.getY()<50)
                    ) &&
                    (
                        (!isLeft && endPoint.getX()<0)||
                        (isLeft && endPoint.getX()>0 )
                    )

                && start.distance(branch.getP2())<60;

            return result;
        }

        public void MoveTo(Point2D end){
            Double R = end.distance(start);
            Double p1 = Math.acos((end.getX()-start.getX())/R);
            Double p2 = Math.acos((Math.pow(R,2)+Math.pow(rootl,2)-Math.pow(branchl,2))/(2*R*rootl));
            if(end.getX()-start.getX()<0) p2 = -p2;
            if(end.getY()-start.getY()<0) p1 = -p1;
            if(!isHand) {p2 = -p2;}

            Double q1 = p1+p2; //plus minus for both sides
            Point2D.Double lokot = new Point2D.Double((rootl*Math.cos(q1))+start.getX(), (rootl*Math.sin(q1))+start.getY());

            root = new Line2D.Double(start,lokot);
            branch = new Line2D.Double(lokot.getX(), lokot.getY(), end.getX(), end.getY());
        }

        public void Calculate(){
            Double R = target.distance(start);
            Double p1 = Math.acos((target.getX()-start.getX())/R);
            Double p2 = Math.acos((Math.pow(R,2)+Math.pow(rootl,2)-Math.pow(branchl,2))/(2*R*rootl));
            if(target.getX()-start.getX()<0 ) p2 = -p2;
            if(target.getY()-start.getY()<0 ) p1 = -p1;
            if(!isHand) {p2 = -p2;}

            Double q1 = p1+p2; //plus minus for both sides
            Point2D.Double lokot = new Point2D.Double((rootl*Math.cos(q1))+start.getX(), (rootl*Math.sin(q1))+start.getY());

            root = new Line2D.Double(start,lokot);
            branch = new Line2D.Double(lokot.getX(), lokot.getY(), target.getX(), target.getY());

        }

        public void render(Graphics2D g) {
            Calculate(); //@todo add flag calculated
            g.setColor(Color.black);
            g.setStroke(new BasicStroke(roots, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.draw(root);
            g.setStroke(new BasicStroke(branchs, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g.draw(branch);
        }

        public void Move() {
            Point2D.Double curent = (Point2D.Double) branch.getP2();
            int length = 2;
            if(target.distance(curent)<length) {
                MoveTo(target);
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


