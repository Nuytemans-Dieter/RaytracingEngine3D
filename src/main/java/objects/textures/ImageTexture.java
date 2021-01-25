package objects.textures;

import graphics.Rgb;
import objects.Texture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageTexture implements Texture {

    private BufferedImage image;
    private final double pixelSize;
    private int numPixels;

    public ImageTexture()
    {
        this("checker.png", 0.001);
    }

    public ImageTexture(String image)
    {
        this(image, 0.001);
    }

    public ImageTexture(String image, double pixelSize)
    {
        this.pixelSize = pixelSize;
        try
        {
            this.image = ImageIO.read(ImageTexture.class.getResourceAsStream("/" + image));

//            JFrame frame = new JFrame("My first JFrame!");
//            JLabel label = new JLabel();
//            frame.setSize(this.image.getWidth(), this.image.getHeight());
//            frame.setLocationRelativeTo(null);
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//            frame.add( new JLabel(new ImageIcon(this.image)),BorderLayout.CENTER);
//            frame.setIconImage(this.image);
//            frame.setVisible(true);
//            label.setVisible(true);

            numPixels = this.image.getWidth() * this.image.getHeight();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Rgb getColor(double x, double y, double z) {

        if (image == null)
            return Rgb.fromColor( Rgb.Color.WHITE );

        Color c = new Color(this.image.getRGB( (int) (x * (image.getWidth()-1)), (int) (y * (image.getHeight()-1))));
        return new Rgb( (float) c.getRed()/255, (float) c.getGreen()/255, (float) c.getBlue()/255 );
    }
}
