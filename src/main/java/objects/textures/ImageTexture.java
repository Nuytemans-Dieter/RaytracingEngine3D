package objects.textures;

import graphics.Rgb;
import objects.Texture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class ImageTexture implements Texture {

    public ImageTexture()
    {
        try
        {
            BufferedImage image = ImageIO.read(ImageTexture.class.getResourceAsStream("/checker.png"));
            
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Rgb getColor(double x, double y, double z) {
        return Rgb.fromColor( Rgb.Color.RED );
    }
}
