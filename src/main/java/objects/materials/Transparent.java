package objects.materials;

import graphics.Rgb;
import objects.Material;

public class Transparent extends Material {

    public Transparent()
    {
        super(
            Rgb.fromColor( Rgb.Color.WHITE ),
            0,
            0.95f,
            0,
            0.00f,
            new float[]{100,100,100},
            1f);
    }

}
