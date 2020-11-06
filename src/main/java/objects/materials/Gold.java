package objects.materials;

import graphics.Rgb;
import objects.Material;

public class Gold extends Material {

    public Gold()
    {
        super(
            new Rgb(0.831f, 0.686f, 0.216f),
            0.3f,
            0.5f,
            0.9f,
            0.3f,
            new float[] {42.42f, 47.98f, 47.98f},
            0.0f
        );
    }

}
