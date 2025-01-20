import java.awt.*;

/**
 * Bubble is a record representing decomposition of the task of drawing a bubble with a reflection, this part is the
 * bubble. This task is carried out relative to a circle. Could it make sense to have a circle as a class?
 * @param color
 * @param center_x the center of the relative circle
 * @param center_y the center of the relative circle
 * @param radius the radius of the relative circle
 */
public record Bubble(Color color, int center_x, int center_y, int radius) implements FractalElement{
    /**
     * draw(Graphics) draws the Bubble, not including the reflection
     * @param g where to draw the Bubble
     */
    @Override
    public void draw(Graphics g, int window_width, int window_height) {
        int draw_width = radius*2;
        int draw_height = draw_width;
        int origin_x = (center_x - radius) + window_width/2;
        int origin_y = (center_y - radius) + window_height/2;
        Color old_color = g.getColor();
        g.setColor(color);
        g.fillOval(origin_x,origin_y,draw_width,draw_height);
        g.drawOval(origin_x + 1, origin_y + 1, draw_width - 2, draw_height - 2);
        g.setColor(new Color(getDarkerColor(color).getRed(), getDarkerColor(color).getGreen(), getDarkerColor(color).getBlue(), 180));
        g.drawOval(origin_x,origin_y,draw_width,draw_height);
        g.setColor(old_color);
    }

    /**
     * getDarkerColor(Color) is a helper method that returns a darker shade of the same color.
     * @param color the Color to be referenced
     * @return a darker shade of the same color
     */
    private Color getDarkerColor(Color color) {
        int[] the_color = new int[]{color.getRed(), color.getGreen(), color.getBlue()};
        for (int j = 0; j < 3; j++) {
            the_color[j] = (int) (((double) the_color[j])/1.618);

        }
        return new Color(the_color[0], the_color[1], the_color[2]);
    }

}
