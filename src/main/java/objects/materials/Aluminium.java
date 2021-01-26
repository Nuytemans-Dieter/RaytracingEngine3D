package objects.materials;

import graphics.Rgb;
import objects.Material;

public class Aluminium extends Material {

    public Aluminium()
    {
        super(
            new Rgb(0.45f, 0.45f, 0.45f),
            0.8f,
            0.3f,
            32,
            0.2f,
            1,
            0.0f
        );
    }

}
