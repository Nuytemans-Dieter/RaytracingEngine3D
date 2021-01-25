package objects.textures;

import graphics.Rgb;
import objects.Texture;

import javax.imageio.ImageIO;
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

        Color c = new Color(this.image.getRGB( (int) (x * image.getWidth()), (int) (y * image.getHeight())));
        return new Rgb( c.getRed(), c.getGreen(), c.getBlue() );
    }
}
