package objects.materials;

import graphics.Rgb;
import objects.Material;

public class Gold extends Material {

    public Gold()
    {
        super(
            new Rgb(0.85f, 0.65f, 0.1f),
            2.5f,
            0.5f,
            64,
            0.1f,
            new float[] {42.42f, 47.98f, 47.98f},
            0.0f
        );
    }

}
