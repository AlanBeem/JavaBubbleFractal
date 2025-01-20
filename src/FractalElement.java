import java.awt.*;

/**
 * FractalElement requires implementing classes be able to draw themselves
 */
public interface FractalElement {
    abstract void draw(Graphics gr, int window_width, int window_height);
}