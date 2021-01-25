package objects.textures;

import graphics.Rgb;
import objects.Texture;

public class SineTexture implements Texture {
    @Override
    public Rgb getColor(double x, double y, double z) {
        return Math.sin( x + y + z ) < 0.5 ? Rgb.fromColor( Rgb.Color.RED ) : Rgb.fromColor( Rgb.Color.GREEN );
    }
}
