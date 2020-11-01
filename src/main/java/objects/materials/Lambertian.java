package objects.materials;

import graphics.Rgb;
import objects.Material;

public class Lambertian extends Material {

    public Lambertian()
    {
        super
        (
            new Rgb(0.5f, 0.5f, 0.5f),
            0.4f,
            0.4f,
            16
        );
    }
}
