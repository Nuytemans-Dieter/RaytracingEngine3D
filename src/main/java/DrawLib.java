import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawLib {

    private final PointPanel pointPanel;

    public DrawLib(int width, int heigth)
    {
        pointPanel = new PointPanel(width, heigth);

        JFrame frame = new JFrame("Ray tracing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(pointPanel);
        frame.pack();
        frame.setVisible(true);
    }


    /**
     * Draws a white dot at the specified location
     * @param y y-coördinate of the dot (heigth)
     * @param x x-coördinate of the dot (width)
     */
    public void drawPoint(int y, int x)
    {
        pointPanel.drawPoint(y, x, 1.0f, 1.0f, 1.0f);
    }

    /**
     * Draws a coloured dot at the specified location
     * @param y y-coördinate of the dot (heigth)
     * @param x x-coördinate of the dot (width)
     * @param r red color component [0.0, 1.0]
     * @param g green color component [0.0, 1.0]
     * @param b blue color component [0.0, 1.0]
     */
    public void drawPoint(int y, int x, float r, float g, float b)
    {
        pointPanel.drawPoint(y, x, r, g, b);
    }

}

class PointPanel extends JPanel {

    private final BufferedImage frame;

    PointPanel(int width, int heigth) {
        super(true);
        frame = new BufferedImage(width, heigth, BufferedImage.TYPE_INT_RGB);
        this.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
    }


    /**
     * Draws a coloured dot at the specified location
     * @param y y-coördinate of the dot (heigth)
     * @param x x-coördinate of the dot (width)
     * @param r red color component [0.0, 1.0]
     * @param g green color component [0.0, 1.0]
     * @param b blue color component [0.0, 1.0]
     */
    public void drawPoint(int y, int x, float r, float g, float b)
    {
        frame.setRGB(x, y, new Color(r, g, b).getRGB());
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.drawImage(frame, 0, 0, this.getWidth(), this.getHeight(), null);
    }
}