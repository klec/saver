// Tutorial 20: ScreenSaver.java
// Program simulates screen saver by drawing random shapes.

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ScreenSaver extends JPanel implements ActionListener
{
    private int ri = 0;
    private ArrayList<MyPoint> mypoints = new ArrayList();

    // no-argument constructor
    public ScreenSaver()
    {
        setOpaque(true);
        this.setBackground(Color.BLACK);
        addPoints();
    }

    private void addPoints(){
        GraphicsDevice monitor = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        this.setSize(monitor.getDisplayMode().getWidth(), monitor.getDisplayMode().getHeight());
        //mypoints = new MyPoint[pointsCount];

        try {
            BufferedImage img = ImageIO.read(new File(getClass().getResource("speroteck_logo.png").getFile()));
            for(int i = 1; i < img.getHeight(); i+=2){
                for(int j = 1; j < img.getWidth(); j+=2){
                    int rgb = img.getRGB(j, i);
                    if(rgb!=16777215){
                        Color c = new Color(rgb);
                        mypoints.add(new MyPoint(getSize(), new Point(j, i), c));
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
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



        ri+=10;
//        if(ri==5){
//            for(MyPoint p: mypoints){
//                p.revert();
//                ri=-1;
//            }
//        }else{

            for(MyPoint p: mypoints){
                if(p.x<700+ri&&p.vx==0 && p.vy==0){
                    p.init();
                }
                if(p.vx!=0 || p.vy!=0){
//                    p.move3();
                }

                p.render(g2);
            }
//        }

    }

    private void createScreenUpdate()
    {
        Timer autoUpdate = new Timer(5, this );
        autoUpdate.setInitialDelay(8000);
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
        Logo logo = new Logo();
        frame.add(logo);
        frame.add(application);

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


