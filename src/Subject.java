import java.awt.*;
import java.util.ArrayList;

public interface Subject {
    public void attach(Observer observer);
    public void detach(Observer observer);
    public void notifyObservers();
    public ArrayList<FractalElement> getData(int window_width, int window_height);
    public void setSettings(int recursion_depth, int child_to_parent,
                            int child_count, int bedlam, Color color, boolean random_colors);
}
