import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by klec on 6/12/14.
 */
public class Logo extends JPanel{
    private ArrayList<Shape> elements;

     @Override
    public void paint(Graphics g) {
         graph= (Graphics2D)g;
        super.paint(g);
        doDrawing(g);
    }

    private Graphics2D graph;

    public Logo(){
        setOpaque(true);
        this.setBackground(Color.BLACK);
        elements= new ArrayList<Shape>();

        elements.add(new Arc2D.Double(40, 111, 20, 14, 10, 250, Arc2D.OPEN)); //s
        elements.add(new Arc2D.Double(40, 125, 20, 16, 200, 240, Arc2D.OPEN));//s
        elements.add(new Line2D.Double(67, 110, 67, 157));                    //p
        elements.add(new Arc2D.Double(67, 111, 30, 30, 0, 360,  Arc2D.OPEN)); //p
        elements.add(new Arc2D.Double(105, 110, 28, 31, 0, 315, Arc2D.OPEN)); //e
        elements.add(new Line2D.Double(105, 125, 133, 125));                  //e
        elements.add(new Line2D.Double(141, 110, 141, 140));                  //r
        elements.add(new Arc2D.Double(142, 111, 22, 25, 80, 90, Arc2D.OPEN));//r
        elements.add(new Arc2D.Double(158, 111, 30, 30, 0, 360,  Arc2D.OPEN));//o
        elements.add(new Line2D.Double(200, 102, 200, 135));                  //t
        elements.add(new Line2D.Double(192, 111, 210, 111));                  //t
        elements.add(new Arc2D.Double(200, 129, 15, 12, 180, 110,Arc2D.OPEN));//t
        elements.add(new Arc2D.Double(215, 111, 28, 31, 5, 315, Arc2D.OPEN)); //e
        elements.add(new Line2D.Double(215, 125, 243, 125));                  //e
        elements.add(new Arc2D.Double(250, 111, 28, 31, 45, 270, Arc2D.OPEN));//c
        elements.add(new Line2D.Double(282, 102, 282, 140));                  //k
        elements.add(new Line2D.Double(287, 125, 300, 112));                  //k
        elements.add(new Line2D.Double(302, 140, 287, 125));                  //k
    }

    public void doDrawing(Graphics g)
    {
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File(getClass().getResource("speroteck_logo.png").getFile()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //g.drawImage(img, 10,10, null);

        for (Shape element: elements){
            String s = element.getClass().getName();
            if (s.equals("java.awt.geom.Line2D$Double")) {
                drawLine(element);

            } else if (s.equals("java.awt.geom.Oval2D$Double")) {
                drawLine(element);

            } else {
                drawLine(element);

            }
        }
    }

    public void addElement(Shape element){
        elements.add(element);
    }

    private void drawLine(Shape line){
        //this.graph.drawLine(line.getX1(),line.getY1(),line.getX2(),line.getY2());
        this.graph.setStroke(new BasicStroke(5));
        this.graph.draw(line);
    }
}
