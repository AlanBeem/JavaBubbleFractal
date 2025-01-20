/**
 * Main is a runnable main that uses no global variables but does conduct a necessarily ordered set of behaviors given
 * the design pattern of Observer and Subject in FractalGenerator and FractalDrawing.
 */
public class Main {
    public static void main(String[] args) {
        FractalGenerator f_gen = new FractalGenerator();
        FractalGUI f_gui = new FractalGUI(f_gen);
        FractalDrawing f_drawing = new FractalDrawing(f_gen);
    }
}