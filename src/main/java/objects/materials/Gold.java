package objects.materials;

import graphics.Rgb;
import objects.Material;

public class Gold extends Material {

    public Gold()
    {
        super(
            new Rgb(0.45f, 0.35f, 0.1f),
            2.5f,
            0.5f,
            64,
            0.1f,
            0.42,
            0.0f
        );
    }

}
