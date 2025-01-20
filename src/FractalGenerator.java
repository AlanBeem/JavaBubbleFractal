import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * FractalGenerator is where the settings of this application reside, mutable via setSettings, a method required by the
 * Subject interface. This class operates in accordance with the Observer design pattern, wherein this is a Subject.
 * This contains a recursive (private) method that generates a fractal of reference geometry and public method getData()
 * that returns an ArrayList of FractalElement based on that reference geometry, and in so doing maintains the
 * requirement of being capable of contain various shapes that implement FractalElement (simply requiring a
 * draw(Graphics) method) but still making use of the choice to compute the fractal geometry using circles with an
 * origin (x, y) and a radius (using a private inner record). The decomposition of functions yields some of the
 * theoretical generalizability of this class discussed in Proj04.pdf, for instance drawing an arbitrary polygon would
 * be easy to add and only require revising getData() and the code the generate the polygon.
 */
public class FractalGenerator implements Subject {

    /**
     * observers holds the list of Observers of this (Subject)
     */
    private ArrayList<Observer> observers;

    /**
     * recursion_depth is a setting that is mutable via setSettings
     */
    private int recursion_depth;

    /**
     * child_to_parent is a setting that is mutable via setSettings
     */
    private int child_to_parent;

    /**
     * child_count is a setting that is mutable via setSettings
     */
    private int child_count;

    /**
     * bedlam is a setting that is mutable via setSettings
     */
    private int bedlam;

    /**
     * colors is a setting that is mutable via setSettings
     */
    private Color color;

    /**
     * initial_theta is set to a Gaussian value with a mean of pi and std dev of sqrt(pi)
     */
    private double initial_theta;

    /**
     * random_colors is a setting that is mutable via setSettings
     */
    private boolean random_colors;


    /**
     * FractalGenerator() is the constructor of this class and sets up this to work as a Subject and initializes
     * settings based on magic numbers corresponding to FractalGUI sliders initial settings
     */
    public FractalGenerator() {
        observers = new ArrayList<Observer>();
        color = new Color((float) 1,(float) 1,(float) 1,(float) 0.4);
        setSettings(1,20,4,0,color,false);
    }

    /**
     * attach(Observer) provides part of the function of maintaining a list of Observers, namely of adding an Observer
     * to the instance variable observers, an ArrayList of Observer
     * @param observer the Observer to add to observers
     */
    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * detach(Observer) provides part of the function of maintaining a list of Observers, namely of removing an Observer
     * from the instance variable observers, an ArrayList of Observer
     * @param observer the Observer to be removed from the list of Observers
     */
    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    /**
     * notifyObservers is required by the Subject interface with respect to the Observer design pattern, its operation
     * is simple and proscribed by the implementation of the Observer design pattern
     */
    @Override
    public void notifyObservers() {
        for (Observer observer: observers) {
            observer.update();
        }
    }

    /**
     * getData() converts the output of getElements, which operates on circles, to FractalElements, which have color and
     * have a draw method, in this case, bubbles with reflections composed of a Bubble and an Arc (records), but we
     * could as easily position squares relative to a circular fractal geometry (a nice fractal might be the corners of
     * a square (or random rectangle) all having a square (or (random) rectangle) but this would likely require
     * rewriting the recursive method getElements, as an illustration of the bounds of the capabilities of a
     * generalized getData(), additionally it would be relatively easy to use getData() as is to put a star or other
     * shape as the reflection).
     *
     * @return bubbles_arcs an ArrayList of FractalElement generated according to drawing composition
     */
    public ArrayList<FractalElement> getData(int window_width, int window_height) {
        Random rand = new Random();
        int x_initial = 0, y_initial = 0, radius = (int) ((double) window_width/8);
        if (bedlam > 0) {
            x_initial += rand.nextGaussian(0,bedlam*bedlam);
            y_initial += rand.nextGaussian(0,bedlam*bedlam);
            radius = (int) rand.nextGaussian(radius,bedlam*bedlam);
        }
        ArrayList<Circle> circles = getElements( new Circle(x_initial, y_initial, radius), 1);
        ArrayList<FractalElement> bubbles_arcs = new ArrayList<>();
        for (int i = 0; i < circles.size(); i++) {
            if (random_colors) {
                color = getColor();
            }
            Circle relative_circle = circles.get(i);
            bubbles_arcs.add(new Bubble(color, relative_circle.center_x(), relative_circle.center_y(), relative_circle.radius()));
            bubbles_arcs.add(new Arc(relative_circle.center_x(), relative_circle.center_y(), relative_circle.radius()));
        }
        return bubbles_arcs;
    }

    /**
     * getColor() is a helper method that is used when random_colors is true, by getData()
     * @return a random color from an array of colors from Proj04.pdf
     */
    private Color getColor() {
        Random rand = new Random();
        Color color;
        int a = (int) (0.4*255);
        Color[] pastel_colors = new Color[] {new Color(255, 255, 176,a),
            new Color(148, 168, 208, a),
            new Color(221, 212, 240,a),
            new Color(251, 182, 209, a),
            new Color(255, 223, 211, a)};
        // @TODO: recreate previous version color selection, it looked really good
        /*do {
            color = new Color(Math.min(rand.nextInt(145)+150,255),
                    Math.min(rand.nextInt(255)+150,255),
                    Math.min(rand.nextInt(255)+150, 255), (int) (0.4*255));
            // the while statement limits certain colors such as browns, reds, and oranges
        } while ((Math.abs(color.getRed() - color.getGreen()) < 55 && Math.abs(color.getGreen() - color.getBlue()) < 55) ||
                (color.getRed() > (color.getBlue() + color.getGreen()) / 1.618) ||
                (color.getGreen() > (color.getRed() + color.getBlue()) / 1.5) ||
                (color.getRed() + color.getGreen() + color.getBlue()) > 540 ||
                (Math.abs(color.getRed() - color.getGreen()) > 65 && color.getRed() + color.getGreen() < 255));
        return new Color(color.getRed(), color.getBlue(), color.getGreen(), (int) (0.4*255));*/
        return pastel_colors[rand.nextInt(255)% pastel_colors.length];
    }

    /**
     * getElements is a private recursive method that returns an ArrayList of Circle
     * @param current the current Circle
     * @param depth the current depth in recursion
     * @return an ArrayList of Circle that is recursively generated by this method
     */
    private ArrayList<Circle> getElements(Circle current, int depth) {
        ArrayList<Circle> array = new ArrayList<Circle>();
        if (current.radius() > 0) {
            array.add(current);
            depth++;
            if (depth <= recursion_depth) {
                double subdivision = Math.toRadians(360 / child_count);
                int child_radius = (int) (current.radius() * (Math.sqrt(((double) child_to_parent) / 100.0))); // I'll interpret this as a percent size
                int distance = current.radius() + child_radius;
                for (int i = 0; i < child_count; i++) {
                    double theta = subdivision * i;
                    if (bedlam > 0) {
                        Random rand = new Random();
                        child_radius = (int) rand.nextGaussian(child_radius, 0.1*child_radius*(bedlam/4.0));
                        theta += initial_theta;
                        theta = rand.nextGaussian(theta,0.1*subdivision*(bedlam/4.0));
                        distance = (int) rand.nextGaussian(distance, 0.25*distance*(bedlam/4.0));

                    }
                    array.addAll(getElements(new Circle((int) (Math.round(current.center_x() + distance * Math.cos(theta))),
                            (int) (Math.round(current.center_y() + distance * Math.sin(theta))), child_radius), depth));
                }
            }
        }
        return array;
    }

    /**
     * setSettings is required by the interface and sets all instance variables of FractalGenerator
     * @param recursion_depth the recursion depth (at 2 this will generate a parent element and one level of child elements)
     * @param child_to_parent the percentage ratio of child to parent radii
     * @param child_count the number of child elements
     * @param bedlam the randomization value
     * @param color the currently selected color
     * @param random_colors whether to use random colors selected from an array
     */
    @Override
    public void setSettings(int recursion_depth, int child_to_parent, int child_count, int bedlam, Color color, boolean random_colors) {
        Random rand = new Random();
        this.recursion_depth = recursion_depth;
        this.child_to_parent = child_to_parent;
        this.child_count = child_count;
        this.bedlam = bedlam;
        this.color = color;
        this.random_colors = random_colors;
        this.initial_theta = rand.nextGaussian(Math.PI, Math.sqrt(Math.PI));
        notifyObservers();
    }

    /**
     * Circle is a private helper record that represents the position and radius of a circle
     * @param center_x the x coordinate of the center of the circle
     * @param center_y the y coordinate of the center of the circle
     * @param radius the radius of the circle
     */
    private record Circle(int center_x, int center_y, int radius) {

    }

}