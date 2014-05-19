// Tutorial 20: ScreenSaver.java
// Program simulates screen saver by drawing random shapes.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ScreenSaver extends JFrame
{
    // JButton to clear drawingJPanel
    private JButton clearJButton;

    // DrawJPanel for displaying rectangles
    private javax.swing.JPanel drawingJPanel;

    // no-argument constructor
    public ScreenSaver()
    {
        this.setBackground(new Color(0,0,0));
        this.setUndecorated(true);
        this.setResizable(false);
        createUserInterface();
        GraphicsEnvironment.getLocalGraphicsEnvironment()
        .getDefaultScreenDevice()
        .setFullScreenWindow(this);
    }

    // create and position GUI components; register event handlers
    private void createUserInterface()
    {
        // get content pane for attaching GUI components
        Container contentPane = getContentPane();

        // enable explicit positioning of GUI components
        contentPane.setLayout( null );

        // set up clearJButton
        // set up drawingJPanel
        drawingJPanel = new javax.swing.JPanel();
        drawingJPanel.setBounds( 0, 0, 450, 450 );
        contentPane.add( drawingJPanel );

        // set properties of application's window
        setVisible( true );         // display window

    } // end method createUserInterface

    // reset drawingJPanel
    private void clearJButtonActionPerformed( ActionEvent event )
    {
        drawingJPanel.removeAll();

    } // end method clearJButtonActionPerformed

    // main method
    public static void main( String[] args )
    {
        ScreenSaver application = new ScreenSaver();
        application.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

    } // end method main

} // end class ScreenSaver
