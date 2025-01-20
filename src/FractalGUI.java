import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * FractalGUI is an implementation using Java Swing to achieve a graphical user interface with sliders and buttons. In
 * addition, this class has a Subject (interface defined to enable use of the design pattern: observer, subject),
 * however, it is not an Observer, for any change in state, all of state values are written to Subject using its
 * setSettings() method. I found the UI design was made a lot easier by using relationships to define various sizes and
 * positions. The UI elements are defined from top to bottom, but the "construction" of the elements is finished in
 * the order required to have the references needed for each actionListener.
 */
public class FractalGUI extends JFrame {

    /**
     * colors is a Color used to allow selection of a color by JColorChooser, the rest of the settings are taken
     * directly from the JComponents concerned
     */
    private Color color;

    /**
     * FractalGUI(Subject) is the constructor for FractalGUI and a number of components, 4 sliders, two buttons, and
     * a checkbox. All state changes to the components call setSettings of the subject_generator
     * @param subject_generator the FractalGenerator
     */
    public FractalGUI(Subject subject_generator) {

        color = new Color((float) 1,(float) 1,(float) 1, (float) 0.4);

        final boolean[] random_colors = {false};

        // Ratios of successive Fibonacci numbers approximate the Golden ratio, phi = 1.618... and phi is the most
        // irrational of the irrational numbers, it's used below because it's aesthetically pleasing
        double phi = 1.6180339887498948; // this is the maximum precision of double, an alternative would be to use
        // BigDecimal, but it's already deliberately excessive
        int width = 350;
        int height =  (int) (phi*width);
        int border = (int) (width/phi/phi/phi/phi/phi);
        int item_width = width - 2*border;
        int text_height = (int) (border/phi);
        int slider_height = border + text_height;

        setResizable(false);
        setSize(width, height);
        setTitle("Project 4, CSC143 Winter 2024");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);  // absolute positioning
        getContentPane().add(mainPanel);

        Font font = new Font(Font.DIALOG, Font.BOLD, 12);

        int text_x = border, text_y = border, slider_x = border, slider_y = border+text_height;

        JLabel recursionDepthLabel = new JLabel("Recursion depth");
        recursionDepthLabel.setBounds(text_x,text_y, item_width, text_height);
        recursionDepthLabel.setFont(font);
        mainPanel.add(recursionDepthLabel);

        JSlider recursionDepthSlider = new JSlider(2,8,2);
        recursionDepthSlider.setBounds(slider_x, slider_y, item_width,slider_height);
        recursionDepthSlider.setFont(font);
        recursionDepthSlider.setMajorTickSpacing(1);
        recursionDepthSlider.setPaintTicks(true);
        recursionDepthSlider.setPaintLabels(true);

        mainPanel.add(recursionDepthSlider);

        text_y = text_y + text_height + slider_height + text_height;
        slider_y = text_y + text_height;

        JLabel childToParentRatioLabel = new JLabel("Child to parent ratio");
        childToParentRatioLabel.setBounds(text_x,text_y, item_width, text_height);
        childToParentRatioLabel.setFont(font);
        mainPanel.add(childToParentRatioLabel);

        JSlider childToParentRatioSlider = new JSlider(20,70,20);  // I think that 1 or less than 1 would look really cool with higher bedlam levels
        childToParentRatioSlider.setBounds(slider_x, slider_y, item_width,slider_height);
        childToParentRatioSlider.setFont(font);
        childToParentRatioSlider.setMajorTickSpacing(10);
        childToParentRatioSlider.setMinorTickSpacing(1);
        childToParentRatioSlider.setPaintTicks(true);
        childToParentRatioSlider.setPaintLabels(true);

        mainPanel.add(childToParentRatioSlider);

        text_y = text_y + text_height + slider_height + text_height;
        slider_y = text_y + text_height;

        JLabel childCountLabel = new JLabel("Child count");
        childCountLabel.setBounds(text_x,text_y, item_width, text_height);
        childCountLabel.setFont(font);
        mainPanel.add(childCountLabel);

        JSlider childCountSlider = new JSlider(1,11,1);
        childCountSlider.setBounds(slider_x, slider_y, item_width,slider_height);
        childCountSlider.setFont(font);
        childCountSlider.setMajorTickSpacing(2);
        childCountSlider.setMinorTickSpacing(1);
        childCountSlider.setPaintTicks(true);
        childCountSlider.setPaintLabels(true);

        mainPanel.add(childCountSlider);

        text_y = text_y + text_height + slider_height + text_height;
        slider_y = text_y + text_height;

        String bedlamLabel = "Bedlam level";
        JLabel bedlamLevelLabel = new JLabel("Bedlam level");
        bedlamLevelLabel.setBounds(text_x,text_y, item_width, text_height);
        bedlamLevelLabel.setFont(font);
        mainPanel.add(bedlamLevelLabel);

        JSlider bedlamLevelSlider = new JSlider(0,4,0);
        bedlamLevelSlider.setBounds(slider_x, slider_y, item_width,slider_height);
        bedlamLevelSlider.setFont(font);
        bedlamLevelSlider.setMajorTickSpacing(1);
        bedlamLevelSlider.setPaintTicks(true);
        bedlamLevelSlider.setPaintLabels(true);

        mainPanel.add(bedlamLevelSlider);

        int last_y = text_y + text_height + slider_height;

        int feature_y = last_y + (int) (phi*text_height), feature_width = 5*border, feature_height = (int) (border/phi);

        int feature_x = width/2 - feature_width/2;

        int draw_it_width = (int) (phi*feature_width);

        JButton fractalColor = new JButton("Fractal colors..."); // have an option within color chooser to select multiple colors (might not be easy enough)
        fractalColor.setBounds(feature_x,feature_y, feature_width, feature_height);
        fractalColor.setRolloverEnabled(true);

        mainPanel.add(fractalColor);

        feature_y = feature_y + feature_height + text_height;
        feature_width = (int) ((1.8903/1.61)*feature_width); // measured using a drawing tool that gave the number in inches
        feature_x = width/2 - feature_width/2;
        JCheckBox randomBubblyColors = new JCheckBox(); // opposing regions like red-purple and green (both darker)
        randomBubblyColors.setBounds(feature_x, feature_y, feature_width, text_height);
        randomBubblyColors.setText("Random pastel colors");
        randomBubblyColors.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!random_colors[0]) {
                    random_colors[0] = true;
                } else {
                    random_colors[0] = false;
                }
                subject_generator.setSettings(recursionDepthSlider.getValue(), childToParentRatioSlider.getValue(), childCountSlider.getValue(), bedlamLevelSlider.getValue(), color, random_colors[0]);
            }
        });
        mainPanel.add(randomBubblyColors);

        fractalColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // JColorChooser
                JFrame color_frame = new JFrame();
                color_frame.setResizable(false);
                color_frame.setSize(500,350);
                setTitle("Fractal Color Chooser");
                setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

                JPanel mainPanel = new JPanel();
                mainPanel.setLayout(null);  // absolute positioning
                getContentPane().add(mainPanel);
                JPanel color_panel = new JPanel();
                color_panel.setBounds(350,350,500,350);

                JColorChooser jcc = new JColorChooser();
                jcc.getSelectionModel().addChangeListener(new ChangeListener() {
                    @Override
                    public void stateChanged(ChangeEvent e) {
                        if (randomBubblyColors.isSelected()) {
                            randomBubblyColors.doClick();
                        }
                        color = new Color(jcc.getColor().getRed(), jcc.getColor().getGreen(), jcc.getColor().getBlue(),(int) (0.4*255));
                        subject_generator.setSettings(recursionDepthSlider.getValue(), childToParentRatioSlider.getValue(), childCountSlider.getValue(), bedlamLevelSlider.getValue(), color, random_colors[0]);
                    }
                });
                color_frame.add(jcc);
                color_frame.setVisible(true);

            }

        });

        feature_y = feature_y + text_height + text_height;
        feature_x = width/2 - draw_it_width/2;
        feature_height = ((int )(phi*feature_height));

        JButton draw_it = new JButton("DRAW THE FRACTAL!");
        draw_it.setFont(new Font(Font.MONOSPACED, Font.HANGING_BASELINE|Font.BOLD, 14));
        draw_it.setBounds(feature_x, feature_y, draw_it_width, feature_height);
        draw_it.setRolloverEnabled(true);
        draw_it.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                subject_generator.setSettings(recursionDepthSlider.getValue(), childToParentRatioSlider.getValue(), childCountSlider.getValue(), bedlamLevelSlider.getValue(), color, random_colors[0]);
            }
        });
        mainPanel.add(draw_it);

        // Button code example retrieved from tutorialspoint.com/how-to-add-action-listener-to-jbutton-in-java

        recursionDepthSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                subject_generator.setSettings(recursionDepthSlider.getValue(), childToParentRatioSlider.getValue(), childCountSlider.getValue(), bedlamLevelSlider.getValue(), color, random_colors[0]);
            }
        });

        childToParentRatioSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                subject_generator.setSettings(recursionDepthSlider.getValue(), childToParentRatioSlider.getValue(), childCountSlider.getValue(), bedlamLevelSlider.getValue(), color, random_colors[0]);
            }
        });

        childCountSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                subject_generator.setSettings(recursionDepthSlider.getValue(), childToParentRatioSlider.getValue(), childCountSlider.getValue(), bedlamLevelSlider.getValue(), color, random_colors[0]);
            }
        });

        bedlamLevelSlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                subject_generator.setSettings(recursionDepthSlider.getValue(), childToParentRatioSlider.getValue(), childCountSlider.getValue(), bedlamLevelSlider.getValue(), color, random_colors[0]);
            }
        });

        setVisible(true);
    }

}
