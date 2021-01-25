package objects.textures;

import graphics.Rgb;
import objects.Texture;

public class CheckerboardTexture implements Texture {

    private final double squareSize;

    public CheckerboardTexture()
    {
        this(0.4);
    }

    public CheckerboardTexture(double squareSize )
    {
        this.squareSize = squareSize;
    }

    @Override
    public Rgb getColor(double x, double y, double z) {

        int squareIndex = ((int) (x / squareSize) + (int) (y / squareSize) + (int) (z / squareSize)) % 2;
        return squareIndex==0 ? Rgb.fromColor( Rgb.Color.BLACK ) : Rgb.fromColor( Rgb.Color.GREY );
    }

}
