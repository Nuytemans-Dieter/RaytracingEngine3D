package objects.textures;

import graphics.Rgb;
import objects.Material;
import objects.Texture;

public class Circles implements Texture {

    private final double SIZE;

    public Circles()
    {
        this(0.4);
    }

    public Circles(double size)
    {
        this.SIZE = size;
    }

    @Override
    public Rgb getColor(double x, double y, double z) {

        Rgb color;

        double relX = (x / SIZE);
        double relY = (y / SIZE);
        double relZ = (z / SIZE);

        int squareIndex = ((int) relX + (int) relY + (int) relZ) % 2;
        if (squareIndex == 0 && Math.pow(x%(2*SIZE), 2) + Math.pow(y%(2*SIZE), 2) + Math.pow(z%(2*SIZE), 2)< SIZE*SIZE)
        {
            color = Rgb.fromColor( Rgb.Color.RED );
        }
        else
        {
            color = Rgb.fromColor( Rgb.Color.GREEN );
        }
        return color;
    }
}
