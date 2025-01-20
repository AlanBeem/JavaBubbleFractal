import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * FractalDrawing is a simple class that creates a window and draws on it, it is designed to observe a FractalGenerator
 */
public class FractalDrawing extends JFrame implements Observer {
    private ArrayList<FractalElement> elements;
    /**
     * subject is a reference needed for the Observer design pattern and Subject is an interface
     */
    private Subject subject;
    /**
     * drawing_panel holds an instance of a private inner class DrawingPanel that handles the drawing of the fractal
     */
    private DrawingPanel drawing_panel;

    /**
     * FractalDrawing(Subject) is the constructor for FractalDrawing, and importantly takes a Subject as a parameter,
     * as part of the Observer design pattern. This handles the task of setting up the drawing panel.
     * @param subject the Subject on which to call getData (a FractalGenerator, in this implementation)
     */
    public FractalDrawing(Subject subject) {
        // set up Drawing to be an Observer of Subject
        this.subject = subject;
        this.subject.attach(this);
        int height_width = 800;
        setLocation(350,0);
        setSize(height_width,height_width); // using magic numbers here and in Generator
        setTitle("Fractal Drawing - Project 4, CSC143 Winter 2024");
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        drawing_panel = new DrawingPanel();
        drawing_panel.setLayout(null);
        getContentPane().add(drawing_panel);

        setVisible(true);

        drawing_panel.repaint();

    }

    /**
     * update() occurs when Subject state changes, here this repaints the drawing_panel
     */
    @Override
    public void update() {
        drawing_panel.repaint();
    }

    /**
     * drawAll() draws all the elements from the Subject fractal generator
     */
    private void drawAll(Graphics g) {
        elements = subject.getData(getWidth(), getHeight());
        for (FractalElement fe: elements) {
            fe.draw(g,getWidth(),getHeight());
        }
    }

    /**
     * DrawingPanel extends JPanel in order to draw all the elements when paintComponent is called
     */
    private class DrawingPanel extends JPanel {
        /**
         * This draws a black background then draws all the elements using a call to drawAll()
         * @param g the <code>Graphics</code> object to protect
         */
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            System.out.println("One call");
            if(elements != null) {
                System.out.println(elements.size());
            }
            //g.setColor(new Color(0,0,0));
            //g.fillRect(0,0,getWidth(),getHeight());
            drawAll(g);
        }

        /**
         * Default constructor, comment added to silence xdoclint
         */
        public DrawingPanel() {
            setBackground(new Color(0,0,0));
        }
    }
}