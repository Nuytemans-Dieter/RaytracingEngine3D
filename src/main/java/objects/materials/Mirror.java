package objects.materials;

import graphics.Rgb;
import objects.Material;

public class Mirror extends Material {


    public Mirror()
    {
        super
            (
                new Rgb(0.35f, 0.35f, 0.35f),
                0.03f,
                0.6f,
                128,
                0.95f,
                new float[]{1, 1, 1},
                0.0f
            );
    }
}
