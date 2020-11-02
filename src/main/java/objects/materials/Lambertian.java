package objects.materials;

import graphics.Rgb;
import objects.Material;

public class Lambertian extends Material {

    public Lambertian()
    {
        super
        (
            new Rgb(0.35f, 0.35f, 0.35f),
            0.5f,
            0.2f,
            2,
            0.0f,
            new float[]{1, 1, 1},
            0.0f
        );
    }
}
