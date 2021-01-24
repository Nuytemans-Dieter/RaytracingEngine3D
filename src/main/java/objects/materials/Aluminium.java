package objects.materials;

import graphics.Rgb;
import objects.Material;

public class Aluminium extends Material {

    public Aluminium()
    {
        super(
            new Rgb(0.86f, 0.86f, 0.86f),
            0.8f,
            0.2f,
            0.9f,
            0.2f,
            new float[] {42.42f, 47.98f, 47.98f},
            0.0f
        );
    }

}
