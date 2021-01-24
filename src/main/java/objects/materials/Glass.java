package objects.materials;

import graphics.Rgb;
import objects.Material;

public class Glass extends Material {

    public Glass()
    {
        super(
            new Rgb(0.7f, 0.7f, 0.7f),
            0.3f,
            0.2f,
            0.0f,
            0.05f,
            new float[] {0.5f, 0.5f, 0.5f},
            0.9f
        );
    }

}
