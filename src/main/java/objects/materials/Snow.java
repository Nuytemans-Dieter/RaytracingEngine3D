package objects.materials;

import graphics.Rgb;
import objects.Material;
import objects.textures.ImageTexture;

public class Snow extends Material {

    public Snow( boolean useTextureOne )
    {
        super(
            new Rgb(0.95f, 0.95f, 0.95f),
            0.6f,
            0.5f,
            32,
            0.0f,
            1,
            0.0f
        );

//        this.setEmissive(0.3f, 0.3f, 0.3f);
        this.setTexture( new ImageTexture( useTextureOne ? "snow3.jpg" : "snow2.jpg" ));

    }

}
