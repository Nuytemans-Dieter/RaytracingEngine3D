package objects.object3d;

import graphics.Rgb;
import objects.Object3D;
import objects.Ray;

public class Cilinder extends Object3D {

    @Override
    public Double getCollidingT(Ray ray)
    {
        return 0d;
    }
}
