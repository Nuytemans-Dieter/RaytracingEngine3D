package objects.materials;

import graphics.Rgb;
import objects.Material;

public class Plastic extends Material {

    public Plastic(Rgb color)
    {
        super
        (
            color,
            0.8f,
            0.8f,
            64,
            0.0f,
            1,
            0.0f
        );
    }

}
