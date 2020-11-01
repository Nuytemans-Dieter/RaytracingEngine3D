package objects.materials;

import graphics.Rgb;
import objects.Material;

public class Aluminium extends Material {

    public Aluminium()
    {
        super(
            new Rgb(0.678f, 0.698f, 0.741f),
            0.3f,
            0.1f,
            0.9f,
            0.1f,
            new float[] {42.42f, 47.98f, 47.98f},
            0.0f
        );
    }

}
