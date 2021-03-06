package graphics;

import input.KeyboardInput;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DrawLib {

    private final JFrame frame;
    private final PointPanel pointPanel;

    public DrawLib(int width, int height)
    {
        this(width, height, null);
    }

    public DrawLib(int width, int height, KeyboardInput input)
    {
        pointPanel = new PointPanel(width, height);

        frame = new JFrame("Ray tracing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(pointPanel);
        frame.pack();
        frame.setVisible(true);

        if (input != null)
            frame.addKeyListener( input );
    }


    /**
     * Draws a white dot at the specified location
     * The bottom left pixel is located at (0, 0) and the top right at (width, height)
     *
     * @param y y-coordinate of the dot (height)
     * @param x x-coordinate of the dot (width)
     */
    public void drawPoint(int x, int y)
    {
        pointPanel.drawPoint(x, y, 1.0f, 1.0f, 1.0f);
    }

    /**
     * Draws a coloured dot at the specified location
     * The bottom left pixel is located at (0, 0) and the top right at (width, height)
     *
     * @param y y-coordinate of the dot (height)
     * @param x x-coordinate of the dot (width)
     * @param r red color component [0.0, 1.0]
     * @param g green color component [0.0, 1.0]
     * @param b blue color component [0.0, 1.0]
     */
    public void drawPoint(int x, int y, float r, float g, float b)
    {
        pointPanel.drawPoint(x, y, r, g, b);
    }


    /**
     * Draws a coloured dot at the specified location
     * The bottom left pixel is located at (0, 0) and the top right at (width, height)
     *
     * @param y y-coordinate of the dot (height)
     * @param x x-coordinate of the dot (width)
     * @param color the color of the future pixel
     */
    public void drawPoint(int x, int y, Rgb color)
    {
        pointPanel.drawPoint(x, y, color.r(), color.g(), color.b());
    }


    public void forceUpdate()
    {
        pointPanel.repaint();
    }
}

class PointPanel extends JPanel {

    private final BufferedImage frame;

    PointPanel(int width, int height) {
        super(true);
        frame = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        frame.setAccelerationPriority(0);
        this.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
    }


    /**
     * Draws a coloured dot at the specified location
     * The bottom left pixel is located at (0, 0) and the top right at (width, height)
     *
     * @param y y-coordinate of the dot (height)
     * @param x x-coordinate of the dot (width)
     * @param r red color component [0.0, 1.0]
     * @param g green color component [0.0, 1.0]
     * @param b blue color component [0.0, 1.0]
     */
    public void drawPoint(int x, int y, float r, float g, float b)
    {
        r = Math.min(r, 1);
        g = Math.min(g, 1);
        b = Math.min(b, 1);
        frame.setRGB(x, frame.getHeight() - 1 - y, new Color(r, g, b).getRGB());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(frame, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}