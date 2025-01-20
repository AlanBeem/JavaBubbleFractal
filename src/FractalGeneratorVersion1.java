/*
import java.awt.*;
import java.util.ArrayList;

public abstract class FractalGeneratorVersion1 implements Subject {
    private ArrayList<FractalElement> elements;
    private int recursion_depth, child_to_parent, child_count, bedlam;
    private Color[] colors;
    private ArrayList<Observer> observers;

    public FractalGeneratorVersion1() {
        observers = new ArrayList<Observer>();
        colors = new Color[1];
        colors[0] = new Color(60,180,180);
        setSettings(0,20,0,0, colors);
        // bedlam add noise to center of starting circle for 3, 4, different regimes of 'chaos', could really make it
        // chaotic though, something to think about (unpredictable magnitude of change output to change input, and
        // chaining values together, not unlike process for designing GUI)
        setElements();
    }

    private void setElements() {
        elements = fractalGenerator(new ArrayList<>(), new Circle(0, 0, 0, colors[0]), 0);
        System.out.println("elements set using setElements() in FractalGenerator2, number of elements: " + elements.size());
    }

    private ArrayList<FractalElement> fractalGenerator(ArrayList<FractalElement> elements, Circle circle, int depth) {
        if (depth >= recursion_depth) {
            // do nothing
        } else {
            depth++;
            elements.addAll(bubble(circle));
//            for (Circle child_circle: getChildCircles(circle)) {
//                elements.addAll(fractalGenerator(elements, child_circle, depth));
//            }
        }
        return elements;
    }

    private Circle[] getChildCircles(Circle circle) {
        Circle[] child_circles = new Circle[child_count];
        double subdivision = Math.toRadians(360.0/child_count);
        int child_radius = circle.getRadius()/child_to_parent;
        for (int i = 0; i < child_count; i++) {
            double angle = subdivision*i;
            double delta_x = Math.sqrt((circle.getRadius()+child_radius)*(circle.getRadius()+child_radius)/(1 + (Math.tan(angle)*Math.tan(angle))));
            double delta_y = delta_x*Math.tan(angle);
            //child_circles[i] = new Circle(circle.getCenter_x() + delta_x, circle.getCenter_y() + delta_y,child_radius, colors[0]);
        }
        return child_circles;
    }

    private ArrayList<FractalElement> bubble(Circle circle) {
        ArrayList<FractalElement> bubble_elements = new ArrayList<>();
        // the Bubble is first because the Arc goes over it
        bubble_elements.add(new Bubble(circle.getCenter_x(), circle.getCenter_y(), circle.getRadius(), colors[0]));
        bubble_elements.add(new Arc(circle.getCenter_x(), circle.getCenter_y(), circle.getRadius(), colors[0]));
        return bubble_elements;
    }

    @Override
    public void attach(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void detach(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer observer: observers) {
            observer.update();
        }
    }

    @Override
    public ArrayList<FractalElement> getData() {
        setElements();
        return elements;
    }

//    @Override
    public void setSettings(int recursion_depth, int child_to_parent, int child_count, int bedlam, Color[] colors) {
        this.recursion_depth = recursion_depth;
        this.child_to_parent = child_to_parent;
        this.child_count = child_count;
        this.bedlam = bedlam;
        this.colors = colors;
        notifyObservers();
        System.out.println("Settings set by setSettings() in FractalGenerator2");
    }
}
*/
