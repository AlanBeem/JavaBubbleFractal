import java.awt.*;

/**
 * Arc is a record representing decomposition of the task of drawing a bubble with a reflection, this part is the
 * reflection. This task is carried out relative to a circle.
 * @param center_x the center of the relative circle
 * @param center_y the center of the relative circle
 * @param radius the radius of the relative circle
 */
public record Arc(int center_x, int center_y, int radius) implements FractalElement {
    @Override
    public void draw(Graphics g, int window_width, int window_height) {
        Color old_color = g.getColor();
        g.setColor(new Color(255,255,255,(int) (0.2*255)));
        ((Graphics2D) g).setStroke(new BasicStroke(Math.max((int) (((double) radius)/10.0), 2)));
        if (((double)radius)/10.0 < 1) {
            if (radius == 1) {
                g.setColor(new Color(255, 255, 255, (int) (0.4 * 255)));
                g.fillRect(center_x + (int) (0.75 * radius * Math.cos(45.0)) + window_width/2,
                        center_y - (int) (0.75 * radius * Math.sin(45.0)) + window_height/2, 1, 1);
            } else {
                g.setColor(new Color(255, 255, 255, (int) (0.2 * 255)));
                g.fillRect(center_x + (int) (0.75 * radius * Math.cos(45.0)) + window_width/2,
                        center_y - (int) (0.75 * radius * Math.sin(45.0)) + window_height/2, 1, 2);
                g.fillRect(center_x + (int) (0.75 * radius * Math.cos(45.0))-1 + window_width/2,
                        center_y - (int) (0.75 * radius * Math.sin(45.0))+1 + window_height/2, 2, 1);

            }
        } else {
            double sin_cos_45 = Math.sin(45);
            g.drawArc(center_x + window_width/2,
                    (int) (center_y - (3.0 * radius*sin_cos_45) / 4) + window_height/2,
                    (int) ((3.0 * radius*sin_cos_45) / 4), (int) ((3.0 * radius*sin_cos_45) / 4), 40, 10);
            // drawing two arcs here with a total alpha of 0.4*255, I just think it looks good
            g.drawArc(center_x - 1 + window_width/2,
                    (int) (center_y - (3.0 * radius*sin_cos_45) / 4) + window_height/2,
                    (int) ((3.0 * radius*sin_cos_45) / 4)-1, (int) ((3.0 * radius*sin_cos_45) / 4), 40, 10);
        }
        ((Graphics2D) g).setStroke(new BasicStroke());
        g.setColor(old_color);
        /*int[] x_values = new int[20];
        int[] y_values = new int[20];
        double distance = radius*0.9;
        int theta = 50;
        for (int i = 0; i < 10; i++) {
            x_values[i] = (int) Math.round(center_x + distance*Math.cos(Math.toRadians(theta)));
            y_values[i] = (int) Math.round(center_y - distance*Math.sin(Math.toRadians(theta++)));
        }
        distance = radius*0.8;
        for (int i = 10; i < 20; i++) {
            x_values[i] = (int) Math.round(center_x + distance*Math.cos(Math.toRadians(theta)));
            y_values[i] = (int) Math.round(center_y - distance*Math.sin(Math.toRadians(theta--)));
        }
        gr.fillPolygon(x_values, y_values, 20);
        for (int i = 0; i < 20; i++) {
            x_values[i]++;
            y_values[i]++;
            x_values[i]++;
            y_values[i]++;
        }
        gr.fillPolygon(x_values, y_values, 20);*/
    }

}
