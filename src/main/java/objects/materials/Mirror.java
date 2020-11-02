package objects.materials;

import graphics.Rgb;
import objects.Material;

public class Mirror extends Material {


    public Mirror()
    {
        super
            (
                new Rgb(0.35f, 0.35f, 0.35f),
                0.2f,
                0.4f,
                2,
                0.8f,
                new float[]{1, 1, 1},
                0.0f
            );
    }
}
